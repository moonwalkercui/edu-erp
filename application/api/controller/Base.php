<?php

namespace app\api\controller;

use app\common\controller\BaseController;
use app\common\model\User;
use app\common\service\JwtService;
use think\Exception;
use think\facade\Request;

// 基础类 不验证登录 ，验证登录的类：继承Auth ，验证登录方法： $this->>isLogin();
class Base extends BaseController
{
    // 这个参数要想使用，必须先检查登录状态，即运行$this->chaekLogin();后使用$this->member;
    public $user;

    // 检查token的登录状态 并 定义member属性
    function checkLogin($throwExp = true)
    {
        $token = input('token');
        if (!$token && $throwExp) exception('用户未登录', 2);
        if($token) {
            $this->user = JwtService::getUser($token);
        }
    }

    // 检查登录token
    function checkLoginByCookie()
    {
        redirect( request()->domain() . '/wxauth' );
        $token =  cookie('accessToken');
        if($token) {
            $user_id = ABC2decimal($token);
            $this->user = User::find($user_id);
        } else {
            exception('用户未登录', 2);
        }
    }

    private function crossDomain()
    {
//        header("Access-Control-Allow-Origin:*");
//        header("Access-Control-Allow-Methods:GET, POST, OPTIONS, DELETE");
//        header("Access-Control-Allow-Headers:DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type, Accept-Language, Origin, Accept-Encoding");
//        header("Access-Control-Allow-Origin:*");
    }
}