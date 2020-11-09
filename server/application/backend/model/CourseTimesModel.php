<?php

namespace app\backend\model;

use app\common\model\Clazz;
use app\common\model\CourseTimes;
use app\common\model\Grade;
use app\common\model\Section;

class CourseTimesModel extends \app\common\model\CourseTimes
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            ['type' => 'input', 'name' => 'name', 'label' => '课次名称'],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

}