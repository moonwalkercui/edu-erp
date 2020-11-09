<?php

namespace app\common\model;

class UserLevel extends BaseModel
{
    protected $table = 'user_level';
    // 计算等级
    static function calByPoints($points)
    {
        $list = self::order('need_points desc')->cache('user_levels')->select();
        $find = null;
        foreach ($list as $v) {
            if($v['need_points'] <= $points) {
                $find = $v;
                break;
            }
        }
        if($find === null) self::find();
        return $find;
    }
}