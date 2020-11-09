<?php

namespace app\api\controller;

use app\common\controller\BaseController;
use app\common\model\Staff;
use app\common\model\Student;
use app\common\model\WxAccess;
use app\common\model\WxMenu;
use app\common\service\JwtService;
use app\common\service\WxService;
use think\Exception;
use think\facade\Log;
use think\facade\Session;
use WeChat\Receive;

class Wx extends BaseController
{
    // 微信公众号入口
    function entry()
    {
        try {
            $wx = WxService::receiveEvent(); // 微信服务器配置验证用，只用一次 然后注释掉即可
        } catch (Exception $e) {
            Log::error($e->getMessage());
            return;
        }

        switch ($wx->getMsgType()) {
            case 'event':
                $this->handleEvent($wx, $wx->getEvent());
                break;
            case 'text':
                $this->handleText($wx, $wx->getTextContent());
                break;
            default:
                $wx->text($this->helpText())->reply();
        }
    }

    public function auth()
    {
        $state = input('state');
        $redirect_url = request()->domain() . '/wxaccess'; // 跳转地址
        WxService::oauth($redirect_url, $state);
    }

    public function access()
    {
//        session("wx_access_id", null);
//        session("student_id", null);
        $state = input('state');
        $access_data = WxService::accessData();
        if (empty($access_data['openid'])) {
            exception("获取Openid失败");
        }
        $user_info = WxService::getUserInfo($access_data);
        $access = WxAccess::getAccessItem($access_data['openid'], $access_data['unionid'] ?? null, $user_info);
//        dump($access);die;
        session("wx_access_id", $access['id']);

        $arr = explode('_', $state);
        // 跳转到vuejs前端等
        switch ($arr[0]) {
            case 'calendar':
                $this->storeTeacherSession($access['staff_id']);
                $front_url = request()->domain() . '/staffcenter/#/pages/course/calendar';
                break;
            case 'offduty':
                $this->storeTeacherSession($access['staff_id']);
                $front_url = request()->domain() . '/staffcenter/#/pages/offduty/apply'; // 员工请款
                break;
            case 'payout':
                $this->storeTeacherSession($access['staff_id']);
                $front_url = request()->domain() . '/staffcenter/#/pages/payout/apply'; // 员工请款
                break;
            case 'sign':
                $this->storeTeacherSession($access['staff_id']);
                $front_url = request()->domain() . '/staffcenter/#/pages/index/sign';  // 签到菜单路径： http://erp.hzb-it.com/wxauth.html?state=sign
                break;
            case 'staffcenter':
                $this->storeTeacherSession($access['staff_id']);
                $front_url = request()->domain() . '/staffcenter/#/'; // 会员中心菜单路径 http://erp.hzb-it.com/wxauth.html?state=sign
                break;
            case 'studentcenter':
                $this->storeStudentSession($access['student_id']);
                $front_url = request()->domain() . '/studentcenter/#/'; // 学生中心 http://erp.hzb-it.com/wxauth.html?state=studentcenter
                break;
            default:
                $front_url = request()->domain() ;
        }
        header('location:' . $front_url);
    }
    private function storeTeacherSession($teacher_id)
    {
        if($teacher_id) {
            session("login_id", $teacher_id);
            session("login_name", Staff::where('id', $teacher_id)->value('name'));
        } else {
            session("login_id", null);
            session("login_name", null);
        }
    }
    private function storeStudentSession($student_id)
    {
        if($student_id) {
            session("student_id", $student_id);
            session("student_name", Student::where('id', $student_id)->value('name'));
        }
    }

    public function wxLogin()
    {
//        $mer_code = cookie('mer_code');
//        if($mer_code == null) {
//            return $this->returnError('Loading...');
//        }
//        $openid = Db::name('merchant_wxtoken')->where('code', $mer_code)->value('openid');
//        if($openid == null) {
//            return $this->returnError('加载中...');
//        }
//        $token_arr = MerchantModel::getTokenByOpenid($openid);
//        if($token_arr == null) return $this->returnError('未绑定商家账号,请用账密登录');
//        return $this->returnSuccess('自动登录', $token_arr);
    }

    private function handleText($wx, $content)
    {
//        switch ($content) {
////            case "内部":
////            case \app\common\model\Config::getValue('store_passport'):
////                $wx->text("http://api.zesmz.com/staffcenter/#/")->reply();
////                break;
//            default:
//                $wx->text($this->helpText())->reply();
//        }
        $wx->text($this->helpText())->reply();
    }

    private function handleEvent(Receive $wx, $event)
    {
        $openid = $wx->getOpenid();

        switch ($event) {
            case "CLICK":
                $key = $wx->getEventClickKey();
                $keys = explode("_", $key);
                if(count($keys) == 3) {
                    if($keys[0] == 'menu' && $keys[1] == 'text') {
                        $wx->text(WxMenu::getContent($keys[2]))->reply();
                    }
                }
                break;
            case "subscribe":
                $wx->text($this->helpText())->reply();
                break;
            case "unsubscribe":
                WxAccess::handleUnsubscribe($openid);
                break;
            default:
                $wx->text("欢迎!")->reply();
        }
    }
    // 处理点击事件
    private function handleClick(Receive $wx, $key)
    {
        Log::notice("微信key: ". $key);
        Log::notice("全部参数: ". var_export(input('')));
    }

    private function helpText()
    {
        return "欢迎关注本公众号！";
//        return "欢迎关注本公众号！你可以点击【会员卡中心】按钮查看自己的会员卡信息，也可以通过【用卡记录】查看自己的用卡状态。祝您生活愉快！";
//        return \app\common\model\Config::getValue('subscribe_reply');
    }


    // 获取js-sdk参数
    function jsSdkConfig()
    {
        $url = input('url');
        $sign = WxService::jsSign($url);
        return $this->dataJson($sign);
    }
}