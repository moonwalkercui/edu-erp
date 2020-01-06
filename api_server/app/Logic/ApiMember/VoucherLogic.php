<?php
namespace App\Logic\ApiMember;

use App\Exceptions\ApiException;
use App\Model\MemberVoucher as MemberVoucherModel;
use App\Service\Api\MemberService;

class VoucherLogic
{
    public static function getOne($id)
    {
        $member = MemberService::getMember();

        $map = [
            'member_id' => $member->member_id,
            'voucher_id' => $id,
        ];
        if(MemberVoucherModel::where($map)->first())
            throw new ApiException('不能重复获取');

        $member->vouchers()->attach($id, ['got_time' => now()]);
        return true;
    }
    public static function useOne($id)
    {
        $member = MemberService::getMember();
        $map = [
            'member_id' => $member->member_id,
            'voucher_id' => $id,
        ];
        if(MemberVoucherModel::where($map)->where('be_used', 0)->first() == false)
            throw new ApiException('无效优惠券');

        return MemberVoucherModel::where($map)->update(['be_used' => 1]);

    }
}