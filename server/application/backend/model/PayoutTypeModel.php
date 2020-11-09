<?php
namespace app\backend\model;

use app\common\model\PayoutType;

class PayoutTypeModel extends PayoutType
{
    use FormDateTrait;
    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'input',
                'name' => 'name',
                'label' => '名称',
                'verify' => true,
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}