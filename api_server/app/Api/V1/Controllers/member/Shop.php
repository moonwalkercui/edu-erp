<?php

namespace App\Api\V1\Controllers\member;

use App\Exceptions\ApiException;
use App\Logic\ApiMember\OrderLogic;
use App\Model\Order as OrderModel;
use App\Model\Goods;
use App\Model\GoodsCategory;
use App\Model\MemberAddress;
use App\Service\Api\MemberService;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class Shop extends Base
{
    public function getGoods(Request $request)
    {
        $list = GoodsCategory::with(['goods' => function($query){
            $query->where('status',1)->orderBy('sort','desc');
        }])->orderBy('sort','desc')->get();
        return $this->fetch($list);
    }

    public function confirm(Request $request)
    {
        if(!$request->info) return $this->error('缺少参数info');
        list($list, $total) = Goods::getTotalPrice($request->info);

        $data['list'] = $list;
        $data['total'] = $total;
        $data['code'] = encrypt([
            'goo' => $request->info,
            'tot' => $total
        ]);

        return $this->fetch($data);
    }
    public function getAddress(Request $request)
    {
        $data = MemberAddress::where('member_id', MemberService::getMemberId())->orderBy('last_use_time', 'desc')->get();
        return $this->fetch($data);
    }

    public function saveAddress(Request $request)
    {
        $this->validateParam($request->all(), [
            'name' => 'required',
            'mobile' => 'required',
            'address' => 'required',
        ]);

        $data = array_merge(cleanXss($request->all()), ['member_id' => MemberService::getMemberId()]);
        if ($request->filled('id')) {
            $res = MemberAddress::where('id', $request->id)->update($data);
        } else {
            $res = MemberAddress::insert($data);
        }
        return $res ? $this->success('已保存') : $this->error('未保存');
    }

    public function delAddress(Request $request)
    {
        $this->validateParam($request->all(), [
            'id' => 'required',
        ]);
        $res = MemberAddress::where('id', $request->id)->delete();
        return $res ? $this->success('已删除') : $this->error('删除失败');
    }

    public function makeOrder(Request $request)
    {
        throw new ApiException('演示版本不支持微信支付');
        $this->validateParam($request->all(), [
            'address' => 'required',
            'code' => 'required',
        ]);

        return $this->fetch(OrderLogic::makeGoodsOrder(MemberService::getMemberId(), $request->code, $request->address, $request->remark));
    }

    public function getOrders(Request $request)
    {
        $where = [];
        $where['member_id'] = MemberService::getMemberId();

        if ($request->filled('status')) {
            $where['status'] = getStatusValue($request->status, 'order');
        }


        if ($request->filled('search_status'))
            array_push($where, ['status', '=', intval($request->search_status)]);

        $model = OrderModel::with('items.item','refunds')->where('type', 3)->where($where)->orderBy('id', 'desc');

        if ($request->filled('page'))
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $data = $model->get();
        return $this->fetch($data);

    }

}