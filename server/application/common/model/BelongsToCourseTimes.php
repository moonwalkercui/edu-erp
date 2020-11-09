<?php

namespace app\common\model;

trait BelongsToCourseTimes
{
    public function times()
    {
        return $this->belongsTo(CourseTimes::class, 'times_id');
    }
}