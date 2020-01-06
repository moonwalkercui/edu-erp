<?php

namespace App\Model;

class Zone extends BaseModel
{
    protected $table='zone';
    public $timestamps = false;
    protected $fillable = ['content','uid','avatar','images','edit_time'];
    public function user()
    {
        return $this->belongsTo('App\Model\UserProfile','uid','user_id');
    }
    public function getAvatarAttribute($value)
    {
        return addImagePrefix($value);
    }
    public function getImagesAttribute($value)
    {
        $imgs = explode(',', rtrim($value,','));
//        $imgs = [
//            'upload/api/img/p_album/20190515/gKEfehtccog3hBsPEOXWFqd7tq9R2WVndJN899su.jpeg',
////            'upload/api/img/p_album/20190515/UehORLo9wPsAd9OWXttxqQ6LzHaJr3SufVZYc8cV.jpeg',
////            'upload/api/img/p_album/20190515/VT9yruCmNOrL24dhfD7RetJj3hm4fcRHYJiOdR9J.jpeg',
////            'upload/api/img/p_album/20190515/mC1Tg93CKNoajJRbaHzmgU6yp8rMf6XpxhqJANhN.jpeg'
//        ];
        if($value && !empty($imgs)){
            foreach ($imgs as &$img) {
                $img = addImagePrefix($img);
            }
        }
        return $value ? $imgs : [];
    }
    public function logs()
    {
        return $this->hasMany('App\Model\ZoneLog' , 'zone_id');
    }
    public static function handleLike($zone_id, $member_id)
    {
        $map = [
            'zone_id' => $zone_id,
            'member_id' => $member_id,
        ];
        $log = ZoneLog::where($map)->first();
        if($log){
            ZoneLog::where($map)->delete();
        } else {
            ZoneLog::insert($map);
        }
//        return $log ? false : true;
        return [
            'msg' => $log ? '已取消' : '已赞',
            'num' => ZoneLog::where(['zone_id' => $zone_id])->count()
        ];
    }

}
