<?php

namespace App\Model;

class ProductImage extends BaseModel
{
    protected $table='product_images';
    protected $fillable = [
        'product_id',
        'name',
        'url',
        'size',
    ];
    public function getUrlAttribute($value)
    {
        return addImagePrefix($value);
    }
    public static function handleSave($product_id, $images)
    {
        self::where('product_id',$product_id)->delete();
        if(!empty($images)){
            $imgArr = [];
            foreach ($images as $i){
                $imgArr[] = [
                    'product_id' => $product_id,
                    'name' => $i['name'],
                    'url' => cleanImagePrefix($i['url']),
                    'size' => $i['size'],
                ];
            }
            ProductImage::insert($imgArr);
        }
    }
}
