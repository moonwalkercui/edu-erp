<?php

namespace app\common\model;


trait BelongsToClazz
{
    public function clazz()
    {
        return $this->belongsTo(Clazz::class, 'clazz_id')->field('id,name');
    }
}