<?php

namespace app\common\model;

trait BelongsToQuestion
{
    function question()
    {
        return $this->belongsTo(Question::class, 'question_id')->field('id,body,type,group_id,answer,explain');
    }
}