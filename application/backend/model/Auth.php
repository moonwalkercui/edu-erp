<?php
namespace app\backend\model;

use app\common\model\Staff;

class Auth extends Staff {
    use FormDateTrait;
	//多对多关联 角色-用户
	public function role()
    {
        return $this->belongsToMany('\app\backend\model\AuthRole','auth_role_staff','role_id','staff_id');
    }
    public function getStatusTextAttr($value, $data)
    {
        $status = config('status.normal');
        return $status[$data['status']];
    }
    //关联新增
    public function relationAdd($id,$roles){
        $user = self::get($id);
        $res = $user->role()->saveAll($roles);
        return $res;
    }
    static function getAuthNodeIds($admin_id)
    {
        $user = self::get($admin_id, 'role');
        $roles = $user->role;
        if(empty($roles)) return [];
        $r_ids = [];
        foreach ($roles as $role) {
            $r_ids[] = $role->id; // 获得角色id
        }
        if(empty($r_ids)) return [];
        $nodes = AuthRole::selectNodes($r_ids);
        if(empty($nodes)) return [];
        return $nodes;
    }
    //通过uid获取node列表
//    public static function getAuthNodes($admin_id)
//    {
//	    if($nodes = cache(config('status.auth.user_auth_nodes'))) return $nodes;
//        //获取用户的管理组角色id
//        $user = self::get($admin_id, 'role');
//        $roles = $user->role;
//        if($roles==false) return 'norole';
//        $r_ids = [];
//        foreach ($roles as $role) {
//            $r_ids[] = $role->id; // 获得角色id
//        }
//        $r_ids = self::getAuthNodeIds($admin_id);
//        if(empty($r_ids)) return 'nonode';
//        //获取角色的节点
//        $nodes = AuthRole::selectNodes($r_ids);
//        if($nodes == false) return 'nonode';
//
//        cache(config('status.auth.user_auth_nodes'),$nodes); //把节点放到session里
//        return $nodes;
//    }
    public static function makeFromData($default_value = [])
    {
        $data = [
            [
                'type' => 'input',
                'name' => 'name',
                'label' => '姓名',
                'placeholder' => '也用于登录账号',
                'require' => true
            ],
            [
                'type' => 'password',
                'name' => 'password',
                'label' => '密码',
            ],
            [
                'type' => 'input',
                'name' => 'phone',
                'label' => '手机号',
            ],
            [
                'type' => 'image',
                'name' => 'photo',
                'label' => '头像',
            ],
            [
                'type' => 'checkbox',
                'name' => 'role_id',
                'label' => '管理组',
                'options' => AuthRole::where('status',1)->column('name','id')
            ],
            [
                'type' => 'radio',
                'name' => 'status',
                'label' => '状态',
                'options' => config('status.normal'),
            ],
//            [
//                'type' => 'number',
//                'name' => 'sort_num',
//                'label' => '排序值',
//                'placeholder' => '越大越靠前'
//            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
    static function handleMistakePassword(Auth $item)
    {
        $item->mistake_times ++;
        if($item->mistake_times >= 5) {
            $item->status = 0;
        }
        $item->save();
        return 5 - $item->mistake_times;
    }
}