<?php

namespace app\common\model;

class StudentAccount extends BaseModel
{
    protected $table = 'student_account';

    static function getByStudentId($student_id)
    {
        $find = self::where(compact('student_id'))->find();
        if(!$find) {
            $student_id = self::insertGetId([
                'student_id' => $student_id
            ]);
            $find = self::where(compact('student_id'))->find();
        }
        return $find;
    }
//    // 给学生增加积分
//    static function addPoints($student_id, $point)
//    {
//        $account = self::getByStudentId($student_id);
//        $account->point += abs($point);
//        return $account->save();
//    }
}