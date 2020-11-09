<?php
namespace app\backend\model;

use app\common\model\H5;
use app\common\model\Knowledge;
use app\common\model\KnowledgeCategory;

class KnowledgeModel extends Knowledge
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
                'name' => 'category_id',
                'label' => '分类',
                'options' => KnowledgeCategory::column("name",'id')
            ], [
                'type' => 'mselect',
                'name' => 'h5_id',
                'label' => '讲解',
                'options' => H5::column("title",'id')
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

}