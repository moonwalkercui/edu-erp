<?php

namespace App\Model;

use Illuminate\Database\Eloquent\SoftDeletes;

class Material extends BaseModel
{
    use SoftDeletes;
    protected $table='materials';
    protected $fillable = [
        'division_id',
        'name',
        'quantity',
        'position',
        'status',
        'price',
    ];
    public function orderItem()
    {
        return $this->morphMany('App\Model\OrderItem', 'item');
    }
    public function getStatusAttribute($value)
    {
        return getStatusText($value,'switch');
    }
    public function logs()
    {
        return $this->hasMany('App\Model\MaterialLog' , 'material_id');
    }
    public function division()
    {
        return $this->belongsTo('App\Model\Divisions','division_id');
    }
}
