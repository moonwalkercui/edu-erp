<?php

namespace App\Service\WeChat;

use App\Exceptions\ApiException;
use App\Model\Order;
use App\Model\OrderRefund;
use App\Service\Api\MemberService;
use EasyWeChat\Factory;
use Illuminate\Support\Facades\Log;

class Payment
{
    public static function prepay($data)
    {
        $app = Factory::payment(Config::paymentConfig());
        $jssdk = $app->jssdk;
        $openid = MemberService::getOpenId();

        $result = $app->order->unify([
            'trade_type' => 'JSAPI',              // 支付方式，小程序支付使用JSAPI
            'body' => $data['body'],            // 订单说明
            'out_trade_no' => $data['pay_sn'],  // 自定义订单号
            'total_fee' => $data['money'] * 100,               // 单位：分
            'openid' => $openid                   // 当前用户的openId
        ]);

        if ($result['return_code'] == 'SUCCESS' && $result['result_code'] == 'SUCCESS') {
            $prepayId = $result['prepay_id'];
            $config = $jssdk->sdkConfig($prepayId);

            // 发送小程序模板消息用的字段
            Order::where('pay_sn', $data['pay_sn'])->update([
                'msg_body' => json_encode([
                    'form_id' => ltrim($config['package'],"prepay_id="),
                    'touser' => $openid,
                ])
            ]);

            return $config;

//            $config : {
//                ["appId"]=>  string(18) "wxe44635c633795dd2"
//                ["nonceStr"]=>  string(13) "5c4bc56220249"
//                ["package"]=>  string(46) "prepay_id=wx26102642087212c3172cef334022329480"
//                ["signType"]=>  string(3) "MD5"
//                ["paySign"]=>  string(32) "1F8BD25026C0B5564AFF9A3BA6CBFF41"
//                ["timestamp"]=>  string(10) "1548469602"
//}

        }

        if ($result['return_code'] == 'FAIL' && array_key_exists('return_msg', $result)) {
            throw new ApiException($result['return_msg']);
        }
        throw new ApiException($result['err_code_des']);

//        "result": {
//            "return_code": "SUCCESS",
//            "return_msg": "OK",
//            "appid": "wxe44635c633795dd2",
//            "mch_id": "1524756001",
//            "nonce_str": "smRQFDA4o1A6r90Y",
//            "sign": "9E607D4860A6259163486AFB5F0885E9",
//            "result_code": "SUCCESS",
//            "prepay_id": "wx26100602348133136ef4f0021397216332",
//            "trade_type": "JSAPI"
//        }
    }

}
