<?php

namespace app\backend\model;
use app\common\model\Clazz;
use app\common\model\Student;
class ClazzSignModel extends \app\common\model\ClazzSign
{
    use FormDateTrait;
    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'select',
                'name' => 'student_id',
                'label' => '选择学生',
                'options' => Student::column('name','id'),
                'require' => true
            ],
            [
                'type' => 'select',
                'name' => 'clazz_id',
                'label' => '选择班级',
                'options' => Clazz::column('name','id'),
                'require' => true
            ],
            [
                'type' => 'datetime', 'name' => 'sign_time',
                'label' => '签到时间',
                'require' => true
            ],
            [
                'type' => 'select',
                'name' => 'sign_state',
                'label' => '选择状态',
                'options' => Clazz::getStates(),
                'require' => true
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}