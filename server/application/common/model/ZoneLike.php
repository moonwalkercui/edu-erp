<?php
namespace app\common\model;

class ZoneLike extends BaseModel
{
    protected $table = 'zone_like';
    static function addOne($zone_id, $uid, $is_teacher=false)
    {
        $uid = $is_teacher ? 't'.$uid : $uid;
        $map = compact('zone_id','uid');
        $find = self::where($map)->find();
        if($find) return [ self::where($map)->delete(), false, self::where(compact('zone_id'))->count()]; // unlike
        else {
            $map['add_time'] = now();
            return [self::insert($map), true , self::where(compact('zone_id'))->count()];  // like
        }
    }
}