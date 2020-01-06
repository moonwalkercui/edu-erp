<?php
namespace App\Http\Controllers;
use App\Logic\RedPacket;
use Illuminate\Support\Facades\Log;

class Cron extends Controller
{
    public function handleRedPacketExpire()
    {
//        for($i = 0; $i < 100; $i ++ ){
//            $arr[] = makeRedPacketAmount(1,100);
//        }
//        sort($arr);
//        var_dump($arr);
//        die;
        Log::notice("执行一次CRON " . API_PREFIX ." ". now());
        RedPacket::handleMultiExpire();
    }
}