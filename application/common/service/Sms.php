<?php
namespace app\common\service;
use aliyun\sms\sendSms;
class Sms
{
    static $err_msg;
    const LIMIT_SEC = 30; // 两次发送间隔时间默认秒
    static function sendCode($mobile)
    {
        $debug = input('debug') == 1;
        $limit = self::getLimitSec();
        if ($limit > 0) {
            return [
                'code' => 0,
                'msg' => '发送间隔过短，请稍后再试',
                'sec' => $limit
            ];
        }
        $code = rand(1000, 9999);
        // 发送短信
        $send_res = $debug ? true : self::sendSmsCode($mobile, $code);
        if ($send_res) {
            self::bindMobileCode($mobile, $code);
            self::setLimitSec();
            return [
                'code' => 1,
                'msg' => '短信发送成功'. ($debug ? $code : ''), // .$code
                'sec' => self::LIMIT_SEC
            ];
        } else {
            return [
                'code' => 0,
                'msg' => '发送失败',
                'sec' => self::LIMIT_SEC
            ];
        }
    }
    // 绑定手机号和验证码
    static function bindMobileCode($mobile, $code)
    {
        cache('mobile_bind_code_' . $mobile, $code, 600); // 十分钟过期
    }
    // 验证验证码是否正确
    static function checkMobileCode($mobile, $code)
    {
        $cc = cache('mobile_bind_code_' . $mobile);
        return $cc && $cc == $code;
    }
    // 设置短信过去时间
    static function setLimitSec()
    {
        cache('send_sms_limit_' . request()->ip(), time() + self::LIMIT_SEC, self::LIMIT_SEC); // 过期时间
    }
    // 获取过期时间
    static function getLimitSec()
    {
        $cac = cache('send_sms_limit_' . request()->ip());
        if ($cac == null) return 0;
        elseif ($cac <= time()) return 0;
        else return $cac - time();
    }
    // 发送短信验证码
    static function sendSmsCode($mobile, $code)
    {
        $driver = new sendSms();
        return $driver->send($mobile, ['code' => $code], 'sms_temp_code');
    }
}