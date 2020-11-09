<?php

namespace app\common\model;


trait BelongsToUser
{
    public function user()
    {
        return $this->belongsTo(User::class, 'user_id');
    }
}