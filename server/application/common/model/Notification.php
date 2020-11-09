<?php

namespace app\common\model;

class Notification extends BaseModel
{
    protected $table = 'notification';
    public function getImageAttr($value)
    {
        return add_image_prefix($value);
    }
    public function setImageAttr($value)
    {
        return remove_image_prefix($value);
    }
}