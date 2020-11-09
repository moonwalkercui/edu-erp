<?php
namespace app\backend\model;

class NavModel extends \app\common\model\Nav
{
    use FormDateTrait;
    function makeFromDataTop($data)
    {
        $list = self::getTreeColumn('top');
        return $this->makeFromData($list, $data);
    }
    function makeFromDataBottom($data)
    {
        $list = self::getTreeColumn('bottom');
        return $this->makeFromData($list, $data);
    }
    function makeFromDataHome($data)
    {
        $list = self::getTreeColumn('home');
        return $this->makeFromData($list, $data);
    }
    private function makeFromData($list, $default_value = [] )
    {
        $keys = array_keys($list);
        $vals = array_values($list);
        array_unshift($keys,0);
        array_unshift($vals,'根目录');
        $arr = array_combine($keys,$vals);
        $data = [
            [
                'type' => 'input',
                'name' => 'name',
                'label' => '名称',
                'require' => true,
            ], [
                'type' => 'select',
                'name' => 'parent_id',
                'label' => '上级导航',
                'options' => $arr,
            ], [
                'type' => 'input',
                'name' => 'url',
                'label' => '链接',
            ], [
                'type' => 'radio',
                'name' => 'is_blank',
                'label' => '是否打开新窗口',
                'options' => [ '0' => '否', '1' => '是' ],

            ], [
                'type' => 'number',
                'name' => 'sort_num',
                'label' => '排序值',
                'info' => '默认为0，数值越大越靠前',
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}