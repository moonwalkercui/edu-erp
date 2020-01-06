<?php

namespace App\Model;

class MemberVoucher extends BaseModel
{
    protected $table='member_voucher';
    public $timestamps = false;
    public function getBeUsedAttribute($value)
    {
        return getStatusText($value, 'used');
    }
    public function voucher()
    {
        return $this->belongsTo('App\Model\Voucher')->select('id','name','catch_price','price');
    }
    public function member()
    {
        return $this->belongsTo('App\Model\MemberProfile','member_id','member_id')->select('member_id','name','nick_name');
    }

}
