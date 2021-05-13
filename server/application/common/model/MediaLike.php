<?php

namespace app\common\model;

class MediaLike extends BaseModel
{
    protected $table = 'media_like';
    static function addOne($media_id, $uid)
    {
        $map = compact('media_id','uid');
        $find = self::where($map)->find();
        if($find) return [ self::where($map)->delete(), false, self::where(compact('media_id'))->count()]; // unlike
        else {
            $map['add_time'] = now();
            return [self::insert($map), true , self::where(compact('media_id'))->count()];  // like
        }
    }
}