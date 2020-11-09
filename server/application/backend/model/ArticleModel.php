<?php

namespace app\backend\model;

use app\common\model\Advertisement;
use app\common\model\ArticleCategory;
use app\common\model\Nav;

class ArticleModel extends \app\common\model\Article
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {

        $data = [
            [
                'type' => 'input',
                'name' => 'title',
                'label' => '文章标题',
                'require' => true
            ], [
                'type' => 'select',
                'name' => 'nav_id',
                'label' => '栏目',
                'options' => Nav::where('parent_id', 19)->column('name', 'id'),
                'require' => true

            ], [
                'type' => 'editor',
                'name' => 'content',
                'label' => '内容',
                'require' => true
            ], [
                'type' => 'select',
                'name' => 'category_id',
                'label' => '分类',
                'options' => ArticleCategory::getTreeColumn(),
            ], [
                'type' => 'image',
                'name' => 'image',
                'label' => '文章封面',
            ], [
                'type' => 'textarea',
                'name' => 'seo_keywords',
                'label' => 'SEO关键字',
            ], [
                'type' => 'textarea',
                'name' => 'seo_description',
                'label' => 'SEO描述',
            ], [
                'type' => 'number',
                'name' => 'sort_num',
                'label' => '排序值',
                'info' => '默认为0，数值越大越靠前'
            ],

        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

    public function makeFromData2($default_value = [])
    {

        $data = [
            [
                'type' => 'input',
                'name' => 'title',
                'label' => '文章标题',
                'require' => true
            ], [
                'type' => 'editor',
                'name' => 'content',
                'label' => '内容',
                'require' => true
            ], [
                'type' => 'image',
                'name' => 'image',
                'label' => '文章封面',
            ], [
                'type' => 'image',
                'name' => 'banner',
                'label' => 'BANNER图',
                'info' => '尺寸同其他banner'

            ], [
                'type' => 'select',
                'name' => 'category_id',
                'label' => '分类',
                'options' => ArticleCategory::getTreeColumn(),
            ], [
                'type' => 'textarea',
                'name' => 'seo_keywords',
                'label' => 'SEO关键字',
            ], [
                'type' => 'textarea',
                'name' => 'seo_description',
                'label' => 'SEO描述',
            ], [
                'type' => 'number',
                'name' => 'sort_num',
                'label' => '排序值',
                'info' => '默认为0，数值越大越靠前'
            ], [
                'type' => 'radio',
                'name' => 'status',
                'label' => '是否发布',
                'options' => config('status.switch'),
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}