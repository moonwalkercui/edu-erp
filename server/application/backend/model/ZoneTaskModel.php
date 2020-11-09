<?php

namespace app\backend\model;
use app\common\model\Clazz;

class ZoneTaskModel extends \app\common\model\ZoneTask
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'select',
                'name' => 'clazz_id',
                'label' => '班级',
                'options' => Clazz::column('name', 'id'),
                'require' => true
            ],
            [
                'type' => 'editor',
                'name' => 'content',
                'label' => '作业要求',
                'require' => true
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

}