<?php
namespace app\common\model;

class ClazzNotice extends BaseModel
{
    use BelongsToClazz, BelongsToStaff;
    protected $table = 'clazz_notice';

    /*
     * 阅读
     * */
    public static function handleRead($id, $student_id)
    {
        $map = [
            'notice_id' => $id,
            'student_id' => $student_id,
        ];
        $item = ClazzNoticeRead::where($map)->find();
        if($item) return;

        $map['state'] = 1;
        $map['read_time'] = now();
        ClazzNoticeRead::insert($map);
    }

    function readLog()
    {
        return $this->hasMany(ClazzNoticeRead::class, 'notice_id');
    }

}