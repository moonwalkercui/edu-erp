<?php

namespace App\Model;

class JoinInvitation extends BaseModel
{
    public $timestamps = false;
    protected $table='join_invitations';
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'invitation');
    }
}
