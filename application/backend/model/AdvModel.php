<?php
namespace app\backend\model;

class AdvModel extends \app\common\model\Advertisement
{
    use FormDateTrait;
    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'input',
                'name' => 'title',
                'label' => '标题',
                'verify' => true,
            ], [
                'type' => 'image',
                'name' => 'image',
                'label' => '图片',
                'info' => '建议比例750 x 310px'
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

}