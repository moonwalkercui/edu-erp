<?php namespace App\Service\Rbac;

use Illuminate\Support\Facades\Config;

trait RoleTrait
{
    public function users()
    {
        return $this->belongsToMany(Config::get('rbac.user_model') , Config::get('rbac.role_user_table') , 'role_id', 'user_id');
    }
    public function permissions()
    {
        return $this->belongsToMany(Config::get('rbac.permission_model') , Config::get('rbac.permission_role_table') , 'role_id' , 'permission_id');
    }
    // 给角色添加权限
    public function addPermissions($permissions)
    {
        return $this->permissions()->attach($permissions);
    }
    // 获取缓存里的权限列表
    public function getRolePermissions()
    {
        return $this->permissions()->get();
    }
    // 更新角色的权限
    public function updatePermissions($permission_ids)
    {
        if(!empty($permission_ids))
            $this->permissions()->sync($permission_ids);
        else
            $this->permissions()->detach();
    }
    // 删除角色
    public function deleteRole()
    {
        $this->permissions()->detach(); // 移除中间表中角色的所有关联权限
        $this->users()->detach(); // 移除中间表中角色的所有关联user
    }
//    public static function boot()
//    {
//        parent::boot();
//        static::deleting(function($role) {
//            // 删除角色时
//            $role->permissions()->detach(); // 移除中间表中角色的所有关联权限
//            $role->users()->detach(); // 移除中间表中角色的所有关联user
//            return true;
//        });
//    }
//    // 判断角色是否具有权限 $permission_name 可以是数组
//    public function hasPermission( $permission_name )
//    {
//        if ( is_array($permission_name) ) {
//            foreach ($permission_name as $id ) {
//                if ($this->hasPermission( $id ))
//                    return true;
//            }
//            return false;
//        } else {
//            foreach ($this->getRolePermissions() as $permission) {
//                if ($permission->name == $permission_name) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//    // 反向判断，如果在permission列表里，表示没有权限。
//    public function hasNoPermission($permission_name)
//    {
////        if ( is_array($permission_name) ) {
////            foreach ($permission_name as $id ) {
////                if ($this->hasPermission( $id ))
////                    return true;
////            }
////            return false;
////        } else {
////            foreach ($this->getRolePermissions() as $permission) {
////                if ($permission->name == $permission_name) {
////                    return true;
////                }
////            }
////        }
////        return false;
//    }
}
