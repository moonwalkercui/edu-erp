<?php
namespace App\Service\Api;

use App\Exceptions\ApiException;
use App\Model\MemberProfile as MemberProfileModel;

class OrderService
{
    public static function balancePayOrder($member_id, $balance, $order_sn)
    {
        if($balance < 0) throw new ApiException('余额支付金额错误');
        $balance_db = MemberProfileModel::where('member_id', $member_id)->value('balance');
        if($balance_db - $balance < 0)  throw new ApiException('余额支付金额错误');

        MemberProfileModel::where('member_id', $member_id)->decrement('balance', $balance);

        self::makeMoneyLog($member_id, -$balance, 1, '订单号:'.$order_sn );

    }
    // $in_trans 是否在更新之前写日志，常用在事务内，事务的是true
    public static function makeMoneyLog($member_id, $change_balance, $stage, $remark)
    {
        // 这里的db要用model查询 to do
//        return DB::table('member_balance_log')->insert([
//            'member_id' => $member_id,
//            'balance_change' => $change_balance,
//            'stage' => $stage,
//            'remark' => $remark,
//            'created_at' => now(),
//        ]);
    }


}
