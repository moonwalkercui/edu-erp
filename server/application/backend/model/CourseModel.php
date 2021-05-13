<?php

namespace app\backend\model;

use app\common\model\Clazz;
use app\common\model\CourseTimes;
use app\common\model\Grade;
use app\common\model\Section;

class CourseModel extends \app\common\model\Course
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            ['type' => 'date', 'name' => 'date', 'label' => '日期'],
//            ['type' => 'select', 'name' => 'grade_id', 'label' => '年级', 'options' => Grade::order('id desc')->column('name', 'id')],
            ['type' => 'select', 'name' => 'clazz_id', 'label' => '班级', 'options' => Clazz::order('id desc')->column('name', 'id')],
            ['type' => 'select', 'name' => 'section_id', 'label' => '章节', 'options' => Section::order('id desc')->column('title', 'id')],
            ['type' => 'select', 'name' => 'times_id', 'label' => '课次', 'options' => CourseTimes::column('name', 'id')],
            ['type' => 'select', 'name' => 'staff_id', 'label' => '授课老师', 'options' => StaffModel::getKV()],
            ['type' => 'radio', 'name' => 'status', 'label' => '状态', 'options' => config('status.course')  ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

    // 每天定时执行1次 生成课时的练习通知给学员
    static function generateTrainingNotice()
    {
//        dump(ZoneTaskModel::whereTime('add_time', 'today')->count());
        if(ZoneTaskModel::whereTime('add_time', 'today')->count() > 0) return;
        $course_list = self::where('date', date('Y-m-d'))->where('section_id is not null')->select()->toArray();
//        dump($course_list);
        if(empty($course_list)) return;
        foreach ($course_list as $c) {
            $section = SectionModel::where('id', $c['section_id'])->find();
            $section->task_id = ZoneTaskModel::insertGetId([
               'clazz_id' => $c['clazz_id'],
               'content' => $section['training'],
               'add_time' => now(),
            ]);
            $section->save();
        }
    }
}