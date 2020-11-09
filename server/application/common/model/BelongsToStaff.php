<?php

namespace app\common\model;
trait BelongsToStaff
{
    public function staff()
    {
        return $this->belongsTo(Staff::class, 'staff_id')->field('id,name,photo');
    }
}