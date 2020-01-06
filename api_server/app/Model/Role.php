<?php namespace App\Model;

use App\Service\Rbac\RoleTrait;
class Role extends BaseModel
{
    use RoleTrait;
    protected $table = 'roles';
    protected $fillable = ['name', 'display_name', 'description','view_division_ids'];
}