<?php
namespace app\backend\model;
class ArticleCategoryModel extends \app\common\model\ArticleCategory
{
    use FormDateTrait;
    public function makeFromData($default_value = [])
    {
        $list = self::getTreeColumn();
        array_unshift($list, '根目录');
        $data = [
            [
                'type' => 'input',
                'name' => 'name',
                'label' => '分类名称',
                'require' => true
            ], [
                'type' => 'select',
                'name' => 'parent_id',
                'label' => '上级分类',
                'options' => $list,
                'require' => true
            ], [
                'type' => 'number',
                'name' => 'sort_num',
                'label' => '排序值',
                'info' => '默认为0，数值越大越靠前'
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}