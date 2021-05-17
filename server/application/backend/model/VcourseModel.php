<?php

namespace app\backend\model;
use app\common\model\Clazz;
class VcourseModel extends \app\common\model\Vcourse
{
    use FormDateTrait;
    public function makeFromData($default_value = [])
    {
        $data = [

            [
                'type' => 'select',
                'name' => 'clazz_id',
                'label' => '选择班级',
                'options' => Clazz::column('name','id'),
                'require' => true
            ],
            [
                'type' => 'input',
                'name' => 'title',
                'label' => '标题',
                'require' => true
            ],
            [
                'type' => 'vodlist',
                'name' => 'vod_urls',
                'label' => '选择腾讯视频',
                'require' => true
            ],
            [
                'type' => 'number',
                'name' => 'sn',
                'label' => '序号',
            ],
            [
                'type' => 'editor',
                'name' => 'content',
                'label' => '简介',
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}