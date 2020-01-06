<?php namespace App\Model;


class OrderRefund extends BaseModel
{
    protected $table = 'order_refund';
    public $timestamps = false;
    protected $fillable = [
        'order_id' ,
        'refund_sn' ,
        'total_fee' ,
        'refund_fee' ,
        'created_time' ,
        'status',
    ];
    public function order()
    {
        return $this->belongsTo('App\Model\Order','order_id');
    }
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'refund');
    }
    public static function reject($id, $reason)
    {
        $refund = self::where('id', $id)->first();
        Order::where('id', $refund->order_id)->update([
            'status' => 1,
        ]);
        $refund->status = -1;
        $refund->reject_reason = $reason;
        return $refund->save();
    }
    public static function accept($id)
    {
        return self::where('id', $id)->update([
            'status' => 1,
        ]);
    }

}