<?php

namespace App\Model;

use Illuminate\Database\Eloquent\SoftDeletes;

class ProductGroupBuy extends BaseModel
{
    use SoftDeletes;
    protected $table='product_group_buy';
    public $timestamps = false;
    protected $fillable = [
        'product_id',
        'spec_id',
        'price',
    ];
}
