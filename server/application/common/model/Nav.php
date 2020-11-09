<?php

namespace app\common\model;

class Nav extends BaseModel
{
    protected $table = 'nav';
    static function getList($position)
    {
        $list = self::where(compact('position'))->order('id,sort_num desc')->select()->toArray();
        return list_to_child($list);
    }
    static function getListByPid($parent_id)
    {
        return self::where(compact('parent_id'))->order('sort_num desc')->select();
    }
    static function getTree($position)
    {
        $list = self::where(compact('position'))->order('id,sort_num desc')->select()->toArray();
        return list_to_html($list);
    }
    static function getTreeColumn($position)
    {
        $list = self::getTree($position);
        $res = [];
        foreach ($list as $v) {
            $res[$v['id']] = $v['_html'] . $v['name'];
        }
        return $res;
    }
    static function getTopMenu()
    {
        return self::where('position','top')->order('sort_num desc')->select();
    }
}