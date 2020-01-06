<?php
namespace App\Model;

class GoodsCategory extends BaseModel
{
    public $timestamps = false;
    protected $table = 'goods_categories';
    public function goods()
    {
        return $this->hasMany('App\Model\Goods','category_id');
    }

}
