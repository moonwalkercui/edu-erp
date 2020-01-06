<?php

namespace App\Api\V1\Controllers\member;

use App\Logic\ApiMember\MemberLogic;
use App\Model\Member;
use App\Model\MemberProfile;
use App\Model\Setting;
use App\Service\Api\MemberService;
use App\Service\Api\UploaderService;
use App\Service\Sms\SmsUtil;
use App\Service\WeChat\MpForMember;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class Normal extends Base
{
    public function uploader(Request $request)
    {
//        var_dump($request->all());die;
        $action = $request->input('action');
        switch ($action) {
            case 'uploadImg':
                $result = UploaderService::uploadImage($request, 'm_'.MemberService::getMemberId());
                break;
            default:
                $result = ["status" => "error",'msg'=> '无上传类型'];
        }
        return json_encode($result);
    }
    public function homeData()
    {
        $org_info = Setting::getOrgInfo();
        $products = \App\Model\Product::getRecommend();
        return $this->fetch(compact('org_info', 'products'));
    }
    // 发送验证码
    public function sendSmsCode(Request $request)
    {
        $this->validateParam($request->all() , [
            'mobile' => 'required',
            'code' => 'required',
            'captcha' => 'required',
        ],[
            'mobile.required' => '请输入手机号',
            'code.required' => '缺少CODE',
            'captcha.required' => '缺少验证码',
        ]);

        $captcha = new \App\Service\Api\Captcha();
        if($captcha->validateCode($request->captcha, $request->code) == false) {
            return $this->error('验证码错误');
        }
        $res = SmsUtil::sendCode($request->mobile);
        return $res['code'] == 1 ? $this->success($res['msg']) : $this->error($res['msg']) ;
    }
    // 小程序首次登陆会新建账号，通过此方法初次绑定手机号 并 检查是否已经注册过 存在则合并
    public function bindMobile(Request $request)
    {
        $this->validateParam($request->all() , [
            'mobile' => 'required',
            'code' => 'required',
        ]);
        $mobile = cleanXss($request->mobile);
        if(SmsUtil::validCode($mobile, $request->code) == false)
            return $this->error('短信验证码错误') ;

        $res = MemberLogic::handleBindMobile($mobile);
        if($res) return $this->fetch($res);
        return $this->error('绑定失败');

//        $exist_profile = MemberProfile::where('mobile', $mobile)->first();
//        $token = ""; // 是否有新token
//        if($exist_profile) {
//            // 已经注册过会员了，需要采用原会员数据，并返回新token和profile
//            $openid = MemberService::getOpenId();
//            Member::where('wx_open_id', $openid)->update(['wx_open_id' => $openid . '_2']);
//            Member::where('id', $exist_profile->member_id)->update(['wx_open_id' => $openid]);
//            $profile = MemberProfile::makeProfileRes($exist_profile);
//            $token = MemberProfile::makeToken($exist_profile);
//            return $this->fetch(compact('token','profile'));
//
//        } else {
//            // 该手机号没有注册过，直接更新即可。
//            if(MemberLogic::bindMobile(MemberService::getMemberProfileId(), $mobile))
//                return $this->fetch(compact('token'));
//        }
//        return $this->error('绑定失败');
    }
    public function bindMobileByWx(Request $request)
    {
        $this->validateParam($request->all() , [
            'encryptedData' => 'required',
            'iv' => 'required',
            'sid' => 'required',
        ],[
            'encryptedData.required'=>'缺少数据1',
            'iv.required'=>'缺少数据2',
            'sid.required'=>'缺少数据',
        ]);
//
        $encryptedData = $request->input('encryptedData');
        $iv = $request->input('iv');
        $sid = $request->input('sid');
        $program = new MpForMember();
        $res = $program->decryptPhone($sid, $iv, $encryptedData);
        if($res['mobile']) {
            $res = MemberLogic::handleBindMobile($res['mobile']);
            if($res) return $this->fetch($res);
        }
        return $this->error('绑定失败请重试');
    }
}