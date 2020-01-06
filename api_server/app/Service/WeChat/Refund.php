<?php

namespace App\Service\WeChat;

use App\Exceptions\ApiException;
use App\Model\Order;
use App\Model\OrderRefund;
use App\Service\Api\MemberService;
use EasyWeChat\Factory;
use Illuminate\Support\Facades\Log;

class Refund
{
    public static function refund($refund_id)
    {
        $refund = OrderRefund::where('id', $refund_id)->first();
        $app = Factory::payment(Config::refundConfig());
        $order = Order::where('id', $refund->order_id)->first();

        // 参数分别为：商户订单号、商户退款单号、订单金额、退款金额、其他参数
        $result = $app->refund->byOutTradeNumber($order->pay_sn, $refund->refund_sn, $refund->total_fee * 100, $refund->refund_fee * 100, [
            // 可在此处传入其他参数，详细参数见微信支付文档
            'refund_desc' => '学员退款',
        ]);
        Log::notice('退款执行结果数据' . var_export($result, true));
        if ($result['return_code'] == 'SUCCESS' && $result['result_code'] == 'SUCCESS') {
            return true;
        } else {
            return $result;
        }
//        array(9) {
//        ["return_code"]=>   string(7) "SUCCESS"
//        ["return_msg"]=>   string(2) "OK"
//        ["appid"]=>   string(18) "wxe44635c633795dd2"
//        ["mch_id"]=>   string(10) "1524756001"
//        ["nonce_str"]=>   string(16) "d1xrGfVEWdukPxAu"
//        ["sign"]=>   string(32) "FF30DEF5E68D52496AE218D2DD225984"
//        ["result_code"]=>   string(4) "FAIL"
//        ["err_code"]=>   string(9) "NOTENOUGH"
//        ["err_code_des"]=>   string(51) "基本账户余额不足，请充值后重新发起"
    }
}
