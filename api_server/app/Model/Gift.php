<?php

namespace App\Model;

use Illuminate\Database\Eloquent\SoftDeletes;

class Gift extends BaseModel
{
    use SoftDeletes;
    protected $table='gifts';
    protected $fillable = [
        'category_id',
        'name',
        'points',
        'content',
        'image',
        'storage',
        'status',
        'sort',
    ];
    public function getImageAttribute($value)
    {
        return addImagePrefix($value);
    }
    public function category()
    {
        return $this->belongsTo('App\Model\GiftCategory','category_id');
    }
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'switch');
    }
    // $post_code: [{id: 10, num: 2}, {id: 3, num: 1}, {id: 1, num: 1}]
    public static function getTotalPrice($post_info)
    {
        $post_data = json_decode($post_info, true);
        $ids = $temp = [];
        foreach ($post_data as $v) {
            $ids[] = $v['id'];
            $temp[$v['id']] = $v['num'];
        }

        $list = self::whereIn('id',$ids)->get()->toArray();

        $total = 0;
        foreach ($list as &$l) {
            $num = $temp[$l['id']];
            if ($num > $l['storage']) throw new ApiException('库存不足:'.$l['name']);
            $l['num'] = $num;
            $l['total'] = $num * $l['points'];
            $total +=  $l['total'];
        }
        return [$list, $total];
    }
}
