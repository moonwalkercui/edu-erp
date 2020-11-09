<?php

namespace app\backend\model;
use app\common\model\Clazz;
use app\common\model\Grade;

class ClazzModel extends \app\common\model\Clazz
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'input',
                'name' => 'name',
                'label' => '班级名',
                'require' => true
            ],
//            [
//                'type' => 'select',
//                'name' => 'grade_id',
//                'label' => '年级',
//                'options' => Grade::column('name', 'id')
//            ],
            [
                'type' => 'mselect',
                'name' => 'staff_in_charge',
                'label' => '负责人',
                'options' => StaffModel::getKV()
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
    public function changePoints($default_value = [])
    {
        $data = [
            [
                'type' => 'number',
                'name' => 'point',
                'label' => '变动积分数字',
                'require' => true,
                'info' => '输入大于0的数字',
            ],
            [
                'type' => 'input',
                'name' => 'reason',
                'label' => '变动原因',
                'require' => true,
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}