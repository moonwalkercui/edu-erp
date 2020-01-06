<?php

namespace App\Model;


class Advertisement extends BaseModel
{
    protected $fillable = [ 'title', 'image','position' ];
    // 注意是数组
    public static function getByPosition($p_name)
    {
        return Advertisement::where('position', $p_name)->get();
    }
    public static function getOneByPosition($p_name)
    {
        return Advertisement::where('position', $p_name)->first();
    }
    public static function getById($id)
    {
        return Advertisement::find($id);
    }
    public function getImageAttribute($value)
    {
        return addImagePrefix($value);
    }
    public function getPositionAttribute($value)
    {
        return getStatusText($value, 'adv');
    }
}
