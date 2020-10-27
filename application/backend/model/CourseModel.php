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
            ['type' => 'select', 'name' => 'section_id', 'label' => '课时', 'options' => Section::order('id desc')->column('title', 'id')],
            ['type' => 'select', 'name' => 'times_id', 'label' => '课次', 'options' => CourseTimes::column('name', 'id')],
            ['type' => 'select', 'name' => 'staff_id', 'label' => '授课老师', 'options' => StaffModel::getKV()],
            ['type' => 'radio', 'name' => 'status', 'label' => '状态', 'options' => config('status.course')  ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

}