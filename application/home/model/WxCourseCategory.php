<?php
namespace app\home\model;

use think\Model;

class WxCourseCategory extends Model
{
    protected $table = 'el_zy_currency_category';
    protected $connection = 'wx_db';
    static function getList()
    {
        return self::all();
    }
}