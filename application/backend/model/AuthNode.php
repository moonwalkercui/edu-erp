<?php
namespace app\backend\model;
use think\Model;

class AuthNode extends Model {
    public function role()
    {
        return $this->belongsToMany('\app\backend\model\AuthRole','auth_node_role','role_id','node_id');
    }
    protected $node_level = [
        ['level'=>1,'name'=>'模型'],
        ['level'=>2,'name'=>'控制器'],
        ['level'=>3,'name'=>'方法'],
    ];
    public function getLevels(){
        return $this->node_level;
    }
    //获取节点树
    public static function getNodes()
    {
        return self::order('sort')->select();
    }
    public static function getMenus($pid)
    {
        return self::order('sort')->where('parent_id', $pid)->where('is_menu',1)->select(); // ->cache('system_menu') todo
    }
    public static function getAll()
    {
        return self::order('sort')->where('is_menu',1)->select(); // ->cache('system_menu') todo
    }
    public static function getNodeTree(){
        $n_list = self::getNodes();
        return list_to_child($n_list->toArray());
    }
    public static function getNodeMenuTree($pid = null){
        if($pid === null) $pid = self::where('parent_id', 0)->order('sort')->value('id');
        return list_to_child(self::getAll(), $pid);
    }

}