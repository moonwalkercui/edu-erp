<?php

namespace App\Logic\ApiMember;

use App\Exceptions\ApiException;
use App\Logic\RedPacket;
use App\Model\Order as OrderModel;
use App\Model\Course;
use App\Model\Goods;
use App\Model\MemberAddress;
use App\Model\MemberProduct;
use App\Model\MemberProfile;
use App\Model\Order;
use App\Model\OrderItem;
use App\Model\OrderRefund;
use App\Model\Proceeds;
use App\Model\ProductSpecification;
use App\Model\Setting;
use App\Model\Voucher;
use App\Service\Api\MemberService;
use App\Service\WeChat\Payment;
use Illuminate\Support\Facades\DB;

class OrderLogic
{
    public static function pay($order_id)
    {
        if(Setting::checkWxPay() == false) throw new ApiException('未开通微信支付 请线下付款');
//        var_dump($order_id);die;
        $new_sn = makeSn();
        $order = OrderModel::where('id', $order_id)->first();
        Proceeds::where('sn',$order->pay_sn)->update([
            'sn' => $new_sn
        ]);
        $order->pay_sn = $new_sn;
        $order->save();
        $item_name_arr = $order->items->pluck('item_name')->toArray();
        return [
            'msg' => '订单支付中',
            'pay_money' => $order->pay_money,
            'pay_info' => Payment::prepay([
                'pay_sn' => $new_sn,
                'money' => $order->pay_money,
                'body' => implode(',',$item_name_arr),
            ]),
        ];
    }
    // $money 是已经减去了优惠后的，比如红包抵值和代金券的
    public static function make($money, $spec_id, $red_packet, $course_id = null, $voucher_id = null, $pay_type = 'wxpay')
    {
        $red_packet = abs($red_packet);
        $order_sn = makeSn();
        $spec = ProductSpecification::find($spec_id);

        $price = floatval($spec->price);
        $member = MemberService::getMember();
        $member_id = $member->member_id;
        $product = $spec->product;

        if(MemberProduct::where('product_id', $product->id)->count() >= $product->quantity)
            throw new ApiException('名额不足');
        if($product->start_at != null && (
            strtotime($product->start_at) > time() ||
            strtotime($product->end_at . ' 23:59:59') < time()
            )) throw new ApiException('不在报名时间内无法报名');

        $cut_money = 0;
        if ($voucher_id) {
            $voucher = Voucher::find($voucher_id);
            if ($voucher->catch_price != 0 && $voucher->catch_price > $price) {
                throw new ApiException('未达到代金券不能使用代金券');
            }
            $cut_money = $voucher->price;
//            dump($voucher);
        }

        $cut_money += $red_packet;
        if($red_packet > $product->red_packet_max ) throw new ApiException('红包抵值金额超限');

        // 如果余额不足，那么就是现金支付，需要判断现金实付金额是否和系统金额一致。排除金额小于0的情况。
        if (floatval($money) == 0)
            throw new ApiException('不支持零元支付');
        $money = floatval($money);
        if (($money > 0 && $price - $cut_money  != $money) || $money < 0)
            throw new ApiException('支付金额有误');
//        if ($balance < 0) {
//            throw new ApiException('余额支付金额有误');
//        }

        DB::beginTransaction();

        try {
            // 如果支付金额是0 说明是余额支付
//            if ($balance > 0) {
//                // 扣减余额并留记录
//                OrderService::balancePayOrder(MemberService::getMemberId(), $balance, $money, $order_sn);
//            }
            $pay_sn = makeSn();
            // 大于0的需要走微信支付
            $order = [
                'sn' => $order_sn,
                'status' => 0,
                'pay_sn' => $pay_sn,
                'member_id' => $member_id,
                'type' => $product->getOriginal('type'), // 课程类型
                'total_price' => $price,
                'pay_money' => $money,
                'cut_money' => $cut_money,
                'pay_type' => $pay_type,
                'salesman_uname' => $member->salesman_uname,
//                'balance_pay' => $balance,
//                'badge_ids' => $product->category_ids ? implode(',', Badge::whereIn('category_id', explode(',', $product->category_ids))->pluck('id')->toArray()) : null,
            ];

            $order = Order::create($order);
            if (false === $order) {
                throw new \Exception('订单生成失败');
            }

            self::makeProceedsLog($member_id, $money, $pay_sn, $order->id, $pay_type);

            $item_name = $product->name . $spec->name;
            if ($course_id) {
                $course = Course::find($course_id);
                $item_id = $course_id;
                $item_type = 'Course';
                $item_name .= '(' . $course->date . ' ' . $course->start_at . ')';
            } else {
                $item_id = $product->id;
                $item_type = 'Product';
            }

            $order_item = [
                'order_id' => $order->id,
                'item_id' => $item_id,
                'item_type' => $item_type,
                'item_name' => $item_name,
                'item_spec' => $spec_id,
                'price' => $price,
                'deal_price' => $price - $cut_money,
                'num' => 1,
                'remark' => '',
                'created_at' => now(),
                'courses_quantity' => $course_id ? 0 : $spec->courses_quantity,
            ];

            $res_oi = OrderItem::insert($order_item);

            if (false === $res_oi) {
                throw new \Exception('订单项目记录生成失败');
            }
            if ($voucher_id) {
                Voucher::useVoucher($voucher_id, $member_id, $order_sn);
            }
            MemberProfile::becomeStudent($member_id);

            RedPacket::pay($member_id, $order->id, $red_packet);

        } catch (\Exception $e) {
            DB::rollBack();
            throw new ApiException($e->getMessage());
        }
        DB::commit();
        return [
            'msg' => '订单已经生成请支付',
            'pay_money' => $money,
            'pay_info' => $pay_type == 'wxpay' ? Payment::prepay([
                'pay_sn' => $pay_sn,
                'money' => $money,
                'body' => $item_name,
            ]) : [],
        ];

    }
    // 注意 ：包括商品订单
    public static function cancel($id)
    {
        $order = Order::where('id', $id)->first();
        if ($order->status != 0) {
            throw new ApiException('仅未支付订单可取消');
        }
        return  Order::where('id', $id)->update(['status' => -1]);
    }

    // 注意 ：包括商品订单
    public static function refund($id)
    {
        $order =  Order::where('id', $id)->first();
        if ($order->getOriginal('status') != 1) {
            throw new ApiException('仅已支付的订单可申请退款'); // todo
        }
        if (OrderRefund::where('order_id' , $order->id)->first()) {
            throw new ApiException('请勿重复申请');
        }
        $res = Order::where('id', $id)->update(['status' => 9]);

        $res1 = OrderRefund::insert([
            'order_id' => $order->id,
            'refund_sn' => makeSn(),
            'total_fee' => $order->pay_money,
            'refund_fee' => $order->pay_money,
            'created_time' => now(),
            'status' => 0,
        ]);
        return $res && $res1;
    }

    public static function makeGoodsOrder($member_id, $code, $address_id, $remark, $pay_type = 'wxpay')
    {
        $decrypt = decrypt($code);
        $goods_list = json_decode($decrypt['goo'], true);
        $money = $decrypt['tot'];

        $order_sn = makeSn();
        $pay_sn = makeSn();

        $address = MemberAddress::where('id', $address_id)->first();

        DB::beginTransaction();

        try {
            $order = [
                'sn' => $order_sn,
                'status' => 0,
                'pay_sn' => $pay_sn,
                'member_id' => $member_id,
                'type' => 3,
                'total_price' => $money,
                'pay_money' => $money,
                'cut_money' => 0,
                'balance_pay' => 0,
                'remark' => '[收货地址]'. $address->name.$address->mobile.' '.$address->address . ' 其他说明:'. $remark,
            ];
            $order = Order::create($order);
            if (false === $order) {
                throw new \Exception('订单生成失败');
            }
            self::makeProceedsLog($member_id, $money, $pay_sn, $order->id, $pay_type, 'Goods');
            $order_item = [];
            $goods_name = '';
            foreach ($goods_list as $g) {
                $goods = Goods::where('id', $g['id'])->first();
                $order_item[] = [
                    'order_id' => $order->id,
                    'item_id' => $g['id'],
                    'item_type' => 'Goods',
                    'item_name' => $goods->name,
                    'price' => $goods->price,
                    'deal_price' => $goods->price,
                    'num' => $g['num'],
                    'remark' => '',
                    'created_at' => now(),
                ];
                $goods_name .= $goods->name;
            }
            $res_oi = OrderItem::insert($order_item);
            if (false === $res_oi) {
                throw new \Exception('订单项目记录生成失败');
            }
            DB::commit();
        } catch (\Exception $e) {
            DB::rollBack();
            throw new ApiException($e->getMessage());
        }
        return [
            'msg' => '订单已经生成请支付',
            'pay_money' => $money,
            'pay_info' => Payment::prepay([
                'pay_sn' => $pay_sn,
                'money' => $money,
                'body' => $goods_name,
            ]),
        ];
    }
    private static function makeProceedsLog($member_id,$money,$pay_sn, $order_id, $pay_type, $target_type = 'Order')
    {
        if ($money > 0) {
            $res_p = Proceeds::insert([
                'sn' => $pay_sn,
                'pay_type' => $pay_type,
                'member_id' => $member_id,
                'target_type' => $target_type,
                'target_id' => $order_id,
                'money_receivable' => $money,
                'created_at' => now(),
                'updated_at' => now(),
                'status' => 0,
            ]);
            if (false === $res_p) throw new ApiException('支付记录生成失败');
        }
    }


}