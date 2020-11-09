<?php
namespace app\backend\model;

use app\common\model\OffdutyType;

class OffdutyTypeModel extends OffdutyType
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