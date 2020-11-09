<?php

namespace app\backend\model;

use app\common\model\Clazz;
use app\common\model\Student;
use app\common\service\TencentCloud;

class OldStudentModel extends \app\common\model\OldStudent
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'input',
                'name' => 'name',
                'label' => '姓名',
                'verify' => true,
            ], [
                'type' => 'image',
                'name' => 'avatar',
                'label' => '头像',
                'info' => '方形即可'
            ], [
                'type' => 'image',
                'name' => 'photo',
                'label' => '生活照',
                'info' => '尺寸400*225px'
            ], [
                'type' => 'input',
                'name' => 'study_time',
                'label' => '学习时间',
            ], [
                'type' => 'editor',
                'name' => 'description',
                'label' => '个人介绍',
            ], [
                'type' => 'number',
                'name' => 'sort_num',
                'label' => '排序',
                'info' => '数值越大越靠前'
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

}