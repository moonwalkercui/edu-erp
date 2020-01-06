<?php
namespace App\Api\V1\Controllers\user;

use App\Model\Order;
use App\Model\RedPacketLog;
use App\Model\Resources\RedPacketLogResource;
use App\Model\Setting;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class ShareApi extends Base
{
    public function redPacketLog(Request $request)
    {
        $where = [];
        if($request->filled('search_member'))
            array_push($where, ['member_id' , '=' , $request->search_member]) ;
        if($request->filled('search_referral'))
            array_push($where, ['friend_id' , '=' , $request->search_referral]) ;
        if($request->filled('search_stage'))
            array_push($where, ['stage' , '=' , $request->search_stage]) ;
        if($request->filled('search_sn'))
            array_push($where, ['order_id' , '=' , Order::where("sn", $request->search_sn)->value('id')]) ;


        $model = RedPacketLog::with('member','order','referral')->where($where)->orderBy("created_at","desc");
//        if($request->filled('search_division') == false){
//            $internal_ids = RbacLogic::getViewDivisionIds( UserService::getUser() );
//            if($internal_ids) $model->whereIn('division_id',$internal_ids);
//        }
        if($request->filled('page'))
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $res = $model->get();
        return $this->fetchResource(RedPacketLogResource::collection($res));

    }
    public function redPacketSetting(Request $request)
    {
        $data = $request->all();
        $data = cleanXss($data);
        $this->validateParam($data , [
            'red_packet_min' => 'required|numeric|min:0|max:999',
            'red_packet_max' => 'required|numeric|min:0|max:999',
            'red_packet_day_times' => 'numeric|min:0|max:999',
            'red_packet_help_times' => 'numeric|min:0|max:999',
            'red_packet_helpsame_times' => 'numeric|min:0|max:999',
        ],[
            'red_packet_min.required'=>'缺少最小金额',
            'red_packet_min.max'=>'最小金额不能大于999',
            'red_packet_max.required'=>'缺少最大金额',
            'red_packet_max.max'=>'最大金额不能大于999',
            'red_packet_max.gt'=>'最大金额必须大于最小金额',
            'red_packet_day_times.max'=>'领红包次数不能大于999',
            'red_packet_help_times.max'=>'助力总次数不能大于999',
            'red_packet_helpsame_times.max'=>'给同一人助力次数不能大于999',
//            'red_packet_day_times.max'=>'zuida',
//            'red_packet_help_times.max'=>'缺少名称',
        ]);
        $data["red_packet_day_times"] = $request->input("red_packet_day_times", 10);
        $data["red_packet_help_times"] = $request->input("red_packet_help_times", 2);

//        if($data['red_packet_min'] >= $data['red_packet_max']) return $this->error("最小金额不能大于最大金额");

        unset($data['token']);
        if(Setting::saveSetting($data))
            return $this->success('已保存');
    }
}