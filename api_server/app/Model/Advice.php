<?php namespace App\Model;

use Illuminate\Database\Eloquent\Model;

class Advice extends BaseModel
{
    protected $table='advices';
    protected $fillable = ['user', 'content', 'created_at'];
    public $timestamps = false;
}
