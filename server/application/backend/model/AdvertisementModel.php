<?php
namespace app\backend\model;
class AdvertisementModel extends \app\common\model\Advertisement
{
    use FormDateTrait;
    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'input',
                'name' => 'title',
                'label' => '分类名称',
                'require' => true
            ], [
                'type' => 'image',
                'name' => 'image',
                'label' => '图片',
                'require' => true
            ], [
                'type' => 'select',
                'name' => 'position',
                'label' => '显示位置',
                'options' => config('status.ad_position'),
            ], [
                'type' => 'input',
                'name' => 'url',
                'label' => 'URL'
            ], [
                'type' => 'number',
                'name' => 'sort_num',
                'label' => '排序值',
                'info' => '默认为0，数值越大越靠前'
            ], [
                'type' => 'radio',
                'name' => 'status',
                'label' => '是否显示',
                'options' => config('status.switch'),
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}