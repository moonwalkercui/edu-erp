<?php

namespace app\backend\model;

use app\common\model\Staff;
use app\common\model\User;

class StaffModel extends \app\common\model\Staff
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'input',
                'name' => 'name',
                'label' => '登录名',
            ],
            [
                'type' => 'input',
                'name' => 'password',
                'label' => '密码',
            ],

            [
                'type' => 'image',
                'name' => 'photo',
                'label' => '头像',
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

    public function makeFromData2($default_value = [])
    {
        $data = [
            [
                'type' => 'mselect',
                'name' => 'manage_staff_leave',
                'label' => '请假管辖人员',
                'options' => Staff::column('name','id')
            ],
            [
                'type' => 'mselect',
                'name' => 'manage_staff_payout',
                'label' => '请款管辖人员',
                'options' => Staff::column('name','id')
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}