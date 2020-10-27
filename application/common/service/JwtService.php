<?php
namespace app\common\service;
use app\api\model\UserModel;
use Firebase\JWT\BeforeValidException;
use Firebase\JWT\ExpiredException;
use Firebase\JWT\JWT;
use Firebase\JWT\SignatureInvalidException;

class JwtService
{
    const Key = "49b22f904112e6a837e38222e8bdd32a";
    const Iss = "541720500@qq.com";
    const Exp = 24 * 30; // 过期小时数

    const ERROR_BUILD = '生成TOKEN失败';
    const ERROR_WRONG_CONTENT = 'TOKEN内容有误';

    static $user;

    static function getUser($token)
    {
        if(!empty(static::$user)) return static::$user;

        $payload = static::deToken($token);
        if(!isset($payload['id']))
            exception(static::ERROR_WRONG_CONTENT);
        static::$user = UserModel::find($payload['id']);
        return static::$user;
    }
    // 尝试获取用户 获取不到返回null
    static function tryGetUser()
    {
        if(isset(static::$user )) return static::$user;

        $token = input('token');
        if(!$token) return null;
        $payload = static::deToken($token);
        if(!isset($payload['id'])) return null;
        static::$user = UserModel::find($payload['id']);
        return static::$user;
    }
    static function deToken($token)
    {
        try{
            $result = JWT::decode($token, static::Key, ['HS256']);
        } catch (\Exception $e) {
            if($e instanceof \InvalidArgumentException) {
                $error_msg = '缺少TOKEN';
            } elseif ($e instanceof ExpiredException) {
                $error_msg = '登录状态过期';
            } elseif ($e instanceof SignatureInvalidException) {
                $error_msg = 'TOKEN验证错误';
            } elseif ($e instanceof BeforeValidException) {
                $error_msg = 'NBF错误';
            } elseif ($e instanceof \UnexpectedValueException) {
                $error_msg = 'TOKEN解码错误';
            } else {
                $error_msg = $e->getMessage();
            }
            exception($error_msg, 2);
        }
        return json_decode(passport_decrypt($result->body), true);
    }
    static function enToken($payload)
    {
        return static::getToken([
            'body' => passport_encrypt(json_encode($payload))
        ]);
    }
    public static function getToken($payload = [])
    {
        if (empty($payload)) {
            exception(static::ERROR_BUILD);
        }
        $now = time();
        $payload = array_merge($payload, [
            'typ' => 'JWT',
            'alg' => 'HS256',
            'iss' => static::Iss,
            'iat' => $now,
            'exp' => $now + static::Exp * 3600,
        ]);
//  header
//  jwt的头部承载两部分信息：
//  声明类型，这里是jwt
//  声明加密的算法 通常直接使用 HMAC SHA256

//  playload 载荷就是存放有效信息的地方
//  iss: jwt签发者
//  sub: jwt所面向的用户
//  aud: 接收jwt的一方
//  exp: jwt的过期时间，这个过期时间必须要大于签发时间
//  nbf: 定义在什么时间之前，该jwt都是不可用的.
//  iat: jwt的签发时间
//  jti: jwt的唯一身份标识，主要用来作为一次性token, 从而回避重放攻击。
        return Jwt::encode($payload, static::Key);
    }
}
