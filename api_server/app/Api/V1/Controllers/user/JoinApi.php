<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\JoinLogic;
use App\Model\ApiUser\JoinApplicantModel;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class JoinApi extends Base
{

    // 申请加入我的组织的列表 todo 权限
    public function applicationList(Request $request)
    {
        $where = [];
        $model = JoinApplicantModel::orderBy('id','desc');
        if($request->filled('search_name'))
            $model->where('username', cleanXss($request->input('search_name')) )
                ->orWhere('user_name', cleanXss($request->input('search_name')) );

        if($request->filled('page'))
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $data = $model->get();
        return $this->fetch($data);
    }
    public function handleApprove(Request $request)
    {
        $this->validateParam($input = $request->all() , [
            'ids' => 'required',
            'division' => 'required',
        ],['division.required'=>'未选择门店']);
        if(JoinLogic::handleApplication($input['ids'], 1 ,$input['division'] , isset($input['remark']) ? $input['remark'] : ''))
            return $this->success('处理完毕');
    }
    public function handleReject(Request $request)
    {
        $this->validateParam($input = $request->all() , [
            'ids' => 'required',
        ]);
        if(JoinLogic::handleApplication($input['ids'], -1 ,isset($input['remark']) ? $input['remark'] : ''))
            return $this->success('处理完毕');
    }

//
//    // 发出邀请
//    public function inviteUser(Request $request)
//    {
//        $this->validateParam($request->all() , [
//            'username' => config('system.validate.username_check'),
//        ]);
//        $data = $request->only('username','remark');
//        if(InvitationLogic::inviteUser($data['username'],$data['remark']))
//            return $this->success('已发出邀请');
//    }
//    // 被邀请列表
//    public function invitedList(Request $request)
//    {
//        $user = $this->user_info;
//        $res = JoinInvitationModel::where('invited_username',$user->username)
//            ->paginate($request->input('per_page', config('system.number_paginate')));
//        return $this->fetch($res);
//    }
//    // 拒绝邀请
//    public function inviteReject(Request $request)
//    {
//        $this->validateParam($request->all() , [
//            'username' => config('system.validate.username_check'),
//        ]);
//        if(InvitationLogic::inviteReject($request->input('username')))
//            return $this->success('已取消邀请');
//    }
//    // 取消邀请
//    public function inviteCancel(Request $request)
//    {
//        $this->validateParam($request->all() , [
//            'username' => config('system.validate.username_check'),
//        ]);
//        if(InvitationLogic::inviteCancel($request->input('username')))
//            return $this->success('已取消邀请');
//    }
//    // 我的邀请列表
//    public function myInvitation(Request $request)
//    {
//        $user = $this->user_info;
//        $res = JoinInvitationModel::where('username',$user->username)->where('organization_id',$user->organization_id)
//            ->paginate($request->input('per_page', config('system.number_paginate')));
//        return $this->fetch($res);
//    }
//
//
//    // 我的申请
//    public function myApplication(Request $request)
//    {
//        $user = $this->user_info;
//        $res = JoinApplicantModel::where('username',$user->username)->withoutGlobalScope(OrgScope::class)
//            ->paginate($request->input('per_page', config('system.number_paginate')));
//        return $this->fetch($res);
//    }
//
//    // 取消申请 oid:organization_id
//    public function applicationCancel(Request $request)
//    {
//        $this->validateParam($input=$request->all() , [
//            'oid' => 'required',
//        ]);
//        if(JoinLogic::applicationCancel($input['oid']))
//            return $this->success('已取消申请');
//    }



}