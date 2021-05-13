<?php
namespace app\common\model;

use app\backend\model\SectionModel;
use app\backend\model\ZoneTaskModel;
use think\Db;

class ZoneTask extends BaseModel
{
    use BelongsToClazz,BelongsToStaff;
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
    // 添加任务
//    static function addTask($item)
//    {
//        $course = Course::find($item['id']);
//        if($course['section_id']) {
//            $section = SectionModel::where('id', $course['section_id'])->find();
//            if($section && $section->task_id == null) {
//                $section->task_id = ZoneTaskModel::insertGetId([
//                    'clazz_id' => $course['clazz_id'],
//                    'content' => $section['training'],
//                    'add_time' => now(),
//                ]);
//                $section->save();
//            }
//        }
//    }
    // 更新内容
//    static function updateTask($section_id)
//    {
//        $data = compact('section_id');
//        $data['add_time'] = now();
//        ZoneTaskModel::insert($data);
//        $section = SectionModel::where('id', $section_id)->find();
//        if($section && $section->task_id) {
//            ZoneTaskModel::where('id', $section->task_id)->update([
//                'content' => $section['training']
//            ]);
//        }
//    }
}