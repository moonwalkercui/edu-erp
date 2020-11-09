<?php

namespace app\common\controller;

use app\common\exception\ApiExceptionHandler;
use app\common\service\Sms;
use app\frontend\model\WechatLoginModel;
use app\common\service\UserAuth;
use think\Controller;
use think\facade\Session;

class BaseController extends Controller
{
    protected $isMob = false;

    protected function initialize()
    {
        // 防止浏览器js和css缓存的
        $this->assign('browser_cc_flag', config('app_debug') ? '?d' . rand(10000, 99999) : '?v1');
        $this->isMob = is_mob();
//        $this->assign('isMob', $this->isMob ? 1 : 0);
//        if (in_array(request()->module(), ['ucenter', 'frontend'])) {
////            $this->view->config('view_path', $this->app->getModulePath() . (is_mob() ? 'h5view' : 'view') . DIRECTORY_SEPARATOR);
//            // 微信端登录要进行openid的自动记录
//            if($this->isMob) UserAuth::WxLogin();
//        }
    }
    // 获取登录后的用户，如果没有登录的，或手机端没有绑定手机的，那么要进行提示登录。
    protected function getUser()
    {
        $user = UserAuth::getUser();
        if ($user == null) {
            if (request()->isAjax()) {
                exception('请先登录');
            } else {
                $this->rememberUrl();
                $this->redirect('/login.html');
            }
        };
        return $user;
    }

    protected function handleValidate($data, $rule, $msg = null)
    {
        $result = $this->validate($data, $rule, $msg ? $msg : null);

        if (true !== $result) {
            if (ApiExceptionHandler::isReturnJson()) {
                exception($result);
            } else {
                $this->error($result);
            }
        }
    }

    protected function successJson($msg, $data = [], $code = 1)
    {
        return $this->returnJson('success', $msg, $code, $data);
    }

    protected function errorJson($msg, $data = [], $code = 0)
    {
        return $this->returnJson('error', $msg, $code, $data);
    }

    protected function dataJson($data, $msg = "", $code = 1)
    {
        return $this->returnJson('success', $msg, $code, $data);
    }

    private function returnJson($res, $msg, $code, $data)
    {
        return json([
            'result' => $res,
            'code' => $code,
            'msg' => $msg,
            'data' => $data
        ]);
    }

    protected function smsCode()
    {
//        if (!captcha_check(input('cpcode')))
//            return $this->errorJson('验证码错误', 0);
        $mob = input('mob');
        if(!$mob) return $this->errorJson('缺少手机号');
        $res = Sms::sendCode(input('mob'));
        return $res['code'] == 1 ?
            $this->successJson($res['msg'], $res['sec']) :
            $this->errorJson($res['msg'], $res['sec']);
    }

    protected function rememberUrl()
    {
        if (UserAuth::isLogin() == false)
            Session::set('_redirect_url', request()->url());
    }

    protected function redirectBack($url)
    {
        $ss_url = Session::pull('_redirect_url');
        $this->redirect($ss_url ?: $url);
    }

//    protected function fetchHtml()
//    {
//        controller('common/JKBuildHtml')->buildFromFetch( $html = $this->fetch(), input('get.') );
//        return $html;
//    }

    // 判断移动端和pc端模板
    protected function _fetch($template = '', $vars = [], $config = [])
    {
//        $this->view->config('view_path', $this->app->getModulePath() . (is_mob() ? 'h5view' : 'view' ) . DIRECTORY_SEPARATOR);
        return $this->fetch($template, $vars = [], $config = []);
    }

    function returnHtml($content, $title = '标题')
    {
        $str = <<<EOT
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>{$title}</title>
    <style>html,body,img{width: 100%;padding:0;margin:0;}</style>
</head>
<body>
{$content}
</body>
</html>
EOT;
        echo $str;
    }
}