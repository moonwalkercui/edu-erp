<?php

namespace app\common\model;


trait BelongsToGrade
{
    public function grade()
    {
        return $this->belongsTo(Grade::class, 'grade_id');
    }
}