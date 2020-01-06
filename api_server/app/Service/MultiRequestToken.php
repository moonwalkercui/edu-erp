<?php
namespace App\Service;

use App\Exceptions\ApiException;
use Carbon\Carbon;
use Illuminate\Support\Facades\Cache;

class MultiRequestToken
{
    private static $pre_str = 'multi_req_token_';
    public static function make()
    {
        // 根据uniqid的值生成唯一id
        $uniqid = uniqid();
        $code = encrypt($uniqid);
        // 再将token放入缓存里，缓存名是uniqid值 有效期20分钟
        Cache::put( self::$pre_str . $uniqid, $code, Carbon::now()->addMinutes(20) );
        // 发给前端uniqid . token
        return $uniqid. '.' . $code;
    }
    public static function check($token)
    {
        // 获取到token，然后用.拆分，
        $code_arr = explode('.', $token);
        if (count($code_arr) != 2) return false;
        // 从缓存里pull出来的uniqid值，并和token的第二部分做对比
        $token_cache = Cache::pull(self::$pre_str . $code_arr[0]);
        if ($token_cache == null && $token_cache !== decrypt($code_arr[1])) {
            throw new ApiException('请勿重复提交');
        } else {
            return true;
        }
    }
}
