<?php
namespace app\common\model;

class ZoneComment extends BaseModel
{
    protected $table = 'zone_comment';
    function publisher()
    {
        return $this->belongsTo(Student::class, 'publisher_id')->field('id,name,avatar');
    }
    static function addOne($publisher_id, $zone_id, $content)
    {
        $map = compact('zone_id','publisher_id');
        if($content == '') {
            return self::where($map)->delete();
        }
        $map['content'] = $content;
        $map['add_time'] = now();
        return self::insert($map);
    }
    static function getList($where, $student_id)
    {
        return self::with('publisher')->where($where)->select()->each(function($item) use ($student_id){
            $item->is_my = $item['publisher_id'] == $student_id ? 1 : 0;
        });
    }
}