<?php
namespace app\home\controller;

use app\common\model\AudioCategory;
use app\common\model\Audio as AudioModel;

class Audio extends Base
{
    function category()
    {
        $list = AudioCategory::getChildTree();
        return $this->dataJson($list);
    }

    function listData()
    {
        $cid = input('cid/d');
        $list = AudioModel::getAllByCid($cid);
        return $this->dataJson($list);
    }

    function subtitle()
    {
        $input = input('options');
        $data = json_decode( html_entity_decode($input), true) ;
        $data = AudioModel::getInfoAndSubtitle(intval($data['id']));
        return $this->dataJson($data);
    }

//    function dodo()
//    {
//        $i = 0;
//        \app\common\model\Audio::select()->each(function($item) use ($i){
//            $item->audio_uri = str_replace('new.xinyangedu.com','www.xinyangedu.com',$item->audio_uri);
//            $item->subtitle_uri = str_replace('new.xinyangedu.com','www.xinyangedu.com',$item->subtitle_uri);
//            $item->save();
//            echo $i++;
//        });
//    }
}