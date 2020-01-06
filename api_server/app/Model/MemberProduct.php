<?php

namespace App\Model;

class MemberProduct extends BaseModel
{
    protected $table = 'member_product';
    public $timestamps = false;
    public function product()
    {
        return $this->belongsTo('App\Model\Product')->select('id', 'name', 'expired_at');
    }
    public function member()
    {
        return $this->belongsTo('App\Model\MemberProfile', 'member_id', 'member_id')->select('member_id', 'name', 'nick_name', 'mobile');
    }
    public static function getProductIds($mid, $gt_zero = true)
    {
        return $gt_zero
            ? self::where('member_id', $mid)
                ->where('remaining_quantity','>',0)
                ->pluck('product_id')
            : self::where('member_id', $mid)
                ->pluck('product_id');
    }
    // sign一次执行一次
    public static function decQuantity($mid, $pid)
    {
        return self::where('member_id', $mid)->where('product_id', $pid)->decrement('remaining_quantity');
    }
    // pay一次执行一次
    public static function incByOrder($order_id, $mid)
    {
        $order = Order::find($order_id);
        foreach ($order->items as $item) {
            self::incQuantity($mid, $item->item_id, $item->courses_quantity);
        }
    }
    public static function incQuantity($mid, $pid, $quantity)
    {
        $item = self::where('member_id', $mid)->where('product_id', $pid)->first();
        if($item) {
            $item->total_quantity += $quantity;
            $item->remaining_quantity += $quantity;
            $item->save();
        } else {
            self::insert([
               'member_id' => $mid,
               'product_id' => $pid,
               'total_quantity' => $quantity,
               'remaining_quantity' => $quantity,
            ]);
        }
    }
    // 获取product的member列表
    public static function getProductMembers($mid, $pid, $gt_zero = true)
    {
        $where = [];
        if($gt_zero == false) {
            array_push( $where, ['remaining_quantity' , '>' , 0 ]) ;
        }
        return self::where('member_id', $mid)->where('product_id', $pid)->where($where)->get();
    }
    // 获取member的数量
    public static function getNumbers($mid, $pid)
    {
        return self::where('member_id', $mid)->where('product_id', $pid)->select('total_quantity', 'remaining_quantity')->first();
    }

}