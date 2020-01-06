<?php

namespace App\Model;

use Illuminate\Database\Eloquent\SoftDeletes;

class Classes extends BaseModel
{
    use SoftDeletes;
    protected $table = 'classes';
    protected $fillable = [
        'sn',
        'division_id',
        'name',
        'description'
    ];
    public function division()
    {
        return $this->belongsTo('App\Model\Divisions','division_id');
    }
    public function members()
    {
        return $this->belongsToMany('App\Model\MemberProfile' , 'class_member' , 'class_id', 'member_id');
    }
    public function courses()
    {
        return $this->hasMany('App\Model\Course' , 'class_id');
    }
}
