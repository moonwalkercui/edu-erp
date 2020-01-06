<?php namespace App\Model;

class OrderItem extends BaseModel
{
    protected $table = 'order_items';
    public $timestamps = false;
//    protected $fillable = [
//        'order_id',
//        'item_id',
//        'item_type',
//        'item_name',
//        'deal_price',
//        'num',
//        'remark',
//        'created_at'
//    ];
    public function item()
    {
        return $this->morphTo();
    }
    public function order()
    {
        return $this->belongsTo('App\Model\Order','order_id');
    }

}