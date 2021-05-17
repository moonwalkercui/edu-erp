<?php

namespace app\backend\model;
use app\common\model\Clazz;
use app\common\model\Student;
use app\common\model\Vcourse;

class VcourseStudtyModel extends \app\common\model\VcourseStudty
{
    use FormDateTrait;
    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'select',
                'name' => 'student_id',
                'label' => '学员',
                'options' => Student::column('name', 'id'),
                'require' => true
            ],
            [
                'type' => 'select',
                'name' => 'vcourse_id',
                'label' => '课程',
                'options' => Vcourse::column('title', 'id'),
                'require' => true
            ],
            [
                'type' => 'radio',
                'name' => 'done',
                'label' => '是否完成',
                'options' => config('status.switch'),
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}