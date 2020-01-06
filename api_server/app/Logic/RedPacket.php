<?php

namespace App\Logic;

/*
 * 红包逻辑类
 * 会员的红包储存在 member_profile 的 red_packet 里
 * member_profile 的 red_packet_temp 是已消费残留红包额，如果有过期的，现从这里扣，不够再从red_packet里扣
 *
 *
 *
 * */

use App\Exceptions\ApiException;
use App\Model\MemberProfile;
use App\Model\RedPacketLog;
use App\Model\Setting;
use Carbon\Carbon;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;

class RedPacket
{
    public static function pay($member_id,$order_id,$amount)
    {
        $amount = abs($amount);
//        DB::beginTransaction();
//        try {
            $mem = MemberProfile::where('member_id', $member_id)->first();
            if($mem->red_packet < $amount)
                throw new ApiException("您的红包不足");

            $mem->red_packet -= $amount;
            $mem->red_packet_temp += $amount;
            $mem->save();

            RedPacketLog::insert([
                'member_id' => $member_id,
                'order_id' => $order_id,
                'amount' => - $amount,
                'created_at' => now(),
                'stage' => 'order',
            ]);

//        }catch (\Exception $e) {
//            DB::rollBack();
//            throw new ApiException("红包支付失败");
//        }
    }
    // 获取红包随机值 面额越大 几率越小
    private static function calAmount()
    {
        $setting = Setting::getSetting();
        $min = $setting->red_packet_min >= 0 ? $setting->red_packet_min : 0;
        $max = $setting->red_packet_max >= 1 ? $setting->red_packet_max : 1;
        $amount = makeRedPacketAmount($min, $max);
        return $amount<= 0 ? 1 : $amount ;
    }
    public static function microtime_float()
    {
        list($usec, $sec) = explode(" ", microtime());
        return ((float)$usec + (float)$sec);
    }
    // 收入红包
    public static function income($member_id, $friend_id, $stage)
    {
        $setting = Setting::getSetting();
        // 判断今日次数
        if(RedPacketLog::where('member_id', $member_id)->where("created_at", '>', Carbon::today())->count() >= $setting->red_packet_day_times)
            throw new ApiException("今日获取红包次数已达上限" . $setting->red_packet_day_times);

        if(RedPacketLog::where("friend_id", $friend_id)->where("created_at", '>', Carbon::today())->count() >= $setting->red_packet_help_times)
            throw new ApiException("今日助力次数已达上限" . $setting->red_packet_help_times);

        if(RedPacketLog::where('member_id', $member_id)->where("friend_id", $friend_id)->where("created_at", '>', Carbon::today())->count() >= $setting->red_packet_helpsame_times)
            throw new ApiException("今日给同一人助力已达上限" . $setting->red_packet_helpsame_times);

//        var_dump($member_id);
//        var_dump($friend_id);die;

        $amount = self::calAmount();
        $now = now();
        $member = MemberProfile::where('member_id', $member_id)->first();
        if($member) {
            $member->red_packet += $amount;
            $member->save();

            RedPacketLog::create([
                'member_id' => $member_id,
                'amount' => $amount,
                'created_at' => now(),
                'expired_at' => now()->addMonths(3),
                'stage' => $stage,
                'friend_id' => $friend_id,
            ]);
        }
        // todo 此处需要通知

        return $amount;
    }

    // 处理批量过期 每天凌晨执行
    public static function handleMultiExpire()
    {
        DB::beginTransaction();
        try {
            $expired_list = RedPacketLog::select("member_id", DB::raw('SUM(amount) as total'))
                ->where("expired_at", "<", Carbon::today())
                ->where("handle_status", 0)
                ->where("stage", "share")
                ->groupBy('member_id')
                ->pluck("total", "member_id")
                ->toArray();

            if (empty($expired_list)) {
                echo "None Need Expired";
                return null;
            }

            $members_rp = MemberProfile::whereIn("member_id", array_keys($expired_list))->get()->toArray();

            $mem = [];
            foreach ($members_rp as $m) {
                $mem[$m["member_id"]] = [
                    "red_packet" => $m["red_packet"],
                    "red_packet_temp" => $m["red_packet_temp"],
                    "red_packet_residue" => $m["red_packet"], // 用于直接更新
                    "red_packet_temp_residue" => $m["red_packet_temp"],  // 同上
                ];
            }

            foreach ($expired_list as $mid => $amount) {
                if($mem[$mid]['red_packet_temp'] >= $amount) {    // 消费后的累计余额够扣的，那么直接扣
                    $mem[$mid]['red_packet_temp_residue'] -= $amount;
                }
                if ($mem[$mid]['red_packet_temp'] < $amount) {    // 消费后的累计余额不够扣的，那么就从余额里扣  注意如果red_packet出现负数 就需要排错
                    $mem[$mid]['red_packet_temp_residue'] = 0;
                    $mem[$mid]['red_packet_residue'] -= ($amount - $mem[$mid]['red_packet_temp']);
                }
                if ($mem[$mid]['red_packet'] != $mem[$mid]['red_packet_residue'] || $mem[$mid]['red_packet_temp'] != $mem[$mid]['red_packet_temp_residue']) {
                    MemberProfile::where("member_id", $mid)->update([
                        'red_packet' => $mem[$mid]['red_packet_residue'],
                        'red_packet_temp' => $mem[$mid]['red_packet_temp_residue'],
                    ]);

                    DB::table("red_packet_expire_log")->insert([
                        "member_id" => $mid,
                        "created_at" => now(),
                        "remark" => " 过期金额".$amount
                            ." 原红包余额:".$mem[$mid]['red_packet']
                            ." 新红包余额:".$mem[$mid]['red_packet_residue']
                            ." 原残值:".$mem[$mid]['red_packet_temp']
                            ." 新残值:".$mem[$mid]['red_packet_temp_residue'],
                    ]);
                }
            }

            RedPacketLog::where("expired_at", "<", Carbon::today())
                ->where("handle_status", "=", 0)
                ->where("stage", "share")
                ->update(["handle_status" => 1]);

            DB::commit();
            echo "OK";
        }catch (\Exception $e) {
            DB::rollBack();
            Log::error("红包过期程序出错: ".$e->getMessage());
        }
        return null;
    }

}