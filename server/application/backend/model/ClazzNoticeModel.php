<?php

namespace app\backend\model;
use app\common\model\Clazz;
class ClazzNoticeModel extends \app\common\model\ClazzNotice
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
                'type' => 'editor',
                'name' => 'content',
                'label' => '公告内容',
                'require' => true
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}