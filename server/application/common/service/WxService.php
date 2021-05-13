<?php

namespace app\common\service;

use app\common\model\User;
use app\common\model\WxAccess;
use think\Db;
use think\facade\Log;

class WxService
{
    private static $config = [

        'token' => 'xxxx',
        'appid' => 'xxxx',
        'appsecret' => 'xxxx',
        'encodingaeskey' => 'xxxx',

        // 配置商户支付参数（可选，在使用支付功能时需要）
        'mch_id' => "",
        'mch_key' => '',
        // 配置商户支付双向证书目录（可选，在使用退款|打款|红包时需要）
        'ssl_key' => '',
        'ssl_cer' => '',
        // 缓存目录配置（可选，需拥有读写权限）
        'cache_path' => '',
    ];
    // 处理公众号接收事件（内部已实现配置服务器的能力）
    //        $data = [
    //            $app->getOpenid(),
    //            $app->getMsgType(),
    //            $app->getEvent(),
    //        ];
    //        $app->text("欢迎")->reply();
    static function receiveEvent()
    {
        include env('extend_path') . "WxAliLib/include.php";
        return new \WeChat\Receive(static::$config);
    }
    static function oauth($redirect_url, $state='')
    {
        include env('extend_path') . "WxAliLib/include.php";
        $app = new \WeChat\Oauth(static::$config);
        $url = $app->getOauthRedirect($redirect_url, $state);
        header('location:' . $url);
    }
    static function menu()
    {
        include env('extend_path') . "WxAliLib/include.php";
        return new \WeChat\Menu(static::$config);
    }

    // 首页调转访问到这里
    //        array(5) {
    //        ["access_token"] => string(89) "35_tUsuuvaYh9pIG9x1IbFafHJ3QCBWUXZ3ouG1eTLURL5Xg2SdLkY6nIK6E4wkWnXXYKl4Vmo-4so58L-n8rLrWQ"
    //        ["expires_in"] => int(7200)
    //        ["refresh_token"] => string(89) "35_MLD-GJHrB7z3jk8tPDDw9nqB8nNtQGfj3eh1-aLVi491OH92mjcYJo3LX0MyId5q6D4rWYmiC0FxkDKQ_fReoA"
    //        ["openid"] => string(28) "olGpVxJen9dW5hDvkQsLok-LGsWU"
    //        ["scope"] => string(15) "snsapi_userinfo"
    static function accessData()
    {
        include env('extend_path') . "WxAliLib/include.php";
        $app = new \WeChat\Oauth(static::$config);
        return $app->getOauthAccessToken();
    }
    static function getUserInfo($access_data)
    {
        include env('extend_path') . "WxAliLib/include.php";
        $app = new \WeChat\Oauth(static::$config);
        return $app->getUserInfo($access_data['access_token'],$access_data['openid']);
//        array(5) {
//        ["access_token"] => string(89) "37_uB-Lp6URkoFhONPcerBDHtQV7r11Az0RpUTMwYsvl2PJVWuM9d21fSqqDxT0FwPgR3UwFUZzsgQSvRQ6n5TjzA"
//        ["expires_in"] => int(7200)
//        ["refresh_token"] => string(89) "37_xGZxEP05H0SOkbisrhuUC5-j1T9YXBxmvbcVP_3UbQkoVoAMg153gEPMSAosTpEEE6tL0yFTfhq-6s1R1RoWYw"
//        ["openid"] => string(28) "oXlScv5_yEIVo2zaYLYJQ5CIB1zk"
//        ["scope"] => string(15) "snsapi_userinfo"
//}
    }

    // 网页参数获取
    static function jsSign($url)
    {
        include env('extend_path') . "WxAliLib/include.php";
        $wechat = new \WeChat\Script(static::$config);
        return $wechat->getJsSign($url);
    }

    // 下载临时素材
    static function downloadTempMedia($media_id)
    {
        include env('extend_path') . "WxAliLib/include.php";
        $wechat = new \WeChat\Media(static::$config);
        return $wechat->get($media_id);
    }

//    // 首次配置微信服务器用 WxService::serverSetup();  已经实现
//    static function serverSetup()
//    {
//        $signature = $_GET["signature"];
//        $timestamp = $_GET["timestamp"];
//        $nonce = $_GET["nonce"];
//
//        $token = static::$config['token'];
//        $tmpArr = array($token, $timestamp, $nonce);
//        sort($tmpArr, SORT_STRING);
//        $tmpStr = implode($tmpArr);
//        $tmpStr = sha1($tmpStr);
//        if ($tmpStr == $signature) {
//            echo $_GET['echostr'];
//        }
//    }
}