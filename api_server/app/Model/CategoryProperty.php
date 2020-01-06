<?php

namespace App\Model;

class CategoryProperty extends BaseModel
{
    public $timestamps = false;
    protected $table='category_properties';
    protected $fillable = [
        'name',
        'type_id',
    ];
}
