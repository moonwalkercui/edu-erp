<?php
namespace app\backend\model;
trait FormDateTrait
{
    // 组合表单默认值
    protected static function mergeDefaultFormData($data, $default_value)
    {
        array_push($data, [
            'type' => 'hidden',
            'name' => 'id',
            'label' => '',
        ]);
        foreach ($data as &$v) {
            if (!empty($default_value) && isset($default_value[$v['name']])) {
                $v['value'] = $default_value[$v['name']];
            }else{
                $v['value'] = null;
            }
        }
        return $data;
    }
    // 包装分类
    protected static function categoryWrap($list, $root = '根目录')
    {
        $keys = array_keys($list);
        $vals = array_values($list);
        if($root) {
            array_unshift($keys,0);
            array_unshift($vals, $root);
        }
        return array_combine($keys,$vals);
    }
}