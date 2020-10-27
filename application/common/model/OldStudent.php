<?php
namespace app\common\model;

use think\Model;

class OldStudent extends Model
{
    protected $table = 'old_student';
    static function getList($per_page=10)
    {
        return self::order('sort_num desc')->paginate($per_page);
    }
}