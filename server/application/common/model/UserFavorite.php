<?php

namespace app\common\model;

class UserFavorite extends BaseModel
{
    protected $table = 'user_favorite';
    function case()
    {
        return $this->belongsTo(Cases::class, 'case_id');
    }
    // 收藏与取消
    static function toggleAdd($user_id, $case_id)
    {
        $map = compact('user_id','case_id');
        if(self::beFavorite($user_id, $case_id)) {
            return self::where($map)->delete();
        } else {
            $map['add_time'] = now();
            return self::insert($map);
        }
    }
    // 检查是否已收藏
    static function beFavorite($user_id, $case_id):bool
    {
        $map = compact('user_id','case_id');
        return self::where($map)->count() > 0;
    }
    static function getCaseByUid($user_id)
    {
        return self::where(compact('user_id'))->with('case')->paginate(20, true);
    }
}