<?php

namespace App\Model;
use Illuminate\Support\Facades\Cache;

class Region extends BaseModel
{
    protected $table = 'regions';
    protected $connection = 'common';
    public static function getTree()
    {
//        return Cache::rememberForever('cache_city_tree' ,function(){
            return unlimitedChild(self::orderBy('sort')->select('id','name','pid')->get()->toArray(), 0, 'pid');
//        });
    }
    public static function getList()
    {
        return self::orderBy('sort')->where('pid',1376)->select('id','name')->get()->toArray();
    }
}
