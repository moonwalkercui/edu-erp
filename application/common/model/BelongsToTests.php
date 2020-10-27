<?php

namespace app\common\model;

trait BelongsToTests
{
    public function tests()
    {
        return $this->belongsTo(Test::class, 'test_id');
    }
}