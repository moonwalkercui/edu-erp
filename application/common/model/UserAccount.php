<?php

namespace app\common\model;

class UserAccount extends BaseModel
{
    protected $table = 'user_account';

    static function getByStudentId($student_id)
    {
        $user = Student::find($student_id);
        if(!$user) exception('未知UID');
        return self::where('user_id', $user->id)->find();
    }
//    // 给学生增加积分
//    static function addPoints($student_id, $point)
//    {
//        $account = self::getByStudentId($student_id);
//        $account->point += abs($point);
//        return $account->save();
//    }
}