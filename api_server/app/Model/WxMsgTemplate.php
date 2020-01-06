<?php

namespace App\Model;

class WxMsgTemplate extends BaseModel
{
    protected $table = 'wx_msg_template';
    public $timestamps = false;
    public function getStatusAttribute($value)
    {
        return getStatusText($value, 'switch');
    }
}
