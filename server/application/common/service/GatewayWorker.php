<?php
namespace app\common\service;
use GatewayClient\Gateway;

class GatewayWorker
{
    static $registerAddress = "127.0.0.1:1238";

    static function bindUser($client_id, $uid)
    {
        Gateway::$registerAddress = self::$registerAddress;
        Gateway::bindUid($client_id, $uid);
    }
    static function sendMsg($uid, $message)
    {
        Gateway::$registerAddress = self::$registerAddress;
        Gateway::sendToUid($uid, $message);
    }
}