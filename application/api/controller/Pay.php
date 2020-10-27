<?php
namespace app\api\controller;

use app\backend\model\PayLog;
use app\backend\model\VipOrder;
/*
 * 支付相关处理
 * */
class Pay extends Base
{
    private function readyPay($order_sn)
    {
        $paylog = PayLog::where(compact('order_sn'))->find();
        if ($paylog['status'] != 0) {
            exception("订单不是待付款状态,请重新发起");
        }
        $order = null;
        if ($paylog['stage'] == 'order') {
            $order = \app\backend\model\Order::where("sn", $order_sn)->find();
        } elseif ($paylog['stage'] == 'vip') {
            $order = VipOrder::where("sn", $order_sn)->find();
        }
        if (!$order)
            exception("未知订单");
        return $order;
    }

    private static $ali_config = [
        // 沙箱模式
        'debug' => true,
        'appid' => '2021001161664774',
        // 开放平台的 应用私钥
        'private_key' => '',
        // 开放平台的 支付宝公钥
        'public_key' => "",
        'notify_url' => 'http://app.jushengwlkj.com/alipaynotify.html', // 可以应用的时候配置哦
        'return_url' => '', // 可以应用的时候配置哦
    ];

    // 支付宝支付参数
    function aliPayParam()
    {
        $this->checkLogin();
        $order_sn = input('order_sn');
        if (!$order_sn) return $this->errorJson("缺少订单号");
        $order = $this->readyPay($order_sn);
        try {
            // 请参考（请求参数）：https://docs.open.alipay.com/api_1/alipay.trade.app.pay
            $pay = \AliPay\App::instance(self::$ali_config);
            $result = $pay->apply([
                'out_trade_no' => $order_sn, // 商户订单号
                'total_amount' => 0.01, // $order['money_order'] // 支付金额
                'subject' => '支付订单' . $order_sn, // 支付订单描述
            ]);
            return $this->dataJson($result);
        } catch (\Exception $e) {
            return $this->errorJson($e->getMessage());
        }
    }

    // 订单支付后的业务处理
    function aliPayNotify()
    {
        $data = input('');
        whiteLog(">>>>>>>>>> 支付宝支付回调参数 <<<<<<<<<<<". var_export($data, true), "ali_");
        // 如果支付成功
        try {
            $pay = \AliPay\App::instance(self::$ali_config);
            $result = $pay->notify();
            if (in_array($result['trade_status'], ['TRADE_SUCCESS', 'TRADE_FINISHED'])) {
                // 更新订单状态，支付完成
                PayLog::handlePaid($result['out_trade_no'], $result['total_amount'], $result['trade_no'], $result['buyer_id'], 'ali');
                whiteLog("收到来自支付宝的异步通知 " . '订单号：' . $result['out_trade_no'] . '订单金额：' . $result['total_amount'], "ali_");
            } else {
                whiteLog("收到异步通知 但不是支付成功通知", "ali_");
            }
        } catch (\Exception $e) {
            whiteLog("[ERROR] 支付宝支付回调异常\n" . $e->getMessage() . "|" . $e->getFile() . "|" . $e->getLine(), "ali_");
        }
    }

    private static $wx_config = [
        'token' => 'test',
        'appid' => '',
        'appsecret' => '',
        'encodingaeskey' => '',
        // 配置商户支付参数（可选，在使用支付功能时需要）
        'mch_id' => "1587684681",
        'mch_key' => '',
        // 配置商户支付双向证书目录（可选，在使用退款|打款|红包时需要）
        'ssl_key' => '',
        'ssl_cer' => '',
        // 缓存目录配置（可选，需拥有读写权限）
        'cache_path' => '',
    ];

    // 微信支付参数
    function wxPayParam()
    {
        $this->checkLogin();
        $order_sn = input('order_sn');
        if (!$order_sn) return $this->errorJson("缺少订单号");
        $order = $this->readyPay($order_sn);
        try {
            // 实例对应的接口对象
            $app = new \WeChat\Pay(self::$wx_config);
            // 组装参数，可以参考官方商户文档
            $options = [
                'body' => '支付订单' . $order_sn,
                'out_trade_no' => $order_sn,
                'total_fee' => 1, // $order['money_order'] * 100, // 分
                'trade_type' => 'APP',
                'notify_url' => 'http://app.jushengwlkj.com/wxpaynotify.html',
            ];
//            商户服务器生成支付订单，先调用【统一下单API】生成预付单，获取到prepay_id后将参数再次签名传输给APP发起支付。
            $res = $app->createOrder($options);
            if (!isset($res['prepay_id'])) return $this->errorJson("未获取到prepay_id");
            $res = $app->createParamsForApp($res['prepay_id']);
            return $this->dataJson($res);

        } catch (\Exception $e) {
            return $this->errorJson($e->getMessage());
        }
    }

    // 订单支付后的业务处理
    function wxPayNotify()
    {
        try {
            $testxml = file_get_contents("php://input");
            $jsonxml = json_encode(simplexml_load_string($testxml, 'SimpleXMLElement', LIBXML_NOCDATA));
            $result = json_decode($jsonxml, true);
            whiteLog(">>>>>>>>>> 微信支付回调参数 <<<<<<<<<<<". var_export($result, true), 'wx_');
            // {"appid":"","bank_type":"CMB_CREDIT","cash_fee":"1","fee_type":"CNY","is_subscribe":"N","mch_id":"1587684681",
            //"nonce_str":"56yh1a08lxvbkya1jtqco83dn37v5fjg","openid":"oZuaDw8z6JOOqVCp5_v1UozqU8-4","out_trade_no":"20071543226772244",
            //"result_code":"SUCCESS","return_code":"SUCCESS","sign":"3A978B8918B65694731458044E70EAE0","time_end":"20200715171214","total_fee":"1",
            //"trade_type":"APP","transaction_id":"4200000616202007150991039280"
            $wechat = \WeChat\Pay::instance(self::$wx_config);
            $data = $wechat->getNotify();
            if ($data['return_code'] === 'SUCCESS' && $data['result_code'] === 'SUCCESS') {
                PayLog::handlePaid($result['out_trade_no'], $result['total_fee'] / 100,$result['transaction_id'], $result['openid'], 'wx');
                whiteLog("收到来自微信的异步通知 " . '订单号：' . $result['out_trade_no'] . '订单金额：' . $result['total_fee']/100, "wx_");
                ob_clean();
                echo $wechat->getNotifySuccessReply();
            }
        } catch (\Exception $e) {
            whiteLog('[ERROR] 微信支付回调异常' . $e->getMessage() . "|" . $e->getFile() . "|" . $e->getLine(), 'wx_');
        }
    }

}