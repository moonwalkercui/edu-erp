<?php
namespace App\Model;

use App\Exceptions\ApiException;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Support\Facades\Log;

class Voucher extends BaseModel
{
    use SoftDeletes;
    protected $table='vouchers';
    public $timestamps = false;
    protected $fillable = ['name','price','catch_price','image','term','invalid_at','created_at','status'];
    public function getStatusAttribute($value)
    {
        return getStatusText($value, 'switch');
    }
    public function members()
    {
        return $this->belongsToMany('App\Model\MemberProfile', 'member_voucher',  'voucher_id', 'member_id', null , 'member_id')->withPivot('be_used','got_time');
    }
    public function getImageAttribute($value)
    {
        return addImagePrefix($value);
    }
    public function log()
    {
        return $this->hasMany('App\Model\MemberVoucher');
    }
    //    使用优惠券
    public static function useVoucher($voucher_id, $member_id, $order_sn = null)
    {
        $res = MemberVoucher::where([
            'member_id' => $member_id,
            'voucher_id' => $voucher_id,
        ])->update([
            'be_used' => 1,
            'use_time' => now(),
            'order_sn' => $order_sn
        ]);
        if(false == $res) {
            Log::error('代金券使用失败'.'[voucher_id]'.$voucher_id.'[member_id]'.$member_id.'[order_sn]'.$order_sn);
        }
    }

    public static function encodeId($id)
    {
        return alphaID($id, false, 4);
    }
    public static function decodeId($code)
    {
        $id = alphaID($code, true, 4);
        if(null == self::where('id',$id)->where('invalid_at','>',now())->first())
            throw new ApiException('无效优惠券');
        return $id;
    }
}
