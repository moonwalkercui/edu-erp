<?php
namespace App\Model;
use App\Exceptions\ApiException;
use Illuminate\Database\Eloquent\SoftDeletes;
class Goods extends BaseModel
{
    use SoftDeletes;
    protected $table = 'goods';

    public function getImageAttribute($value)
    {
        return addImagePrefix($value);
    }
    public function category()
    {
        return $this->belongsTo('App\Model\GoodsCategory','category_id');
    }
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'product');
    }
    // $post_code: [{id: 10, num: 2}, {id: 3, num: 1}, {id: 1, num: 1}]
    public static function getTotalPrice($post_info)
    {
        $post_data = json_decode($post_info, true);
        $ids = $temp = [];
        foreach ($post_data as $v) {
            $ids[] = $v['id'];
            $temp[$v['id']] = $v['num'];
        }

        $list = Goods::whereIn('id',$ids)->get()->toArray();

        $total = 0;
        foreach ($list as &$l) {
            $num = $temp[$l['id']];
            if ($num > $l['storage']) throw new ApiException('商品库存不足:'.$l['name']);
            $l['num'] = $num;
            $l['total'] = $num * $l['price'];
            $total +=  $l['total'];
        }
        return [$list, $total];
    }
    public static function incSaleQuantityByOrderId($order_id)
    {
        $order_items = OrderItem::where('order_id', $order_id)->get();
        foreach ($order_items as $item) {
            self::where('id', $item->item_id)->increment('sale_quantity', $item->num);
            self::where('id', $item->item_id)->decrement('storage', $item->num);
        }
    }
}
