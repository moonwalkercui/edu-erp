<?php
namespace app\common\service;

use app\common\exception\ApiExceptionHandler;
use app\common\model\User;
use think\facade\Session;

class UserAuth
{
    static $user;

    // 通过session查询用户
    static function getUser()
    {
        $uid = Session::get('guid');
        if($uid == null) {
            return null;
        }
        if(isset(self::$user) && self::$user) {
            $user = self::$user;
        } else {
            $user = self::$user = User::get($uid);
        }
        return $user;
    }
    // 通过session验证登录
    static function isLogin() : bool
    {
        return Session::get('guid') != null;
    }

    // api
    static function getUserByToken()
    {
        $token = input('token');
        if(!$token) exception('用户未登录', ApiExceptionHandler::NEED_LOGIN);

        if(isset(self::$user) && self::$user)
            return self::$user;

        $payload = JwtService::deToken($token);
        if(!isset($payload['id'])) exception('TOKEN内容有误');

        self::$user = User::find($payload['id']);
        return self::$user;
    }
    static function isLoginByToken()
    {
        return input('?token');
    }
}