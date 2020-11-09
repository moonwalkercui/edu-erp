<?php

namespace app\common\model;

class UserLog extends BaseModel
{
    protected $table = 'user_log';
    static function addOne($user_id, $remark)
    {
        $map = compact('user_id','remark');
        $map['add_time'] = now();
        self::insert($map);
    }
}