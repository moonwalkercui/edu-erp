<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\Department as DepartmentModel;
use App\Model\ApiUser\UserModel;
use App\Model\UserProfile as UserProfileModel;
use App\Model\User;
use App\Service\Api\LogService;
use App\Service\Api\UserService;
class DepartmentLogic
{

    public static function setUserDepartment($username,$department_id)
    {
        $user = UserService::getUserInfo();
        $applicant = UserService::checkExist($username);

        $update_user = User::find($applicant->id);
        $update_user->department_id = $department_id;
        if($update_user->save()) return true;
        else throw new ApiException('更新失败');
    }
    public static function save($request , $condition = [])
    {
        $data['department_name'] = cleanXss($request->input('name'));
        $data['division_id'] = intval($request->input('division'));
        $parent = $request->input('parent');
        $data['parent_ids'] = empty($parent) ? '' : implode(',' ,$parent);
        $data['parent_id'] = empty($parent) ? 0 : end($parent);
//        $data['leader_username'] = $request->input('leader',null);
//        if( $data['leader_username'] != null){
//            UserService::checkExist($data['leader_username']);
//        }

        $title = '修改部门';
        if(empty($condition)){
            $title = '添加部门';
        }else{
            if($condition['id'] == $data['parent_id']) throw new ApiException('上级部门选择错误');
        }
        return BaseLogic::save('组织管理', $title.':'.$data['department_name'] , '\App\Model\Department' , $data , $condition );
    }

    public static function delete($department_id)
    {
        $count = DepartmentModel::where('parent_id',$department_id)->count();
        if($count > 0) throw new ApiException('不能删除有下级的部门');
        return BaseLogic::delete('组织管理', '删除部门','\App\Model\Department' , [
            'id' => $department_id ,
        ] ,function($condition){
            UserProfileModel::where('department_id',$condition['id'])->update([
                'department_id' => null,
                'department_ids' => null,
            ]);
        });
    }
    public static function saveUsersDepartment($department_id,$users)
    {
        $departments = DepartmentModel::all()->toArray();

        $user_ids = UserModel::whereIn('username',$users)->pluck('id');

        $res = UserProfileModel::whereIn('user_id',$user_ids)->update([
            'division_id' => DepartmentModel::where('id',$department_id)->value('division_id'),
            'department_id' => $department_id,
            'department_ids' => getParentsIds($departments , $department_id),
        ]);
        if ( $res === false ){
            throw new ApiException('修改失败');
        } elseif ($res === 0){
            throw new ApiException('无更新');
        } else {
            LogService::userLog('部门管理','更新部门成员');
            return true;
        }
    }

}
