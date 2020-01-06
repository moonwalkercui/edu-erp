<?php namespace App\Model;

use App\Service\Rbac\PermissionTrait;

class Permission extends BaseModel
{
    use PermissionTrait;
    protected $table = 'permissions';
    protected $fillable = ['name', 'uri','display_name', 'method', 'description'];
    public $timestamps = false;
    public static function getList()
    {
        // todo 发布后进行缓存
//        return Cache::rememberForever('permissions',1， function () {
            return self::all();
//        });
    }
}