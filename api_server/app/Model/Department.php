<?php

namespace App\Model;

class Department extends BaseModel
{
    protected $table='departments';
    protected $fillable = ['department_name', 'parent_ids','parent_id','leader_username','created_at','division_id','sort'];
    public function division()
    {
        return $this->belongsTo('App\Model\Divisions','division_id');
    }
    // 获取部门id路径 如 3,5
    public static function getParentsIds($id)
    {
        $list = self::all()->toArray();
        return getParentsIds($list , $id);
    }
}
