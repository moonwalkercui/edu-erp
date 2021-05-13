<?php
namespace app\common\model;

class ClazzNoticeRead extends BaseModel
{
    protected $table = 'clazz_notice_read';

    /*
    * 阅读
    * */
    public static function checkRead($notice_id, $student_id) : bool
    {
        $map = compact('notice_id','student_id');
        $item = ClazzNoticeRead::where($map)->find();
        return $item ? true : false;
    }
}