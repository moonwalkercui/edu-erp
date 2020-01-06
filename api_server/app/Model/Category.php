<?php

namespace App\Model;

class Category extends BaseModel
{
    public $timestamps = false;
    protected $table = 'categories';
    protected $fillable = [
        'name',
        'parent_id',
        'parent_ids',
    ];
    public function types()
    {
        return $this->belongsToMany('App\Model\CategoryTypes' , 'category_type'  , 'category_id', 'type_id');
    }
    public function products()
    {
        return $this->hasMany('App\Model\Product','category_id');
    }
//    public function badges()
//    {
//        return $this->hasMany('App\Model\Badge','category_id');
//    }
}
