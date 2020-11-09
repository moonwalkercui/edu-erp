<?php
namespace app\common\model;
use think\Model;

class Region extends Model
{
    protected $table = 'region';

    public static function getListByPid($pid = 0)
    {
        $list = self::where('pid', $pid)->select();
        return $list;
    }
    public static function getNameById($id)
    {
        return self::where('id', $id)->value('name');
    }
    public static function getTree($province_id = 1375)
    {
        $data = self::field('id,pid,name')->select()->toArray();
        return list_to_child($data,$province_id,'pid');
    }
}