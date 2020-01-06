<?php namespace App\Service\Rbac;

use Illuminate\Support\Facades\Config;

trait UserTrait
{
    public function roles()
    {
        return $this->belongsToMany( Config::get('rbac.role_model') , Config::get('rbac.role_user_table') , 'user_id', 'role_id');
    }
    // 获取uer的所有的角色
    public function getUserRoles()
    {
        return $this->roles()->get();
    }
    /*
     * 检查用户是否有某个权限
     * @param $permission 权限名，可以是数组
     */
    public function hasPermission( $permission )
    {
//        if (is_array($permission)) {
//            foreach ($permission as $permName) {
//                if($this->can($permName))
//                    return true;
//            }
//            return false;
//        } else {
        foreach ($this->getUserRoles() as $role) {
            $uri_arr = $role->getRolePermissions()->pluck('uri')->toArray();
            return in_array($permission,$uri_arr);
//                foreach ($role->getRolePermissions() as $perm) {
//                    if (str_is( $permission, $perm->uri) ) {
//                        return true;
//                    }
//                }
        }
//        }
//        return false;
    }
    public function getPermissions()
    {
        $permissions = [];
        foreach ($this->getUserRoles() as $role) {
            $permissions[] = $role->getRolePermissions()->pluck('id')->toArray();
        }
        return array_unique(array_flatten($permissions));
    }

    /*
     * 给user添加角色
     * */
//    public function addRoles($role_ids)
//    {
//        return $this->roles()->attach($role_ids);
//    }
    /*
     * 更新user的角色
     * */
//    public function updateRoles($role_ids)
//    {
//        if(!empty($permission_ids))
//            $this->roles()->sync($role_ids);
//        else
//            $this->roles()->detach();
//    }

    /*
     * 判断user是否有某个角色搞事情
     * @param $role_names 角色名，可以是数组
     * */
//    public function hasRole($role_names)
//    {
//        if (is_array($role_names)) {
//            foreach ($role_names as $roleName) {
//                if($this->hasRole($roleName) == true)
//                    return true;
//            }
//            return false;
//        } else {
//            foreach ($this->getUserRoles() as $role) {
//                if ($role->name == $role_names) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

//    // 清空user的角色和权限
//    public function deletePermissions()
//    {
//        foreach ($this->getUserRoles() as $role){
//            $role->permissions()->detach(); // 移除中间表中角色的所有关联权限
//            $role->users()->detach(); // 移除中间表中角色的所有关联user
//        }
//    }


    //    /*
//     * 给user关联创建单个角色
//     * @param $role 角色的数组 如： ['name' => '经理', 'display_name' => '销售经理', 'description' => '大角色']
//     * @return 一个Role对象
//     * */
//    public function createRoles($role)
//    {
//        return $this->roles()->create($role);
//    }
//    /*
//     * 给user批量关联创建多个角色
//     * @param $roles_array 角色的数组 如：[
//     *       ['name' => '经理', 'display_name' => '销售经理', 'description' => '大角色'],
//     *       ['name' => '主管', 'display_name' => '技术主管', 'description' => '小角色']
//     *   ]
//     * @return 多个Role对象
//     * */
//    public function createRolesFromArray($roles_array)
//    {
//        $role_model_name = Config::get('rbac.role_model');
//        foreach ($roles_array as $role){
//            $roles[] = new $role_model_name($role);
//        }
//        return $this->roles()->saveMany($roles);
//    }


//
//    /**
//     * Checks role(s) and permission(s).
//     */
//    public function ability($roles, $permissions, $options = [])
//    {
//        // Convert string to array if that's what is passed in.
//        if (!is_array($roles)) {
//            $roles = explode(',', $roles);
//        }
//        if (!is_array($permissions)) {
//            $permissions = explode(',', $permissions);
//        }
//
//        // Set up default values and validate options.
//        if (!isset($options['validate_all'])) {
//            $options['validate_all'] = false;
//        } else {
//            if ($options['validate_all'] !== true && $options['validate_all'] !== false) {
//                throw new InvalidArgumentException();
//            }
//        }
//        if (!isset($options['return_type'])) {
//            $options['return_type'] = 'boolean';
//        } else {
//            if ($options['return_type'] != 'boolean' &&
//                $options['return_type'] != 'array' &&
//                $options['return_type'] != 'both') {
//                throw new InvalidArgumentException();
//            }
//        }
//
//        // Loop through roles and permissions and check each.
//        $checkedRoles = [];
//        $checkedPermissions = [];
//        foreach ($roles as $role) {
//            $checkedRoles[$role] = $this->hasRole($role);
//        }
//        foreach ($permissions as $permission) {
//            $checkedPermissions[$permission] = $this->can($permission);
//        }
//
//        // If validate all and there is a false in either
//        // Check that if validate all, then there should not be any false.
//        // Check that if not validate all, there must be at least one true.
//        if(($options['validate_all'] && !(in_array(false,$checkedRoles) || in_array(false,$checkedPermissions))) ||
//            (!$options['validate_all'] && (in_array(true,$checkedRoles) || in_array(true,$checkedPermissions)))) {
//            $validateAll = true;
//        } else {
//            $validateAll = false;
//        }
//
//        // Return based on option
//        if ($options['return_type'] == 'boolean') {
//            return $validateAll;
//        } elseif ($options['return_type'] == 'array') {
//            return ['roles' => $checkedRoles, 'permissions' => $checkedPermissions];
//        } else {
//            return [$validateAll, ['roles' => $checkedRoles, 'permissions' => $checkedPermissions]];
//        }
//
//    }
//
//    /**
//     * Alias to eloquent many-to-many relation's attach() method.
//     *
//     * @param mixed $role
//     */
//    public function attachRole($role)
//    {
//        if(is_object($role)) {
//            $role = $role->getKey();
//        }
//
//        if(is_array($role)) {
//            $role = $role['id'];
//        }
//
//        $this->roles()->attach($role);
//    }
//
//    /**
//     * Alias to eloquent many-to-many relation's detach() method.
//     *
//     * @param mixed $role
//     */
//    public function detachRole($role)
//    {
//        if (is_object($role)) {
//            $role = $role->getKey();
//        }
//
//        if (is_array($role)) {
//            $role = $role['id'];
//        }
//
//        $this->roles()->detach($role);
//    }
//
//    /**
//     * Attach multiple roles to a user
//     *
//     * @param mixed $roles
//     */
//    public function attachRoles($roles)
//    {
//        foreach ($roles as $role) {
//            $this->attachRole($role);
//        }
//    }
//
//    /**
//     * Detach multiple roles from a user
//     *
//     * @param mixed $roles
//     */
//    public function detachRoles($roles=null)
//    {
//        if (!$roles) $roles = $this->roles()->get();
//
//        foreach ($roles as $role) {
//            $this->detachRole($role);
//        }
//    }

}
