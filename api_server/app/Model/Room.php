<?php

namespace App\Model;

class Room extends BaseModel
{
    protected $table = 'rooms';
    public $timestamps = false;
    protected $fillable = [
        'division_id',
        'name',
        'position',
    ];
    public function division()
    {
        return $this->belongsTo('App\Model\Divisions','division_id')->select('id','name');
    }
}
