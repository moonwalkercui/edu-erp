<?php

namespace App\Logic\ApiMember;

use App\Exceptions\ApiException;
use App\Model\Gift as GiftModel;
use App\Model\MemberProfile as MemberProfileModel;
use App\Model\GiftOrder;
use Illuminate\Support\Facades\DB;

class GiftLogic
{
    public static function exchange($member_id, $gift_id)
    {
        $gift = GiftModel::where('id', $gift_id)->first();
        if ($gift->storage <= 0) throw new ApiException('库存不足');
        if ($gift->getOriginal('status') != 1) throw new ApiException('礼品已下架');

        $member_points = MemberProfileModel::where('member_id', $member_id)->value('points');
        if ($member_points < $gift->points) throw new ApiException('您的小星星数量不足');
        $insert = [
            'gift_id' => $gift_id,
            'gift_num' => 1,
            'points' => $gift->points,
            'member_id' => $member_id,
            'created_at' => now(),
            'stage' => 0,
        ];
        DB::beginTransaction();
        try {
            GiftOrder::insert($insert);
            MemberProfileModel::where('member_id', $member_id)->decrement('points', $gift->points);
            GiftModel::where('id', $gift_id)->update([
                'exchange_num' => $gift->exchange_num + 1,
                'storage' => $gift->storage - 1,
            ]);

            DB::commit();
        } catch (\Exception $e) {
            DB::rollBack();
            throw new ApiException($e->getMessage());
        }
        return true;
    }

}