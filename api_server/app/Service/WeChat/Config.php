<?php
namespace App\Service\WeChat;
use App\Model\Setting;

/**
 * Created by PhpStorm.
 * User: 541720500@qq.com
 */
class Config
{
    public static $NotifyUrl = API_DOMAIN_PREFIX . '/paymentcallback';
    public static $RefundNotifyUrl = API_DOMAIN_PREFIX . '/refundcallback';
    public static $CertPath = '/www/wwwroot/apibase/storage/app/cert/'. API_PREFIX . '/apiclient_cert.pem'; // XXX: 绝对路径！！！！;
    public static $CertKeyPath = '/www/wwwroot/apibase/storage/app/cert/'. API_PREFIX . '/apiclient_key.pem'; // XXX: 绝对路径！！！！;

    public static function appConfig()
    {
        $setting = Setting::getSetting();
        return [
            'app_id' => $setting->wx_appid,
            'secret' => $setting->wx_secret,
            'response_type' => 'array',
            'log' => [
                'level' => 'debug',
                'file' => __DIR__.'/wechat.log',
            ]
        ];
    }
    public static function paymentConfig()
    {
        $setting = Setting::getSetting();
        return [
            'app_id'             => $setting->wx_appid,
            'mch_id'             => $setting->wx_mchid,
            'key'                => $setting->wx_key,
            'notify_url'         => self::$NotifyUrl,     // 你也可以在下单时单独设置来想覆盖它
//            'cert_path'          => 'path/to/your/cert.pem', // XXX: 绝对路径！！！！
//            'key_path'           => 'path/to/your/key',      // XXX: 绝对路径！！！！
        ];
    }
    public static function refundConfig()
    {
        $setting = Setting::getSetting();
        return [
            'app_id'             => $setting->wx_appid,
            'mch_id'             => $setting->wx_mchid,
            'key'                => $setting->wx_key,
            'cert_path'          => self::$CertPath,
            'key_path'           => self::$CertKeyPath,
            'notify_url'         => self::$RefundNotifyUrl,     // 你也可以在下单时单独设置来想覆盖它
        ];
    }
}