<?php

namespace App\Model;

use Illuminate\Database\Eloquent\SoftDeletes;

class Product extends BaseModel
{
    use SoftDeletes;
    protected $table='products';
    protected $fillable = [
        'name',
        'price',
        'category_id',
        'category_ids',
        'price_original',
        'slogan',
        'description',
        'category_attributions',
        'sort',
        'image',
        'start_at',
        'end_at',
        'user_id',
        'purpose',
        'target',
        'faq',
        'expired_at',
        'attention',
        'recommend',
        'type',
        'is_groupbuy',
        'groupbuy_num',
        'groupbuy_start',
        'groupbuy_end',
        'red_packet_max',
    ];

    public function getStatusAttribute($value)
    {
        return getStatusText($value,'product');
    }
    public function getTypeAttribute($value)
    {
        return getStatusText($value,'product_type');
    }
    public function getRecommendAttribute($value)
    {
        return getStatusText($value,'is_yes');
    }
    public function getIsGroupbuyAttribute($value)
    {
        return getStatusText($value,'is_yes');
    }
    public function getImageAttribute($value)
    {
        return addImagePrefix($value);
    }
    // 根据类查询类别的产品
    public static function getByCate($cate_id , $per_page = 0)
    {
        $model = self::whereRaw('FIND_IN_SET(?,category_ids)', [$cate_id]);
        if($per_page > 0)
            return $model->paginate($per_page); // 带分页
        else
            return $model->get();
    }
    public function groupbuy()
    {
        return $this->hasMany('App\Model\ProductGroupBuy','product_id');
    }
    public function deal()
    {
        return $this->hasMany('App\Model\MemberProduct','product_id');
    }
    public function course()
    {
        return $this->hasMany('App\Model\Course','product_id');
    }
    // 教师列表
    public function users()
    {
        return $this->belongsToMany('App\Model\UserProfile' , 'courses'  , 'product_id', 'user_id', '', 'user_id')->distinct();
    }
    public function members()
    {
        return $this->belongsToMany('App\Model\MemberProfile','member_product', 'product_id', 'member_id');
    }
    public function courses()
    {
        return $this->hasMany('App\Model\Course' , 'product_id');
    }
    public function divisions()
    {
        return $this->belongsToMany('App\Model\Divisions', 'product_division'  , 'product_id', 'division_id');
    }
    public function properties()
    {
        return $this->belongsToMany('App\Model\CategoryProperty' , 'product_property'  , 'product_id', 'property_id');
    }
    public function materials()
    {
        return $this->belongsToMany('App\Model\Material' , 'product_materials'  , 'product_id', 'material_id')->withPivot('quantity');
    }
//    public function linkMaterials()
//    {
//        return $this->hasMany('App\Model\ProductMaterial' , 'product_id')->select('product_id', 'material_id as id' ,'quantity');
//    }
    public function images()
    {
        return $this->hasMany('App\Model\ProductImage' , 'product_id');
    }
    public function specs()
    {
        return $this->hasMany('App\Model\ProductSpecification' , 'product_id');
    }
    // 更新产品与属性的关联
    public static function saveProperties(self $product, $properties)
    {
        if(!empty($properties)){
            $product->properties()->sync($properties);
        }else{
            $product->properties()->detach();
        }
    }
    // 更新产品与物料的关联
    public static function saveMaterials(self $product, $materials)
    {
        if(!empty($materials)){
            foreach ($materials as $v) {
                $sync[$v['id']] = ['quantity' => $v['quantity']];
            }
            $product->materials()->sync($sync);
        }else{
            $product->materials()->detach();
        }
    }
    public static function getRecommend()
    {
        return self::with('users','specs')->where([
            'status' => 1,
            'recommend' => 1,
        ])->where(function($query){
            $query->whereNull('start_at')->orWhere(function ($q) {
                $today =  date('Y-m-d',time());
                $q->where('start_at', '<=', $today)->where('end_at', '>=', $today);
            });
        })->get();
    }
}
