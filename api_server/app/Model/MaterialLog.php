<?php

namespace App\Model;

use App\Service\Api\UserService;

class MaterialLog extends BaseModel
{
    protected $table='material_log';
    public $timestamps = false;
    protected $fillable = [
        'username',
        'user_name',
        'material_name',
        'quantity',
        'money',
        'remark',
        'status',
        'created_at',
    ];
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'material');
    }
    public static function makeLog(Material $material , $number, $money , $status , $remark)
    {
        $user = UserService::getUserInfo();
        return self::insert([
            'username' => $user->username,
            'user_name' => $user->profile->name,
            'material_id' => $material->id,
            'material_name' => $material->name,
            'quantity' => $number,
            'money' => $number * $money * (-1), // 出库收了多少钱
            'status' => $status,
            'remark' => $remark,
            'created_at' => now()
        ]);
    }
}
