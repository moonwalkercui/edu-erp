<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\Role as RoleModel;
use App\Model\ApiUser\UserModel;
use App\Service\Api\UserService;

class RbacLogic
{
    // 修改角色
    public static function roleEdit($request)
    {
        $condition['id'] = intval($request->input('id'));
        $data['name'] = cleanXss($request->input('name'));
        $data['description'] = cleanXss($request->input('description'));
        $data['view_division_ids'] = implode(',',cleanXss($request->input('divisions',[])));
        $permissions = cleanXss($request->input('permissions'));
        return BaseLogic::save('权限管理','修改角色:'.$data['name'] , '\App\Model\Role' , $data , $condition , function($id) use ($condition,$permissions) {
            $role = RoleModel::where($condition)->first();
            $role->updatePermissions($permissions);
        });
    }
    // 创建角色
    public static function roleCreate($request)
    {
        $data['name'] = cleanXss($request->input('name'));
        $data['description'] = cleanXss($request->input('description'));
        $data['view_division_ids'] = implode(',',cleanXss($request->input('divisions',[])));
        $permissions = $request->input('permissions');

        return BaseLogic::save('权限管理','创建角色:'.$data['name'] , '\App\Model\Role' , $data , [] , function($id) use ($permissions) {
            $role = RoleModel::find($id);
            $role->addPermissions($permissions);
        });
    }
    // 更新用户权限
    public static function saveUserRoles($request)
    {
        $username = cleanXss($request->input('username'));
        $roles = $request->input('roles');
        $user = UserModel::where('username',$username)->first();
        if($request->filled('roles') == false || count($roles) == 0)
            $res = $user->roles()->detach();
        else
            $res = $user->roles()->sync($roles);
        if($res !== false) return true;
        throw new ApiException('更新权限失败');
    }
    public static function roleDelete($role_id)
    {
        $role = RoleModel::find($role_id);
        $role->deleteRole();
        $role->delete();
        return true;
    }
    // 如果没有设置查看门店的id则默认是返回null：可以查看全部
    public static function getViewDivisionIds($user)
    {
        if(UserService::isManager()) return null;
        $ids = '';
        $user->roles()->each(function($item,$key) use (&$ids){
            $ids .= $item->view_division_ids . ',';
        });
        return trim($ids,',') != '' ? explode(',' , trim($ids,',')) : null;
    }
}