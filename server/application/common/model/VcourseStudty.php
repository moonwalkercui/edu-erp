<?php
namespace app\common\model;

class VcourseStudty extends BaseModel
{
    use BelongsToStudent;
    protected $table = 'vcourse_study';
    function vcourse()
    {
        return $this->belongsTo(Vcourse::class, 'vcourse_id');
    }

    static function isDone($vcourse_id, $student_id, $percent=0) {
        $map = compact('vcourse_id','student_id');
        $find = self::where($map)->find();

        if($find == null) {
            $map['add_time'] = now();
            $map['done'] = 0;
            self::insert($map);
            return false;
        }

        if($find['done'] != 1 && $percent > 0) {
            $find->percent = $percent;
            $find->save();
        }

        return $find['done'] == 1;
    }

    static function handleDone($vcourse_id, $student_id) {
        $map = compact('vcourse_id','student_id');
        return self::where($map)->update([
            'percent' => 100,
            'done' => 1,
            'done_time' => now(),
        ]);
    }

}