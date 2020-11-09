<?php
namespace app\common\model;

use think\model\concern\SoftDelete;

class Clazz extends BaseModel
{
    use SoftDelete,BelongsToGrade;
    protected $table = 'clazz';
    protected $deleteTime = 'delete_time';
    // 获取负责人ids
    static function getStaffIds(int $id)
    {
        $ids = self::where(compact('id'))->value('staff_in_charge');
        return explode(',', $ids);
    }
    static function getStudents($clazz_id)
    {
        return Student::where(compact('clazz_id'))->select();
    }
    static function getByStaffId(int $staff_id, $where=[])
    {
        return self::where("FIND_IN_SET({$staff_id},`staff_in_charge`)")->where($where)->field('id,name')->select();
    }
}