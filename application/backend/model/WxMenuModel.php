<?php

namespace app\backend\model;
class WxMenuModel extends \app\common\model\WxMenu
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $menu = self::where('parent_id', 0)->order('sort_num')->column('name','id');
        $ids = array_keys($menu);
        array_unshift($ids, '0');
        $names = array_values($menu);
        array_unshift($names, '根目录');
        $menus = array_combine($ids, $names);

        $data = [
            [
                'type' => 'input',
                'name' => 'name',
                'label' => '菜单名',
                'require' => true
            ],
            [
                'type' => 'select',
                'name' => 'parent_id',
                'label' => '上级菜单',
                'require' => true,
                'options' => $menus
            ],
            [
                'type' => 'radio',
                'name' => 'type',
                'label' => '行为',
                'options' => [
                    'url' => '链接',
                    'media' => '素材',
                    'text' => '文本',
                ],
                'info' => '若包含二级菜单，该项目不用设置。'
            ],
            [
                'type' => 'textarea',
                'name' => 'value',
                'label' => '内容',
                'info' => '此内容，当行为是链接时为URL，当是文字时填写文字，选择素材会自动填充素材ID'
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
    // 排序
    static function handleSort($ids)
    {
        if(empty($ids)) return false;

        foreach ($ids as $k => $id) {
            self::where('id', $id)->update(['sort_num' => $k]);
        }
        return true;

    }
    // 新增时设置新排序值
    static function getNewSort($parent_id)
    {
        $parent_id = intval($parent_id);
        return self::where(compact('parent_id'))->count();
    }
}