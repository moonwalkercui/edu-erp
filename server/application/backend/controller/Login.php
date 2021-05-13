<?php

namespace app\backend\controller;

/**
 * 登录类
 * @author Ray 541720500@qq.com
 */
class Login extends Base
{
    public function loginform()
    {

//        echo md500('wangzhen2021');
        return $this->fetch('index');
    }

    public function paymentForm()
    {
        return $this->fetch();
    }

    public function login()
    {
        if (request()->isPost() == false)
            $this->error('页面不存在');
        // 验证码验证
//        if (!captcha_check(input('cpcode')))
//            $this->error('验证码错误');
        // 清除管理员id的session值
        session('login_id', null);
        $un = trim(input('post.account'));
        $pw = input('post.password');
        $adm = \app\backend\model\Auth::where(['name' => $un])->find();
        if (!$adm) {
            $this->error('登录信息有误');
        } elseif ($adm->getData('status') != 1) {
            $this->error('该账号已被禁用, 请联系管理员');
        } elseif ($adm['password'] != md500($pw)) {
            $remaining = \app\backend\model\Auth::handleMistakePassword($adm);
            $this->error($remaining > 0 ? "密码错误, 还有{$remaining}次机会" : '该账号被禁用');
        } else {
            $adm->mistake_times = 0;
            $adm->login_time = now();
            $adm->ip = request()->ip();
            $adm->login_num++;
            $adm->save();
            cache('current_login_ip_' . $adm->id, $adm->ip, 3600); // 单点登录
            session('login_id', $adm['id']);
            session("login_name", $adm['name']);
            session("manage_staff_leave", $adm['manage_staff_leave']);
            session("manage_staff_payout", $adm['manage_staff_payout']);
            session("permission_ids", \app\backend\model\Auth::getAuthNodeIds($adm['id']));
//            dump(session('permission_ids'));die;
            $this->makeLog("[登录成功]");
            $this->redirect('index/index');
        }
    }

    public function logout()
    {
        session('login_id', null);
        session(null);
        session(config('status.auth.user_auth_nodes'), null);
        $this->redirect('/s');
    }
}
