<?php
namespace App\Logic\ApiUser;

// 加入组织服务
use App\Exceptions\ApiException;
use App\Model\ApiUser\JoinApplicantModel;
use App\Model\JoinApplicant;
use App\Model\Organization;
use App\Model\User;
use App\Service\Api\LogService;
use App\Service\Api\UserService;

class JoinLogic
{
    // 加入申请
    public static function application(Organization $org, $user = null, $remark='')
    {
        if($org == null) throw new ApiException('该组织已注销 无法加入');
        if($org->can_join != 1) throw new ApiException('该组织拒绝加入');
        if($org->status != getStatusText(1,'organization')) throw new ApiException('该组织已关停');

        if($user === null) $user = UserService::getUserInfo();
        $exist = JoinApplicant::where([
            'username' => $user->username,
            'status' => 0
        ])->first();
        if($exist) throw new ApiException('已申请过了');

        $res = JoinApplicant::create([
            'username' => $user->nick_name,
            'username' => $user->username,
            'remark' => $remark,
            'created_at' => now()
        ]);
        if($res) {
            LogService::userLog('组织申请','通过编码加入组织:'.$org->organization_name);
            return true;
        } else throw new ApiException('申请加入组织失败');
    }
    // 取消申请
    public static function applicationCancel()
    {
        $user = UserService::getUserInfo();
        $join = JoinApplicantModel::where([
            'username' => $user->username,
        ])->first();
        if($join == null) throw new ApiException('未查询到申请记录');
        if($join->delete()) {
            LogService::userLog('组织申请','取消加入申请');
            return true;
        } else throw new ApiException('取消申请失败');

    }

    // 批量处理申请
    public static function handleApplication($ids,$status,$internal,$remark='')
    {
        BaseLogic::checkStatus($status,'applicant');
        $user = UserService::getUserInfo(); // 处理者
        $usernames = JoinApplicantModel::whereIn('id', $ids)->pluck('username')->toArray();
        $org_quantity = User::whereIn('username', $usernames)->pluck('org_quantity','username')->toArray();

        // 如果是通过审核操作 判断用户是否达到可加入组织的上限数量
        $rejected_users = []; // 这些是达到上线的
        if($status == 1)
            foreach ($usernames as $k=>$u){
                if(UserLogic::canKeepJoinOrg($u , $org_quantity[$u]) == false) {
                    $rejected_users[] = $u;
                    unset($usernames[$k]);
                }
            }
        $res = JoinApplicantModel::whereIn('username' , $usernames )->update([
            'status' => $status,
            'handle_remark' => $remark,
            'handle_at' => now(),
            'handle_username' => $user->username,
        ]);
        // 处理达到上限的
        if(!empty($rejected_users))
            JoinApplicantModel::whereIn('username' , $rejected_users )->update([
                'status' => -1,
                'handle_remark' => '系统驳回:会员的可加入组织数量达到上限',
                'handle_at' => now(),
                'handle_username' => '系统',
            ]);
        if(empty($usernames)) throw new ApiException('因会员的可加入组织数量达到上限 无法进行审核');
        if($res){
            foreach ($usernames as $u){
//                UserLogic::joinOrg($user->organization_id , $u ,$internal);
            }
            LogService::userLog('组织申请','处理结果:'.getStatusText($status,'applicant').' '.implode(',',$usernames));
            return true;
        }else{
            throw new ApiException('操作失败');
        }
    }

}
