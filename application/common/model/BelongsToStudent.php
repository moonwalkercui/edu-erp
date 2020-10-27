<?php

namespace app\common\model;

trait BelongsToStudent
{
    public function student()
    {
        return $this->belongsTo(Student::class, 'student_id');
    }
    public function studentBind()
    {
        return $this->belongsTo(Student::class, 'student_id')->bind(['student_name' => 'name','student_avatar' => 'avatar']);
    }
}