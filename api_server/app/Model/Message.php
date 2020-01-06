<?php
namespace App\Model;

use Illuminate\Database\Eloquent\SoftDeletes;

class Message extends BaseModel
{
    use SoftDeletes;
    protected $dates = ['deleted_at'];
    public $timestamps = false;
    protected $table = 'messages';
    protected $fillable = [
        'to_user',
        'title',
        'content',
        'to_name',
        'from_user',
        'from_name',
        'created_at'
    ];
    public function read()
    {
        return $this->hasOne('App\Model\MessageRead','message_id');
    }
}
