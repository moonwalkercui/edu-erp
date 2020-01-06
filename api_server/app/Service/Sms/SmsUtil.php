<?php
/**
 * Created by PhpStorm.
 * User: Administrator
 * Date: 2019/6/6 0006
 * Time: 16:59
 */

namespace App\Service\Sms;


use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Log;

class SmsUtil
{
    public static function sendCode($mob)
    {
        $code = rand(1000,9999);

        if(self::frequencyCtr($mob)) {
            $res = AliSms::send($mob, $code);
//            $res = [
//                'code' => 1,
//                'msg' => '发送成功'.$code,
//            ];
            if($res['code'] == 1) {
                self::cacheCode($mob, $code);
                Cache::put('sms_freq_'. $mob, time(), 2);
            }

            return $res;
        } else {
            return [
                'code' => 0,
                'msg' => '发送频率过高请稍后重试',
            ];
        }
    }
    public static function cacheCode($mob, $code)
    {
//        Log::notice('SMS cache code:'.$code . ' -  ' . $mob);
        Cache::put('sms_'. $mob, $code, 2);
    }
    public static function validCode($mob, $code)
    {
        $cache = Cache::get('sms_'. $mob);
//        Log::notice('SMS cache2:'.$cache . ' 输入code: ' . $code . ' - '. $mob);
        return $cache != null && $cache == $code;
    }
    // 频率控制 60 秒发送一次
    private static function frequencyCtr($mob)
    {
        $last_time = Cache::get('sms_freq_'. $mob);
        return $last_time == null || time() > $last_time + 60 ;
    }
}