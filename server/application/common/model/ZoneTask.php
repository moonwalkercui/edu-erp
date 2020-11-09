<?php
namespace app\common\model;

use think\Db;

class ZoneTask extends BaseModel
{
    use BelongsToClazz;
    protected $table = 'zone_task';

    function zone()
    {
        return $this->hasMany(Zone::class, 'task_id');
    }
    function students()
    {
        return $this->belongsToMany(Student::class, 'zone_task_student', 'student_id', 'task_id');
    }

    // 获取学生领取过的任务id
    static function getTaskIdsByStudentId($student_id)
    {
        return Db::name('zone_task_student')->where(compact('student_id'))->column('task_id');
    }
    static function finishOne($student_id, $task_id)
    {
        Db::name('zone_task_student')->insert(compact('student_id','task_id'));
    }
}