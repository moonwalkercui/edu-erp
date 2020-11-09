<?php

namespace app\backend\model;
use app\common\model\Clazz;
use app\common\model\Grade;

class UserModel extends \app\common\model\User
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'input',
                'name' => 'name',
                'label' => '姓名',
                'require' => true
            ],
//            [
//                'type' => 'select',
//                'name' => 'grade_id',
//                'label' => '年级',
//                'options' => Grade::column('name', 'id')
//            ],
            [
                'type' => 'select',
                'name' => 'clazz_id',
                'label' => '班级',
                'options' => Clazz::column('name', 'id')
            ],
            [
                'type' => 'input',
                'name' => 'mobile',
                'label' => '手机号',
                'require' => true
            ],
            [
                'type' => 'radio',
                'name' => 'gender',
                'label' => '性别',
                'options' => config('status.gender')
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}