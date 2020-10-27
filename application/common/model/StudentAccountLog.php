<?php

namespace app\common\model;

class StudentAccountLog extends BaseModel
{
    protected $table = 'student_account_log';
    const TYPE = [
        'balance' => 1,
        'point' => 2
    ];
    public function getStageTextAttr($value, $data)
    {
        return $this->transferStatusText($data['stage'], 'account_log_stage');
    }
    // 写日志
    public static function addOne($student_id, $type, $amount, $remark='')
    {
        return self::insert([
            'student_id' => $student_id,
            'type' => $type,
            'amount' => $amount,
            'add_time' => now(),
            'remark' => $remark,
            'remaining_point' => StudentAccount::where(compact('student_id'))->value('point'),
        ]);
    }

}