<?php
namespace app\backend\model;
use app\common\model\UserLevel;

class UserLevelModel extends UserLevel
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'input',
                'name' => 'name',
                'label' => '等级名称',
                'require' => true
            ], [
                'type' => 'number',
                'name' => 'need_points',
                'label' => '所需积分',
                'require' => true
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}