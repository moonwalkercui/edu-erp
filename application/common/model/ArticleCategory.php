<?php

namespace app\common\model;

class ArticleCategory extends BaseModel
{
    protected $table = 'article_category';
    static function getTree()
    {
        $list = self::order('id,sort_num desc')->select()->toArray();
        return list_to_html($list);
    }
    static function getTreeColumn()
    {
        $list = self::getTree();
        $res = [];
        foreach ($list as $v) {
            $res[$v['id']] = $v['_html'] . $v['name'];
        }
        return $res;
    }
    static function getPath($category_id)
    {
        return list_parent_path(self::all()->toArray(), $category_id);
    }
    static function getPathPids($category_id)
    {
        $list = self::getPath($category_id);
        $res = [];
        foreach ($list as $v){
            $res[] = $v['id'];
        }
        sort($res);
        return implode(',', $res);
    }
}