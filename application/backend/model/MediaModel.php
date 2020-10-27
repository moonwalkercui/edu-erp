<?php

namespace app\backend\model;

use app\common\model\Clazz;
use app\common\model\Student;
use app\common\service\TencentCloud;

class MediaModel extends \app\common\model\Media
{
    use FormDateTrait;

    function makeFromData1($default_value = [])
    {
        $data = [
            [
                'type' => 'upimgs',
                'name' => 'images',
                'label' => '照片',
                'size' => 9,
                'require' => true,
                'pid' => 0
            ],
            [
                'type' => 'textarea',
                'name' => 'content',
                'label' => '图片描述',
            ],
            [
                'type' => 'select',
                'name' => 'clazz_id',
                'label' => '选择班级',
                'options' => Clazz::column('name', 'id'),
                'require' => true
            ],
            [
                'type' => 'mselect',
                'name' => 'student_ids',
                'label' => '选择学员',
                'options' => Student::column('name', 'id'),
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
    function makeFromData2($default_value = [])
    {
        $data = [
            [
                'type' => 'vodlist',
                'name' => 'video_url',
                'label' => '选择腾讯视频',
                'require' => true
            ],
            [
                'type' => 'textarea',
                'name' => 'content',
                'label' => '视频描述',
            ],
            [
                'type' => 'select',
                'name' => 'clazz_id',
                'label' => '选择班级',
                'options' => Clazz::column('name', 'id'),
                'require' => true
            ],
            [
                'type' => 'mselect',
                'name' => 'student_ids',
                'label' => '选择学员',
                'options' => Student::column('name', 'id'),
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}