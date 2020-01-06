<?php

namespace App\Model;

class JoinApplicant extends BaseModel
{
    public $timestamps = false;
    protected $table='join_applicants';
    protected $fillable = [
        'username',
        'remark',
        'created_at',
        'user_name',
    ];
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'applicant');
    }
}
