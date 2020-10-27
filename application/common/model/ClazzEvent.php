<?php
namespace app\common\model;

class ClazzEvent extends BaseModel
{
    use BelongsToStudent, BelongsToClazz, BelongsToStaff;
    protected $table = 'clazz_event';

    const TYPE_COURSE = 0;
    const TYPE_TEST = 1;
    const TYPE_MEDIA = 2;
    const TYPE_POINT= 3;

    static function addOne($content, $clazz_id, $teacher_id, $type, $target_id = null, $student_id = null, $date = null)
    {
        $data = compact('content','clazz_id','teacher_id','student_id','date','type','target_id');
        $data['date'] = $data['date'] == null ? now(true) : $date;
        $data['add_time'] = now();
        self::insert($data);
    }
}