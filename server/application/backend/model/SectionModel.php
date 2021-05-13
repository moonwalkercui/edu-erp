<?php
namespace app\backend\model;

use app\common\model\Section;

class SectionModel extends Section
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'input',
                'name' => 'title',
                'label' => '章节标题',
            ],
            [
                'type' => 'select',
                'name' => 'category_id',
                'label' => '分类',
                'options' => SectionCategoryModel::getTreeColumn()
            ],
            [
                'type' => 'editor',
                'name' => 'remark',
                'label' => '授课内容',
            ],
            [
                'type' => 'editor',
                'name' => 'training',
                'label' => '重点'
//                'info' => '上课当天，学员端会显示练习通知'
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}