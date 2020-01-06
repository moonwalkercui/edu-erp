<?php
namespace App\Api\V1\Controllers\member;

use App\Exceptions\ApiException;
use App\Model\CourseHomework as CourseHomeworkModel;
use App\Model\Course as CourseModel;
use App\Model\ApiMember\UserModel;
use App\Model\ApiMember\UserProfileModel;
use App\Model\CourseSign;
use App\Model\MemberProduct;
use App\Model\Resources\UserProfileResource;
use App\Model\Zone;
use App\Model\ZoneLog;
use App\Service\Api\MemberService;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;
use Illuminate\Support\Facades\DB;

class User extends Base
{
    public function getAll(Request $request)
    {
        $where = [];
        $model = UserProfileModel::where($where)->orderBy('sort','desc');
        if($request->filled('division')){
            $model->where('division_id',$request->division);
        }
        if($request->filled('page'))
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $res = $model->get();
        return $this->fetchResource(UserProfileResource::collection($res));
    }

    public function profile(Request $request)
    {
        if($request->uname == false) throw new ApiException('缺少参数');
//        $member = MemberService::getMember();
//        $member_id = $member->member_id;

        $user_id = \App\Model\User::where('username', $request->uname)->value('id');
        $data['profile'] = UserProfileModel::with('photo')->where('user_id', $user_id)->first();
//        $data['course_count'] = CourseSign::whereHas('course' , function ($query) use ($user_id) {
//            $query->where('user_id', $user_id);
//        })->where('member_id', $member_id)->count();
        return $this->fetch($data);
    }

    public function login(Request $request)
    {
        if($request->uname == false || $request->password == false)
            throw new ApiException('缺少参数');
        $data = UserModel::login($request->uname, $request->password);
        return $this->fetch($data);
    }
    public function signLog(Request $request)
    {
        if($request->code == false  )
            throw new ApiException('缺少参数');
        $uname = decrypt($request->code);
        $model = CourseSign::whereHas('course', function ($query) use ($uname) {
            $query->where('username',$uname);
        })->with(['member' => function($q) {
            $q->select('member_id','nick_name','name','avatar');
        },'product' => function($q) {
            $q->select('id','name');
        }])->orderBy('sign_at','desc');
        $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        return $this->fetch($res);
    }
    public function scanLog(Request $request)
    {
        if($request->code == false  )
            throw new ApiException('缺少参数');
        $uname = decrypt($request->code);

        $where['sign_username'] = $uname;
        $where['status'] = 1;
//        if (UserProfile::checkUserExist($where['scan_username']) == false)
//            throw new ApiException('参数错误');
        $model = CourseSign::with('course')->where($where)->orderBy('sign_at','desc');
        $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        return $this->fetch($res);
    }
    // 扫码 post  id=13 code=...
    public function scan(Request $request)
    {
        if($request->qrcode == false || $request->code == false )
            return $this->error('缺少参数');
        $uname = decrypt($request->code);
        list($sn, $id, $username) = explode('_', $request->qrcode);
        UserModel::checkStatus($username);

        if( $username != $uname ) {
            return $this->error('失败:您不是该课程的授课老师');
        }

//        if (UserProfile::checkUserExist($username) == false)
//            throw new ApiException('参数错误');

        if ($exist = CourseSign::with('course')->where('id', $id)->first()) {
            if ($exist->getOriginal('status') == 1) {
                return $this->error('已扫过了');
            }
            $exist->sign_at = now();
            $exist->status = 1;
            $exist->sign_username = $username;
            $res = $exist->save();
            return $res == 0 ? $this->error('扫码失败') : $this->success('签到成功');
        } else {
            return $this->error('无效二维码');
        }
    }

    /**
     * 获取课次日历里的日期
     */
    public function getCalendarDate(Request $request)
    {
        if($request->code == false  )
            throw new ApiException('缺少参数');
        $uname = decrypt($request->code);

        $data = CourseModel::select(array(DB::Raw('date as day')))
            ->where('username', $uname)
            ->groupBy('day')
            ->pluck('day');
        return $this->fetch($data);
    }
    public function getCourseByDay(Request $request)
    {

        $this->validateParam($request->all() , [
            'code' => 'required',
            'day' => 'required',
        ]);
        $uname = decrypt($request->code);
        return $this->fetch(
            CourseModel::withCount('homework')->with(['product'=>function($q){
                $q->select('id','name');
            }])->whereDate('date',$request->day)->where('username',$uname)->get()
        );
    }
    public function givePoint(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
            'points' => 'required',
            'code' => 'required',
        ]);
        $uname = decrypt($request->code);
        UserModel::checkStatus($uname);
        if(CourseSign::givePoint($uname,$request->id, $request->points))return $this->success('已发放');
    }
    public function allograph(Request $request)
    {
        $this->validateParam($request->all() , [
            'member' => 'required',
            'course' => 'required',
            'code' => 'required',
        ]);
        $uname = decrypt($request->code);
        UserModel::checkStatus($uname);
        if(CourseSign::allograph($request->member, $request->course, $uname)){
            return $this->success('代签成功');
        }
    }
    public function getMembers(Request $request)
    {
        if ($request->filled('course')){
            $course = \App\Model\Course::find($request->course);
//            $member_ids = MemberProduct::where('product_id',$course->product_id)->where('',)->pluck('member_id');
//            $res = MemberProfileModel::whereIn('member_id',$member_ids)->get();
//            $res = MemberProfileModel::join('member_product','member_profile.member_id','=','member_product.member_id')
//                ->where('member_product.product_id',$course->product_id)
//                ->whereIn('member_product.member_id',$member_ids)
//                ->get();
            $res = MemberProduct::join('member_profile','member_product.member_id','=','member_profile.member_id')
                ->where('member_product.product_id',$course->product_id)
                ->select(
                    'member_profile.member_id',
                    'member_profile.avatar',
                    'member_profile.name',
                    'member_profile.nick_name',
                    'member_profile.mobile',
                    'member_product.total_quantity',
                    'member_product.remaining_quantity'
                )
                ->get();
            $sings = CourseSign::where( 'course_id', $request->course )->pluck('sign_at','member_id');
            foreach ($res as &$v){
                $v->sign_at = isset($sings[$v->member_id]) ? $sings[$v->member_id] : '';
            }
        } else {
            $res = [];
        }
        return $this->fetch($res);
    }
    public function getHomework(Request $request)
    {
        $this->validateParam($request->all() , [
            'course' => 'required',
        ]);
        $res = CourseHomeworkModel::where('course_id',$request->course)->first();
        return $this->fetch($res);
    }
    public function saveHomework(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
            'code' => 'required',
            'caption' => 'required',
        ]);
        $uname = decrypt($request->code);
        if(CourseHomeworkModel::saveHomework(
            $uname,
            intval($request->id),
            cleanXss($request->caption),
            $request->input('images',null),
            cleanXss($request->input('remark',null))
        ))
            return $this->success('已保存');
        else
            return $this->error('保存失败');
    }
    public function zoneList(Request $request)
    {
        $model = Zone::withCount('logs')->orderBy('edit_time','desc');
//        $member_id = MemberService::getMemberId();
//        $my_lick = ZoneLog::where('member_id', $member_id)->pluck('zone_id')->toArray();

        if($request->filled('page'))
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
//        ->each(function($item) use ($my_lick){
//        if(in_array($item->id, $my_lick)) $item->like = 1;
//        else $item->like = 0;
//    })
        else
            $res = $model->get();

        return $this->fetch($res);
    }
    public function zoneLike(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
        ]);
        $res = Zone::handleLike($request->id, MemberService::getMemberId());
        return $this->success($res['msg'], $res['num']);
    }


}