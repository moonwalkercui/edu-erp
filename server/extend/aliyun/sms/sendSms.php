<?php

namespace aliyun\sms;
require_once dirname(__DIR__) . "/sms/SignatureHelper.php";

use aliyun\sms\SignatureHelper;
use think\Config;
use think\Request;
use think\Db;

/**
 * 发送阿里云短信
 */
class sendSms
{
    private $post_url = 'dysmsapi.aliyuncs.com';
    private $config = [];

    public function __set($p, $v)
    {
        if (property_exists($this, $p)) {
            $this->$p = $v;
        }
    }

    public function __get($p)
    {
        if (property_exists($this, $p)) {
            return $this->$p;
        }
    }
    public function __construct()
    {
        $smss = Db::name('config')->where(['group' => 'sms'])->select();
        $config = [];
        foreach ($smss as $k => $v) {
            $config[$v['key']] = $v['value'];
        }
        $this->config = $config;
    }

    public function std_class_object_to_array($stdclassobject)
    {
        $_array = is_object($stdclassobject) ? get_object_vars($stdclassobject) : $stdclassobject;
        foreach ($_array as $key => $value) {
            $value = (is_array($value) || is_object($value)) ? std_class_object_to_array($value) : $value;
            $array[$key] = $value;
        }
        return $array;
    }
    /**
     * 发送短信
     * @template_code 短信模板在config_basic表里存放
     */
    function send($mobile, $data_map, $template_code, $user_id = null)
    {
        $LogSms = Db::name('sms_log');
        //限制一：12小时内不得超过500
        if ($user_id) {
            $countphone12ha = $LogSms->where(compact('user_id'))->where("add_time", '>', time() - 3600 * 12)->where('status', 1)->count();
            if ($countphone12ha > 500) {
                $res['code'] = 0;
                $res['msg'] = "您的操作过于频繁(001),请稍后重试!";
                return $res;
            }
        }
        //限制二：单个手机号12小时不得超过30;
        $countphone12hs = $LogSms->where(compact('mobile'))->where('status', 1)->count();
        if ($countphone12hs > 30) {
            $res['code'] = 0;
            $res['msg'] = "您的操作过于频繁(002),请稍后重试!";
            return $res;
        }
        //限制三：单个手机号300秒不得超过条
        $countphone = $LogSms->where(compact('mobile'))->where("add_time", '>', time() - 300)->where('status', 1)->count();
        if ($countphone > 10) {
            $res['code'] = 0;
            $res['msg'] = "您的操作过于频繁(003),请稍后重试!";
            return $res;
        }
        //限制四：验证码类短信，单个手机号或单个ip300秒不得超过5条
//         if ($content_type == 1) {
//             $countphone = $LogSms->where("(`phone` = '{$mobile}' or create_ip like '%" . $addData['create_ip'] . "%') and create_time > (unix_timestamp()-300)")->count('id');
//         }
//         if ($countphone >= 5) {
//             $res['code'] = 404;
//             $res['msg'] = "您的操作过于频繁(003),请稍后重试!";
//             return $res;
//         }

        $params = [];
        // *** 需用户填写部分 ***
        // fixme 必填：是否启用https
        $security = false;

        // fixme 必填: 请参阅 https://ak-console.aliyun.com/ 取得您的AK信息
        $accessKeyId = $this->config['sms_key'];
        $accessKeySecret = $this->config['sms_secret'];

        // fixme 必填: 短信接收号码
        $params["PhoneNumbers"] = $mobile;

        // fixme 必填: 短信签名，应严格按"签名名称"填写，请参考: https://dysms.console.aliyun.com/dysms.htm#/develop/sign
        $params["SignName"] = $this->config['sms_note'];

        // fixme 必填: 短信模板Code，应严格按"模板CODE"填写, 请参考: https://dysms.console.aliyun.com/dysms.htm#/develop/template
        $params["TemplateCode"] = $this->config[$template_code];

        // fixme 可选: 设置模板参数, 假如存在模板中变量需要替换则为必填项
        $params['TemplateParam'] = $data_map;

        // fixme 可选: 设置发送短信流水号
        $params['OutId'] = "";

        // fixme 可选: 上行短信扩展码, 扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段
        $params['SmsUpExtendCode'] = "";

        // *** 需用户填写部分结束, 以下代码若无必要无需更改 ***
        if (!empty($params["TemplateParam"]) && is_array($params["TemplateParam"])) {
            $params["TemplateParam"] = json_encode($params["TemplateParam"], JSON_UNESCAPED_UNICODE);
        }

        // 初始化SignatureHelper实例用于设置参数，签名以及发送请求
        $helper = new SignatureHelper();

        // 此处可能会抛出异常，注意catch
        $content = $helper->request(
            $accessKeyId,
            $accessKeySecret,
            $this->post_url,
            array_merge($params, array(
                "RegionId" => "cn-hangzhou",
                "Action" => "SendSms",
                "Version" => "2017-05-25",
            )),
            $security
        );
//        return $content;
        $content = $this->std_class_object_to_array($content);
//        print_r(lang($content['Code']));exit();
        $log_data = [
            'user_id' => $user_id,
            'mobile' => $mobile,
            'add_time' => time(),
            'status' => 0,
            'ip' => request()->ip(),
            'content' => json_encode($data_map),
        ];
        if ($content && ($content['Code'] == 'OK')) {
            $res['code'] = 1;
            $res['msg'] = "发送成功";
            $log_data['status'] = 1;
            $LogSms->insert($log_data);
            return $res;
        } else {
            $res['code'] = 0;
            $res['msg'] = lang($content['Code']);
            $LogSms->insert($log_data);
            return $res;
        }
    }
}