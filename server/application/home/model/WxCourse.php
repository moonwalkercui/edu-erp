<?php
namespace app\home\model;

use think\Model;

class WxCourse extends Model
{
    protected $table = 'el_zy_video';
    protected $connection = 'wx_db';
    static function getList($limit, $cate_id = null)
    {
        $model = self::where('is_del',0)->where('type','in','1,2')->order('best_sort asc')->limit($limit);
        if($cate_id) {
            return $model->where('video_category', $cate_id)->select();
        } else {
            return $model->select();
        }
    }
}