<?php

namespace App\Model;

class UserPhoto extends BaseModel
{
    protected $table='user_photo';
    public function getImageAttribute($value)
    {
        return addImagePrefix($value);
    }
}
