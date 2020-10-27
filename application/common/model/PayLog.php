<?php

namespace app\common\model;


class PayLog extends BaseModel
{
    protected $table = 'pay_log';

    public function getPayTextAttr($value, $data)
    {
        $type = config('status.pay_type');
        return (isset($type[$data['pay_type']])) ? $type[$data['pay_type']] : '';
    }

    static function addOne($member_id, $order_sn, $money, $stage = 'order', $remark = '', $hope_id = null)
    {
        $data['order_sn'] = $order_sn;
        $data['member_id'] = $member_id;
        $data['money'] = $money;
        $data['status'] = 0;
        $data['add_time'] = time();
        $data['stage'] = $stage;
        $data['remark'] = $remark;
        $data['hope_id'] = $hope_id;
        $res = self::insertGetId($data);
        if ($res) return true;
        else return false;
    }

    // 支付完成处理 $payment_trade_no 第三方 对账用的
    static function handlePaid($order_sn, $paid_money, $payment_trade_no = null, $open_id = null, $pay_type = 'wx')
    {
        $paylog = self::where(compact('order_sn'))->find();
        if (!$paylog) {
            whiteLog("[ERROR] 支付后处理异常：未知支付记录".$order_sn, 'err_');
            return;
        }
        if ($paylog['status'] != 0) {
            whiteLog("[ERROR] 订单不是待付款状态 或 重复回调".$order_sn, 'err_');
            return;
        }
        try {

            $remark = "";

            $markOrder = function ($order_sn) {
                $order = Order::where("sn", $order_sn)->find();
                $order->status = Order::STATE_PAID;
                $order->time_pay = time();
                $order->save();
            };
            if ($paylog['stage'] == 'order') {
                $markOrder($order_sn);

            } elseif ($paylog['stage'] == 'vip') {
                $order = VipOrder::where("sn", $order_sn)->find();
                $order->status = Order::STATE_PAID;
                $order->time_pay = time();
                $order->save();

                $member = Member::where('id', $order['member_id'])->find();
                $member->is_vip = 1;
                $member->save();

                $ref_id = $member['referrer_id'];
                // 会员付费后推荐人逻辑
                $reward = config('conf_indb.referfer_reward');
                if ($ref_id && $reward) {
                    MemberMoney::where('uid', $ref_id)->setInc('red_package', $reward);
                    MemberMoneylog::makeLog($ref_id, $reward, '邀请人获得返佣红包', MemberMoneylog::TYPE_REDPACKAGE, $member['id']);
                }
            } elseif ($paylog['stage'] == 'hope') {

                $markOrder($order_sn);

                MemberHope::where('id', $paylog['hope_id'])->update([
                    'status' => 1,
                    'cometrue_uid' => $paylog['member_id'],
                    'cometrue_time' => time(),
                ]);

            } else {
                adminWarning("错误的支付", $order_sn);
                $remark.= "错误的支付";
            }
            self::where(compact('order_sn'))->update([
                'status' => 1,
                'pay_type' => $pay_type,
                'paid_time' => time(),
                'payment_trade_no' => $payment_trade_no,
                'remark' => $remark,
                'third_id' => $open_id,
                'paid_money' => $paid_money,
            ]);
        } catch (\Exception $e) {
            whiteLog("[ERROR] 支付后订单处理错误：". var_export($e, true), 'err_');
        }
    }

}