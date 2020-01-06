<?php
namespace App\Service\Api;

use App\Exceptions\ApiException;
use App\Model\ApiUser\UserModel;
use App\Model\Order;
use App\Model\Organization;
use App\Model\Setting;
use App\Model\UserProfile;
use App\Service\WeChat\MiniProgram;

class WxService
{
    public static function sendOrderMsg($pay_sn)
    {
        $order = Order::where('pay_sn', $pay_sn)->first();

        $msg_body = json_decode($order->msg_body, true);
//        {"form_id":"wx19135603793012b407b74f952344142670","touser":"oMC_j5KIWi-dq92_nvwM3sh9Mn1I"}
        $setting = Setting::where('code', API_PREFIX)->first();
        $miniapp = new MiniProgram();
        $miniapp->sendTemplateMsg($msg_body['touser'], $msg_body['form_id'], 'order_paid', [
            'keyword1' => $order->sn,
            'keyword2' => implode(';', $order->items()->pluck('item_name')->toArray()),
            'keyword3' => $order->pay_money . '元',
            'keyword4' => $order->paid_at,
            'keyword5' => $setting->org_name . '欢迎您!' . '请登录小程序查看订单的更多详情,',
            'keyword6' => $setting->org_phone,
        ]);
// 单号 {{keyword1.DATA}}
//课程名称 {{keyword2.DATA}}
//金额 {{keyword3.DATA}}
//支付时间 {{keyword4.DATA}}
//备注 {{keyword5.DATA}}
//客服电话 {{keyword6.DATA}}
    }
}
