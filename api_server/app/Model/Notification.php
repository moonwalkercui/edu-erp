<?php

namespace App\Model;

use Carbon\Carbon;
use Illuminate\Database\Eloquent\SoftDeletes;

class Notification extends BaseModel
{
    protected $fillable = [
        'title',
        'content',
        'image',
        'is_top',
        'sort'
    ];
    public function getIsTopAttribute($value)
    {
        return getStatusText($value,'is_yes');
    }
    public function getIsPopupAttribute($value)
    {
        return getStatusText($value,'is_yes');
    }
    public function getImageAttribute($value)
    {
        return addImagePrefix($value);
    }
}
