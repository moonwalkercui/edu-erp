<?php

namespace app\common\model;

class Media extends BaseModel
{
    use BelongsToClazz;
    protected $table = 'media';
    function getImagesAttr($value, $data)
    {
        return $value ? explode(',', $value) : '';
    }
    public function staff()
    {
        return $this->belongsTo(Staff::class, 'editor_id');
    }
//    function getVideoUrlAttr($value, $data)
//    {
//        return $value ? explode(',', $value) : '';
//    }
}