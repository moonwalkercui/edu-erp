<?php

namespace App\Model;

use Illuminate\Database\Eloquent\SoftDeletes;

class ProductMaterial extends BaseModel
{
    use SoftDeletes;
    protected $table='product_materials';
    protected $fillable = [
        'product_id',
        'material_id',
        'quantity',
    ];
}
