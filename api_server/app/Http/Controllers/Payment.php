<?php

namespace App\Http\Controllers;

use App\Model\Goods;
use App\Model\MemberProduct;
use App\Model\Order;
use App\Model\OrderRefund;
use App\Model\Proceeds;
use App\Service\Api\WxService;
use App\Service\WeChat\Config;
use EasyWeChat\Factory;
use EasyWeChat\Kernel\Support\XML;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Log;

class Payment extends Controller
{
    // 支付回调 /paymentcallback?sn=
    public function callback(Request $request)
    {
        $notify = $this->getNotify();
        $options = Config::paymentConfig();
        $options['app_id'] = $notify['appid'];

        $payment = Factory::payment($options);
        $response = $payment->handlePaidNotify(function ($message, $fail) {
            Log::notice('回调变量message' . var_export($message, true));
            $res = $this->handleOrder($message);
            if($res['status'] == 'fail') {
                return $fail($res['msg']);
            } else {
                return true;
            }

            //       $message:     array (
//                'appid' => 'wxe44635c633795dd2',
//                'bank_type' => 'CFT',
//                'cash_fee' => '1',
//                'fee_type' => 'CNY',
//                'is_subscribe' => 'N',
//                'mch_id' => '1524756001',
//                'nonce_str' => '5c4c5549ca97f',
//                'openid' => 'oMC_j5KIWi-dq92_nvwM3sh9Mn1I',
//                'out_trade_no' => '1O6F08O1',
//                'result_code' => 'SUCCESS',
//                'return_code' => 'SUCCESS',
//                'sign' => 'B1C3009E8F5F61AB364C5AA434804ACD',
//                'time_end' => '20190126204045',
//                'total_fee' => '1',
//                'trade_type' => 'JSAPI',
//                'transaction_id' => '4200000269201901261538887729',
//            )

        });
        return $response;
    }

    private function handleOrder($message)
    {
        try {
            $sn = $message['out_trade_no'];
            $pay_log = Proceeds::where('sn', $sn)->first();
            if ($pay_log->getOriginal('status') == 1) {
                Log::error('出现已支付回调:' . $sn);
                return ['status' => 'complete'];
            }
            // 支付成功后的业务逻辑
            if ($message['return_code'] !== 'SUCCESS') {
                return [
                    'status' => 'fail',
                    'msg' => '通信失败，请稍后再请求',
                ];
            }
            if (array_get($message, 'result_code') === 'SUCCESS') {
                Order::handelPay($sn);
                Proceeds::where('sn', $sn)->update(['status' => 1, 'paid_at' => now(),'remark' => '微信订单号'. $message['transaction_id']]);
                Log::notice('订单支付回调结束:' . $sn);

            } elseif (array_get($message, 'result_code') === 'FAIL') {
                Order::where('pay_sn', $sn)->update([ 'status' => -2 ]);
            }
            WxService::sendOrderMsg($sn);
            return ['status' => 'complete'];

        } catch (\Exception $e) {
            Log::error('支付回调出错:' . $e->getMessage() . '[FILE]' . $e->getFile() . '[LINE]' . $e->getLine());
            return [
                'status' => 'fail',
                'msg' => '支付回调出错:' . $e->getMessage(),
            ];
        }
    }


//    private function handleOrder($message)
//    {
//        try {
//            $sn = $message['out_trade_no'];
//            $pay_log = Proceeds::where('sn', $sn)->first();
//            $order = Order::where('pay_sn', $sn)->first();
//            if ($pay_log->getOriginal('status') == 1) {
//                Log::error('出现已支付回调:' . $sn);
//                return ['status' => 'complete'];
//            }
////            if (!in_array($pay_log->getOriginal('type'), [1, 2])) {
////                Log::error('支付回调失败:' . $sn);
////                return ['status' => 'complete'];
////            }
//
//            // 支付成功后的业务逻辑
//            if ($message['return_code'] !== 'SUCCESS') {
//                return [
//                    'status' => 'fail',
//                    'msg' => '通信失败，请稍后再请求',
//                ];
//            }
//            if (array_get($message, 'result_code') === 'SUCCESS') {
//
//                Proceeds::where('sn', $sn)->update(['status' => 1, 'paid_at' => now(),'remark' => '微信订单号'. $message['transaction_id']]);
//                Order::where('pay_sn', $sn)->update(['status' => 1, 'paid_at' => now()]);
//
////              'order_type' => [ 1 =>'团体课订单', 2 =>'一对一课订单', 3 =>'商品订单' ],
//                if (in_array($order->getOriginal('type'), [1, 2])) {
////                    if ($order->getOriginal('type') == 2) {         // 2 一对一
////                        $course_ids = OrderItem::where('order_id', $order->id)->pluck('item_id');
////                        $courses = Course::whereIn('id', $course_ids)->get()->toArray();
////                    }
////                    CourseSign::generateByOrderId( $order->id, $order->member_id );
//                    MemberProduct::incByOrder( $order->id, $order->member_id );
//                }
//                // 商品订单
//                if ($order->getOriginal('type') == 3) {
//                    Goods::incSaleQuantityByOrderId( $order->id );
//                }
//                Log::notice('订单支付回调结束:' . $sn);
//
//            } elseif (array_get($message, 'result_code') === 'FAIL') {
//                Order::where('pay_sn', $sn)->update([ 'status' => -2 ]);
//            }
//            WxService::sendOrderMsg($sn);
//            return ['status' => 'complete'];
//
//        } catch (\Exception $e) {
//            Log::error('支付回调出错:' . $e->getMessage() . '[FILE]' . $e->getFile() . '[LINE]' . $e->getLine());
//            return [
//                'status' => 'fail',
//                'msg' => '支付回调出错:' . $e->getMessage(),
//            ];
//        }
//    }

    private function getNotify(Request $request = null)
    {
        $request = Request::createFromGlobals();

        try {
            $xml = XML::parse(strval($request->getContent()));
        } catch (\Throwable $e) {
            Log::error('支付回调XML解析错误:' . $e->getMessage());
            throw new \Exception($e->getMessage(), 500);
        }

        if (!is_array($xml) || empty($xml)) {
            Log::error('Invalid request XML');
            throw new \Exception('Invalid request XML', 500);
        }

        return $xml;
    }

    public function refundNotice()
    {
        $app = Factory::payment(Config::refundConfig());
        $response = $app->handleRefundedNotify(function ($message, $reqInfo, $fail) {
            Log::notice('退款结果数据message' . var_export($message, true));
            Log::notice('退款结果数据' . var_export($reqInfo, true));
            // 其中 $message['req_info'] 获取到的是加密信息
            // $reqInfo 为 message['req_info'] 解密后的信息

            $refund = OrderRefund::where('refund_sn',$reqInfo['out_refund_no'])->first();
            if (!$refund || $refund->status == 2) { // 如果订单不存在 或者 订单已经退过款了
                return true; // 告诉微信，我已经处理完了，订单没找到，别再通知我了
            }
            if($message['return_code']=='SUCCESS'){
                if($reqInfo['refund_status']=='SUCCESS'){
                    $refund->status = 2;
                    $refund->success_time = $reqInfo['success_time'];
                    $refund->save();
                }else{
                    Log::alert('退款返回Fail: '.var_export($message, true));
                    return $fail('退款出错');
                }
            }
            return true; // 返回 true 告诉微信“我已处理完成”
            // 或返回错误原因 $fail('参数格式校验错误');
        });
        return $response;
//        //$message
//        array (
//            'return_code' => 'SUCCESS',
//            'appid' => 'wx****',
//            'mch_id' => '****',
//            'nonce_str' => '49bb7b676af7c4ac13987095817eb655',
//            'req_info' => '5YbETX1lmnze3rqH/sLX56Qh/3lFi/kzTRJpB+/+HIINrRbo7+hNsr5cXJaLc8Egqrbs+hGbp2ek4yp8cb+XK+a/dnK9UYvEq9u+J8LMCBW5bhaO7ReplHWK8Epxw8F0UnRPJUFKywLwHOdLQGFEMwoEgM92td0PaT4iObYp4mlb8BTe4b3oxN5bUXBYu8B8sFOXzHuHmO8GNjlYPCNNftSjV2BSCz2X9toPzxuhklk3oy8kxYjruzPGj6WtmcvFLx7Xy3Y2hleu2hhaOZ8/kn3FmG6ql2lnMIaq+z2wYeEY3xoy2dA9yx7DgvpNoL89/CDiCQtFhiZcAt3+dzi42dNQ6N9p/8102F9f6jaESFXocitsZCsxPDruRO+/evXi8JOhwu9qfTgSw1pq1ZkgNGRP0AV6GVf98HIjV0TGYcJOcAFqc9D+wNkzMZi7uyDvtuoMr2XiaNDY0l8cP7mkCK3MFypbCLlLuD6smkqamVggdM1ppf8rWhcWlWukUP8e9h/+M9+xLDfOTNV2KXPpQHCf3MiFzAzcyGXNdfPMaxLJ+Xzzos8Zu1r5Jvek+Ab3Rz6hc06vawsvsDE8RD7sI0gN4+sG2dJ6Ssfp1bWVnc6Nl882x4TZc8s+DA4M8ULfPgK/rmnQ8831YsJCZK9T7t/H36qjB1xraaCcZX9z+LFP1eUbcTRvWs39QEzjXJPpuLOoeHVBoNzM1iHqPf/c5DN2r7wnwH/ihvrW08VKWMXTrSYDW5seOBNbx0i5QMEQO7qBdVtj5vOrUqSuz0vABhppIvKn3MgVmycOqfno9SF4r7d09FhS6lUzsXCNu7Q6nNUWWMXZF8Lwy94RXN4hvyH1rHlRl2KuIl/rMMCiiXG6MbUxbbhIqx6tof6N11U0ws73u89v3C+Qm+tuELOpbDsnz38L91jyBkVJTgdVGACWQAzwfJmfRIZTf2A2OTnkoUhe/BLeKNstY0LZdH0miPnSGtL4bPWrYfolg1+P5OqOedH2NV39BEc0becIYzDb+zo0KB3M8Vs1KpXLtD59CI=',
//        )
////$reqInfo
//array (
//    'out_refund_no' => 'r2018******38511',
//    'out_trade_no' => '2018*****8290675',
//    'refund_account' => 'REFUND_SOURCE_UNSETTLED_FUNDS',
//    'refund_fee' => '1',
//    'refund_id' => '5000040830678091106300227403',
//    'refund_recv_accout' => '支付用户零钱',
//    'refund_request_source' => 'API',
//    'refund_status' => 'SUCCESS',
//    'settlement_refund_fee' => '1',
//    'settlement_total_fee' => '1',
//    'success_time' => '2018-08-11 17:11:24',
//    'total_fee' => '1',
//    'transaction_id' => '4200000180201809116557984948',
//)
    }

}