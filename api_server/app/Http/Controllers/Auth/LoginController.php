<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Foundation\Auth\AuthenticatesUsers;

class LoginController extends Controller
{
    use AuthenticatesUsers;
    /*登录后跳转地址*/
    protected $redirectTo = '/home';
    // 帐密错误最多次数
    public $maxAttempts = 5;
    public function __construct()
    {
        $this->middleware('guest')->except('logout');
    }
    /*定义登录字段*/
    public function username()
    {
        return 'email';
    }
}
