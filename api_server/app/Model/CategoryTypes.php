<?php

namespace App\Model;

/*分类的类型模型*/
class CategoryTypes extends BaseModel
{
    public $timestamps = false;
    protected $table = 'category_types';
    protected $fillable = [
        'name'
    ];
    public function properties()
    {
        return $this->hasMany('App\Model\CategoryProperty' , 'type_id');
    }
    public function categories()
    {
        return $this->belongsToMany('App\Model\Category' , 'category_type' , 'type_id' , 'category_id');
    }
}
