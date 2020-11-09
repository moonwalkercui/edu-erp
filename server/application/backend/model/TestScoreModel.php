<?php

namespace app\backend\model;

use app\common\model\Clazz;
use app\common\model\CourseTimes;
use app\common\model\Grade;
use app\common\model\Section;
use app\common\model\Test;

class TestScoreModel extends \app\common\model\TestScore
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            ['type' => 'select', 'name' => 'clazz_id', 'label' => '选择班级', 'options' => Clazz::order('id desc')->column('name', 'id')],
            ['type' => 'select', 'name' => 'test_id', 'label' => '选择考试', 'options' => Test::order('id desc')->column('title', 'id')],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

}