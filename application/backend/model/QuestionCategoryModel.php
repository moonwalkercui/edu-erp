<?php
namespace app\backend\model;

use app\common\model\QuestionCategory;

class QuestionCategoryModel extends QuestionCategory
{
    use FormDateTrait;
    public function makeFromData($default_value = [])
    {
        $category = self::where('parent_id', 0)->column("name",'id');
        $data = [
            [
                'type' => 'input',
                'name' => 'name',
                'label' => '名称',
                'verify' => true,
            ], [
                'type' => 'select',
                'name' => 'parent_id',
                'label' => '所属分类',
                'options' =>self::categoryWrap($category)
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}