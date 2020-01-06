<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\MemberProfile as MemberProfileModel;
use App\Model\ApiUser\UserModel;
use App\Model\MemberPool;
use App\Service\Api\LogService;
class MemberProfileLogic
{
    public static function save($request , $condition = [])
    {
        if($request->filled('member_name')) $data['name'] = cleanXss($request->input('member_name'));
        if($request->filled('company_name')) $data['company_name'] = cleanXss($request->input('company_name'));
        if($request->filled('email')) $data['email'] = cleanXss($request->input('email'));
        if($request->filled('industry')) $data['industry'] = intval($request->input('industry'));
        if($request->filled('source')) $data['source'] = intval($request->input('source'));
        if($request->filled('mobile')) $data['mobile'] = cleanXss($request->input('mobile'));
        $title = '修改学员';

        if(empty($condition)){ // 新增
            $title = '新增学员:'.$data['mobile'];
//            $auth_user = UserService::getUserInfo();
//            $map = [
//                'id' => $condition['id'] ,
//                'owner' => $auth_user->username,
//            ];
            // 判断学员是否已经存在member表中
            if(MemberPool::where($condition)->first() != null)
                throw new ApiException('不能重复创建学员');

//            $data['owner'] = $auth_user->username;
        }
        return BaseLogic::save('学员管理', $title , '\App\Model\MemberProfile' , $data , $condition );
    }
    public static function delete($mobiles)
    {
        if(MemberProfileModel::whereIn('mobile',$mobiles)->delete()){
            LogService::userLog('学员管理','删除学员:'.implode(',',$mobiles));
            return true;
        } else throw new ApiException('删除失败');
    }
    // 更改顾问
    public static function changeChangeSalesman($uid, $member_ids)
    {
        $res = MemberProfileModel::whereIn('member_id', $member_ids)->update([
            'salesman_uname' => $uid,
        ]);
        if ( $res === false ){
            throw new ApiException('修改失败');
        } elseif ($res === 0){
            throw new ApiException('无变动');
        } else {
            LogService::userLog('学员管理','更改业务维护人:'.implode(',',$member_ids));
            return true;
        }
    }

}