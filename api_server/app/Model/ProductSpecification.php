<?php

namespace App\Model;

use Illuminate\Database\Eloquent\SoftDeletes;

class ProductSpecification extends BaseModel
{
    use SoftDeletes;
    public $timestamps = false;
    protected $table='product_specifications';
    protected $fillable = [
        'product_id',
        'name',
        'price',
        'courses_quantity',
        'sn',
    ];
    public function getPriceAttribute($value)
    {
        return floor($value)==$value ? intval($value) : $value;
    }
    public function orderItem()
    {
        return $this->morphMany('App\Model\OrderItem', 'item');
    }
    public function courses()
    {
        return $this->hasMany('App\Model\Course' , 'product_spec_id');
    }
    public function product()
    {
        return $this->belongsTo('App\Model\Product');
    }

    // 保存和更新规格的时候的操作
    public static function handelSave(Product $product, $specs)
    {
        self::where('product_id', $product->id)->delete();

        foreach ($specs as $v) {
            if (isset($v['id']) && $v['id'] && $v['price']){
                $where = [
                    'id' => $v['id'],
                    'product_id' => $product->id,
                ];
                ProductSpecification::where($where)->restore(); // 取消软删除
                ProductSpecification::where($where)->update($v);
            } else {
                $product->specs()->create($v);
            }
        }
    }
}
