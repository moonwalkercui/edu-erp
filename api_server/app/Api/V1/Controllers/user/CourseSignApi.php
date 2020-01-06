<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\CourseSignLogic;
use App\Model\CourseSign as CourseSignModel;
use App\Model\MemberProduct;
use App\Model\Resources\CourseSignResource;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class CourseSignApi extends Base
{
    // 查询已签到的列表
    public function getAll(Request $request)
    {
        $where = [];
        if($request->filled('search_member'))
            array_push($where, ['member_id' , '=' , $request->input('search_member')]) ;
        if($request->filled('search_course'))
            array_push($where, ['course_id' , '=' , $request->input('search_course')]) ;
        $model = CourseSignModel::with(['course','product','member'])->where($where)->orderBy('id','desc');

        if($request->filled('page')){
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        }else{
            $res = $model->get();
        }
        return $this->fetchResource(CourseSignResource::collection($res));
    }
    public function getWithDeal(Request $request)
    {
        $deal_data = MemberProduct::where('product_id', $request->search_product)->with('member')->get();
        $signed_data = CourseSignModel::where('course_id', $request->search_course)->get();

        $sign_time = $course_ids = $points = [];
        foreach ($signed_data as $v) {
            $sign_time[$v->member_id] = $v->sign_at;
            $course_ids[$v->member_id] = $v->course_id;
            $points[$v->member_id] = $v->point;
        }

        $data = [];
        foreach ($deal_data as $d) {
            $data[] = [
                'member_id' => $d->member->member_id,
                'member_nickname' => $d->member->nick_name,
                'member_name' => $d->member->name,
                'member_mobile' => $d->member->mobile,
                'course_id' => isset($course_ids[$d->member_id]) ? $course_ids[$d->member_id] : '',
                'sign_at' => isset($sign_time[$d->member_id]) ? $sign_time[$d->member_id] : '',
                'points' => isset($points[$d->member_id]) ? $points[$d->member_id] : '',
            ];
        }

        return $this->fetch($data);
    }
    public function allograph(Request $request)
    {
        $this->validateParam($request->all() , [ 'course' => 'required','member' => 'required' ]);
        if(CourseSignLogic::allograph($request->course, $request->member)){
            return $this->success('已代签');
        }
    }
    public function givePoints(Request $request)
    {
        $this->validateParam($request->all() , [
            'course' => 'required',
            'member' => 'required',
            'points' => 'required',
        ]);
        if(CourseSignLogic::givePoints($request->course, $request->member, $request->points)){
            return $this->success('已奖励');
        }
    }
    public function delete(Request $request)
    {
        $this->validateParam($request->all() , [ 'ids' => 'required' ],[ 'ids.required' => '未选择记录' ]);
        if(CourseSignLogic::delete($request->input('ids'))){
            return $this->success('已删除');
        }
    }
//    public function create(Request $request)
//    {
////        $this->validateParam($request->all() , [
////            'member_name' => config('system.validate.member_name'),
////            'mobile' => config('system.validate.member_mobile'),
////        ]);
//
//        if ( CourseLogic::save($request) )
//            return $this->success('已创建');
//
//    }
}