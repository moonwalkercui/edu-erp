<?php

namespace App\Api\V1\Controllers\member;

use App\Logic\ApiMember\GiftLogic;
use App\Model\GiftCategory as GiftCategoryModel;
use App\Model\Gift as GiftModel;
use App\Model\GiftOrder as GiftOrderModel;
use App\Service\Api\MemberService;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class Gift extends Base
{
    public function confirm(Request $request)
    {
        if(!$request->info) return $this->error('缺少参数info');
        list($list, $total) = GiftModel::getTotalPrice($request->info);

        $data['list'] = $list;
        $data['total'] = $total;
        $data['code'] = encrypt([
            'goo' => $request->info,
            'tot' => $total
        ]);

        return $this->fetch($data);
    }

    public function exchange(Request $request)
    {
        $this->validateParam($request->all(), [
            'id' => 'required',
        ]);
        return GiftLogic::exchange(MemberService::getMemberId(), $request->id) ?
            $this->success('兑换成功') :
            $this->error('兑换失败') ;
    }

    public function orders(Request $request)
    {
        $where['member_id'] = MemberService::getMemberId();
        if ($request->filled('stage')) {
            $where['stage'] = getStatusValue($request->stage, 'gift_order');
        }
        $list = GiftOrderModel::with('gift')->where($where)->orderBy('id','desc')->paginate(20);
        return $this->fetch($list);
    }
}