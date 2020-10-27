<?php
namespace app\backend\model;

class ZoneModel extends \app\common\model\Zone
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'radio',
                'name' => 'verify_score',
                'label' => '点评分数',
                'options' => ['1'=>'1分','2'=>'2分','3'=>'3分','4'=>'4分','5'=>'5分',],
                'require' => true
            ],
            [
                'type' => 'textarea',
                'name' => 'verify_content',
                'label' => '点评内容',
                'require' => true
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}