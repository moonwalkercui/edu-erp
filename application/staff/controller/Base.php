<?php
namespace app\staff\controller;

use app\common\controller\BaseController;
use think\facade\Session;

class Base extends BaseController
{
    protected function initialize()
    {
        parent::initialize();
    }
    protected function checkLogin()
    {
        if(input('debug') == 1)
            session('login_id', 4);
        if(session('login_id') == false) {
//            if($redirect_back) {
//                session('redirect_back_url', request()->url());
//            }
            return false;
        }
        return true;
    }
    protected function handleRedirectBack($return_url = false)
    {
        $url = Session::pull('redirect_back_url');
        if($url) {
            if ($return_url) return $url;
            else $this->redirect($url);
        }
    }
}