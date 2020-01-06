<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\JoinInvitation;
use App\Service\Api\LogService;
use App\Service\Api\UserService;
use Carbon\Carbon;

class InvitationLogic
{

    public static function inviteUser($invited_username,$remark = '')
    {
        $user_info = UserService::getUserInfo();

        if(JoinInvitation::where(['username'=>$user_info->username , 'invited_username'=>$invited_username , 'status'=> 0])->first())
        throw new ApiException('您已邀请过该用户了');
        $invitation = new JoinInvitation();
        $invitation->username = $user_info->username;
        $invitation->invited_username = $invited_username;
        $invitation->remark = $remark;
        $invitation->created_at = Carbon::now();
        if( $invitation->save() ) {
            LogService::userLog('邀请处理','发起邀请');
            return true;
        }
        else throw new ApiException('发起邀请失败');

    }

    public static function inviteReject($username)
    {
        $user_info = UserService::getUserInfo();
        $where = [ 'username'=>$username , 'invited_username'=> $user_info->username];

        $invited = JoinInvitation::where($where)->first();

        if($invited == null)
            throw new ApiException('未查询到被邀记录');

        if($invited->delete()) {
            LogService::userLog('邀请处理','拒绝邀请:'.$username);
            return true;
        }
        else throw new ApiException('拒绝邀请失败');

    }
    public static function inviteCancel($invited_username)
    {
        $user_info = UserService::getUserInfo();
        $where = [ 'username'=>$user_info->username , 'invited_username'=>$invited_username ];

        $invited = JoinInvitation::where($where)->first();

        if($invited == null)
            throw new ApiException('未查询到邀请记录');

        if($invited->delete()) {
            LogService::userLog('邀请处理','取消邀请:'.$invited_username);
            return true;
        }
        else throw new ApiException('取消邀请失败');

    }

}
