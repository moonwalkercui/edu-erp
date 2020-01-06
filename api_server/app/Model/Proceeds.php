<?php

namespace App\Model;

class Proceeds extends BaseModel
{
    protected $table = 'proceeds';
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'proceeds_money');
    }
    public function log()
    {
        return $this->hasMany('App\Model\ProceedsLog', 'proceeds_id');
    }
//    public function user()
//    {
//        return $this->belongsTo('App\Model\UserProfile', 'user_id', 'user_id')->select('user_id','username','name');
//    }
    public function member()
    {
        return $this->belongsTo('App\Model\MemberProfile', 'member_id', 'member_id')->select('member_id','nick_name','name','mobile');
    }
    public function target()
    {
        return $this->morphTo();
    }
    public function division()
    {
        return $this->belongsTo('App\Model\Divisions','division_id')->select('id','name');
    }
    public static function getIdBySn($sn)
    {
        return self::where('sn', $sn)->value('id');
    }
   
}
