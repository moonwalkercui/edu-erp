<?php
namespace App\Model;

class MessageRead extends BaseModel
{
    public $timestamps = false;
    protected $table = 'message_read';
    protected $fillable = [
        'message_id',
        'read_at'
    ];
}
