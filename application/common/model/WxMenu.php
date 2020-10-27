<?php

namespace app\common\model;

class WxMenu extends BaseModel
{
    protected $table = 'wx_menu';

    static function getAll()
    {
        return self::order('sort_num')->select()->toArray();
    }

    static function getTree($all)
    {
        return list_to_child($all);
    }

    static function getPlainTree($all)
    {
        return list_to_html($all);
    }
    static function getContent($id)
    {
        return self::where(compact('id'))->value('value');
    }

    /* 创建菜单数组
    [
    "button" =>
        [
            ['type' => 'view', 'name' => '会员卡中心', 'url' => 'http://api.zesmz.com/wxauth'],
            ['type' => 'view', 'name' => '用卡记录', 'url' => 'http://api.zesmz.com/vipcenter/index.html#/pages/index/history'],
            //                    ['type' => 'click', 'name' => '推广二维码', 'key' => 'GET_QR'],
        ]
    ]
    */
    static function makeMenuArr()
    {
        $fmt = function ($item, $has_child = false) {
            if ($has_child) {
                $arr = [
                    'name' => $item['name'],
                    'sub_button' => [],
                ];
            } else {
                switch ($item['type']) {
                    case 'url':
                        $arr = [
                            'name' => $item['name'],
                            'type' => 'view',
                            'url' => $item['value'],
                        ];
                        break;
                    case 'media':
                        $arr = [
                            'name' => $item['name'],
                            'type' => 'media_id',
                            'media_id' => $item['value'],
                        ];
                        break;
                    case 'text':
                        $arr = [
                            'name' => $item['name'],
                            'type' => 'click',
                            'key' => "menu_text_".$item['id'],
                        ];
                        break;
                    default:
                        $arr = [
                            'name' => $item['name'],
                        ];
                }
            }
            return $arr;
        };
        $list = WxMenu::order('sort_num')->where('parent_id', 0)->select();
        if(count($list) == 0) exception('未设置栏目');

        $res = [];
        foreach ($list as $v) {
            $child = WxMenu::order('sort_num')->where('parent_id', $v['id'])->select();
            if (count($child) == 0) {
                $m = $fmt($v);
            } else {
                $m = $fmt($v, true);
                foreach ($child as $c) {
                    $m['sub_button'][] = $fmt($c);
                }
            }
            $res[] = $m;
        }
        return ['button' => $res];
    }
}