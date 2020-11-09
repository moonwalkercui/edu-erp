<?php
namespace app\staff\controller;

use app\common\model\Staff;
use app\common\model\WxAccess;

class Login extends Base
{
    function loginForm()
    {
        if(request()->isPost()) {
            $data = input('post.');
//            dump($data);
            $staff = Staff::where(['name' => $data['username']])->find();
            $url = null;
            if (!$staff) {
                return $this->errorJson('登录信息有误');
            } elseif ($staff->getData('status') != 1) {
                return $this->errorJson('该账号已被禁用, 请联系管理员');
            } elseif ($staff['password'] != md500($data['password'])) {
                return $this->errorJson('密码错误');
            } else {
                $wx_access_id = session('wx_access_id');
                if($wx_access_id) {
                    WxAccess::where('id', $wx_access_id)->update(['staff_id' =>$staff['id']]);
                }
                $staff->login_time = now();
                $staff->ip = request()->ip();
                $staff->login_num++;
                $staff->wx_access_id = $wx_access_id;
                $staff->save();
                session('login_id', $staff['id']);
                session("login_name", $staff['name']);
            }
            return $this->successJson("登录成功");
        } else{
            return $this->fetch();
        }
    }
}