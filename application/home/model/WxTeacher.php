<?php
namespace app\home\model;

use think\Model;

class WxTeacher extends Model
{
    protected $table = 'el_zy_teacher';
    protected $connection = 'wx_db';
    static function getList($limit)
    {
        $list = self::with('avatar')->where('is_del',0)->order('best_sort asc')->limit($limit)->select();
        foreach ($list as $v) {
            $v->image = UPLOAD_PATH . '/' . $v->avatar['save_path'] . $v->avatar['save_name'];
        }
        return $list;
    }
    function avatar()
    {
        return $this->belongsTo(WxAttach::class, 'head_id', 'attach_id');
    }
}