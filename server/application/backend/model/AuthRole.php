<?php

namespace app\backend\model;

use think\Model;

class AuthRole extends Model
{
    use FormDateTrait;
    protected $table = 'auth_role';

    //多对多关联 角色-用户
    public function adm()
    {
        return $this->belongsToMany('\app\backend\model\Adm', 'auth_role_staff', 'staff_id', 'role_id');
    }

    public function node()
    {
        return $this->belongsToMany('\app\backend\model\AuthNode', 'auth_node_role', 'node_id', 'role_id');
    }

    //通过角色id查询节点
    public function getNodes($id)
    {
        $role = $this::get($id, "node");
        return collection($role->node)->toArray();
    }
    public function getNodeIds($id)
    {
        $role = $this::get($id, "node");
        $ids = [];
        foreach ($role->node as $v) {
            $ids[] = $v->id;
        }
        return $ids;
    }

    //批量查询角色的节点列表
    public static function selectNodes($r_ids)
    {
        //获取角色的节点
        $list = self::with("node")->select($r_ids);
        foreach ($list as $n) { // 遍历角色
            foreach ($n->node as $v) { // 遍历节点
                $ids[] = $v->id;  // 获取节点name
            }
        }
        $ids = array_unique($ids);
        return $ids;
    }

    //批量增加节点
    public function addNodes($id, $nodes)
    {
        $role = $this::get($id);
        $role->node()->saveAll($nodes);
    }

    public function getList()
    {
        $list = $this::all(['status' => 1]);
        return $list;
    }

    public static function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'input',
                'name' => 'name',
                'label' => '名称',
                'verify' => 'name'
            ],
            [
                'type' => 'input',
                'name' => 'remark',
                'label' => '备注',
            ],
            [
                'type' => 'radio',
                'name' => 'status',
                'label' => '状态',
                'options' => config('status.normal'),
            ],
        ];
//        $data = [
//            [
//                'type' => 'input',
//                'name' => 'name',
//                'label' => '店面名称',
//                'info' => '',
//                'verify' => 'required'
//            ],
//            [
//                'type' => 'input',
//                'name' => 'mobile',
//                'label' => '联系电话',
//                'info' => '输入电话号码',
//            ],
//            [
//                'type' => 'select',
//                'name' => 'category',
//                'label' => '选择',
//                'options' => [
//                    'a' => '食品',
//                    'b' => '服装',
//                    'c' => '手机',
//                ],
//            ],
//            [
//                'type' => 'date',
//                'name' => 'birthday',
//                'label' => '生日',
//                'info' => '输选择生日',
//            ],
//            [
//                'type' => 'switch',
//                'name' => 'switch',
//                'label' => '开关',
//                'options' => '开|关',
//            ],
//            [
//                'type' => 'radio',
//                'name' => 'radio',
//                'label' => '单选框',
//                'options' => [
//                    'a' => '食品',
//                    'b' => '服装',
//                    'c' => '手机',
//                ],
//            ],
//            [
//                'type' => 'checkbox',
//                'name' => 'checkbox',
//                'label' => '多选框',
//                'options' => [
//                    'a' => '食品',
//                    'b' => '服装',
//                    'c' => '手机',
//                ],
//            ],
//            [
//                'type' => 'textarea',
//                'name' => 'content',
//                'label' => '编辑器'
//            ],
//            [
//                'type' => 'uploader',
//                'name' => 'picture1',
//                'multi' => false,
//                'label' => '上传单图'
//            ],
//            [
//                'type' => 'uploader',
//                'name' => 'picture2',
//                'multi' => true,
//                'label' => '上传多图',
//                'max' => 5,
//                'info' => '最多保存前5张'
//            ],
//        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}