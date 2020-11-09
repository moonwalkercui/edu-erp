<?php

namespace app\common\model;

class Config extends BaseModel
{
    static $instance;
    protected $table = 'config';
    public static function getValue($key, $default='')
    {
        if(isset(self::$instance)) {

        } else {
            $data = self::column('value','key');
            self::$instance = $data;
        }
        return isset(self::$instance[$key]) ? self::$instance[$key] : $default;
//        return self::where(compact('key'))->value('value');
    }
}