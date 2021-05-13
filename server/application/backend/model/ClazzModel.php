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
            [ 'type' => 'date', 'name' => 'start_date', 'label' => '开始日期','require' => true],
            [ 'type' => 'date', 'name' => 'end_date', 'label' => '结束日期','require' => true],
            [ 'type' => 'time', 'name' => 'sign_start1', 'label' => '第1次签到时间',
                'require' => true, 'info' => '签到签半小时有效' ],
            [ 'type' => 'time', 'name' => 'sign_end1', 'label' => '第1次签退时间',
                'require' => true, 'info' => '若设置了第2次签到时间，则截止时间为此时间' ],
            [ 'type' => 'time', 'name' => 'sign_start2', 'label' => '第2次签到时间',
                'info' => '签到签半小时有效，可空'  ],
            [ 'type' => 'time', 'name' => 'sign_end2', 'label' => '第2次签退时间',
                'info' => '第2次签退时间后有效，可空'],
            [
                'type' => 'mselect',
                'name' => 'staff_in_charge',
                'label' => '负责人',
                'options' => StaffModel::getKV()
            ],
            [ 'type' => 'input', 'name' => 'password', 'label' => '加入密码','info'=>'用于微信学生端绑定,留空或填写0表示取消密码'],

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