<?php

namespace app\backend\model;

use app\common\model\H5;
use app\common\model\Knowledge;

class H5Model extends \app\common\model\H5
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
                'type' => 'mselect',
                'name' => 'knowledge_id',
                'label' => '对应知识点',
                'options' => Knowledge::column("title",'id')
            ], [
                'type' => 'editor',
                'name' => 'content',
                'label' => '内容',
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}