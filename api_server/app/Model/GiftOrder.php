<?php

namespace App\Model;

class GiftOrder extends BaseModel
{
    public $timestamps = false;
    protected $table='gift_order';
    protected $fillable = [
        'gift_id',
        'gift_num',
        'points',
        'member_id',
        'created_at',
        'stage',
        'remark',
    ];
    public function getStageAttribute($value)
    {
        return getStatusText($value, 'gift_order');
    }
    public function gift()
    {
        return $this->belongsTo('App\Model\Gift','gift_id')->select('id','name','points','image');
    }
    public function member()
    {
        return $this->belongsTo('App\Model\MemberProfile','member_id','member_id')->select('member_id','nick_name');
    }
}
