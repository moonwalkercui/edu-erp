<?php namespace App\Model;
class Order extends BaseModel
{
    protected $table = 'orders';
    protected $fillable = [
        'sn',
        'pay_sn',
        'member_id',
        'username',
        'user_name',
        'total_price',
        'pay_money',
        'balance_pay',
        'cut_money',
        'product_price',
        'other_price',
        'pay_type',
        'points',
        'type',
        'remark',
//        'badge_ids',
    ];
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'order');
    }
    public function getTypeAttribute($value)
    {
        return getStatusText($value,'order_type');
    }
    public function getPayTypeAttribute($value)
    {
        return getStatusText($value,'pay_type');
    }
    public function items()
    {
        return $this->hasMany('App\Model\OrderItem','order_id');
    }
    public function refunds()
    {
        return $this->hasMany('App\Model\OrderRefund','order_id');
    }
    public function proceeds()
    {
        return $this->morphOne('App\Model\Proceeds', 'target');
    }
    public function member()
    {
        return $this->belongsTo('App\Model\MemberProfile', 'member_id', 'member_id');
    }
    public static function handelPay($pay_sn)
    {
        $order = Order::where('pay_sn', $pay_sn)->first();
        $order->status = 1;
        $order->paid_at = now();
        $order->save();
//        Order::where('pay_sn', $pay_sn)->update(['status' => 1, 'paid_at' => now()]);
//      'order_type' => [ 1 =>'团体课订单', 2 =>'一对一课订单', 3 =>'商品订单' ],
        if (in_array($order->getOriginal('type'), [1, 2])) {
            MemberProduct::incByOrder( $order->id, $order->member_id );
        }
        // 商品订单
        if ($order->getOriginal('type') == 3) {
            Goods::incSaleQuantityByOrderId( $order->id );
        }
    }
}