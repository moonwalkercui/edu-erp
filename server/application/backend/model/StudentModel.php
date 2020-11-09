<?php

namespace app\backend\model;
use app\common\model\Clazz;
use app\common\model\Grade;
use app\common\model\WxAccess;

class StudentModel extends \app\common\model\Student
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
//                'options' => Grade::column('name', 'id'),
//                'require' => true
//            ],
            [
                'type' => 'select',
                'name' => 'clazz_id',
                'label' => '班级',
                'options' => Clazz::column('name', 'id'),
                'require' => true
            ],
            [
                'type' => 'input',
                'name' => 'mobile',
                'label' => '手机号',
                'require' => true
            ],
//            [
//                'type' => 'image',
//                'name' => 'avatar',
//                'label' => '照片',
//            ],
            [
                'type' => 'radio',
                'name' => 'gender',
                'label' => '性别',
                'options' => config('status.gender')
            ],
            [
                'type' => 'radio',
                'name' => 'status',
                'label' => '状态',
                'options' => config('status.normal')
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
    public function makeFromData1($default_value = [])
    {
        $data = [
            [
                'type' => 'select',
                'name' => 'access_id',
                'label' => '选择公众号关注者',
                'options' => WxAccess::where('nickname','<>','')->column('nickname', 'id'),
                'require' => true
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