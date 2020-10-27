<?php

namespace app\common\model;

class Article extends BaseModel
{
    protected $table = 'article';
    static $sort_map = 'sort_num desc,edit_time desc';
    function setImageAttr($value)
    {
        return remove_image_prefix($value);
    }
    function getImageAttr($value)
    {
        return add_image_prefix($value);
    }
    public function getStatusTextAttr($value, $data)
    {
        return $this->transferStatusText($data['status'], 'switch');
    }
    function category()
    {
        return $this->belongsTo('\app\common\model\ArticleCategory', 'category_id');
    }
    function nav()
    {
        return $this->belongsTo('\app\common\model\Nav', 'nav_id');
    }
    static function getByNav($nav_id, $per_page = 6)
    {
        $model = self::where('is_del',0)->where('type',1)->order(self::$sort_map);
        if($nav_id) {
            if(is_array($nav_id)) {
                $model->where('nav_id','in', $nav_id);
            }else {
                $model->where(compact('nav_id'));
            }
        }
        return $model->paginate($per_page);
    }
    static function getOneByNav($nav_id)
    {
        return self::where(compact('nav_id'))->where('is_del',0)->find();
    }
}