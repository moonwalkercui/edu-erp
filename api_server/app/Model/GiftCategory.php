<?php

namespace App\Model;

class GiftCategory extends BaseModel
{
    public $timestamps = false;
    protected $table='gift_category';
    protected $fillable = [
        'cate_name',
        'sort',
    ];
    public function gifts()
    {
        return $this->hasMany('App\Model\Gift','category_id')->select('id','name','points','storage','image','category_id');
    }
}
