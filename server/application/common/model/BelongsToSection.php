<?php

namespace app\common\model;

trait BelongsToSection
{
    public function section()
    {
        return $this->belongsTo(Section::class, 'section_id')->field('id,title');
    }
}