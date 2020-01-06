<?php namespace App\Service\Rbac;

use Illuminate\Support\Facades\Config;

trait PermissionTrait
{
    public function roles()
    {
        return $this->belongsToMany( Config::get('rbac.role_model') , Config::get('rbac.permission_role') , 'permission_id', 'role_id');
    }

}
