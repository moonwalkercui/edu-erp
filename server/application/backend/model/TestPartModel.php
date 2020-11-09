<?php
namespace app\backend\model;

use app\common\model\TestPart;

class TestPartModel extends TestPart
{
    use FormDateTrait;
    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'textarea',
                'name' => 'content',
                'label' => '说明',
            ], [
                'type' => 'number',
                'name' => 'score',
                'label' => '每题分数',
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

}