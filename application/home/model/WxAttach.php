<?php
namespace app\home\model;

use think\Model;

class WxAttach extends Model
{
    protected $table = 'el_attach';
    protected $connection = 'wx_db';
}