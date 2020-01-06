<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\ProceedsLogic;
use App\Model\Proceeds as ProceedsModel;
use App\Model\MemberProfile;
use App\Model\Resources\ProceedsResource;
use App\Model\UserProfile;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;
use Illuminate\Support\Facades\DB;

class ProceedsApi extends Base
{
    /**
     * 收款列表
     * @Get("/proceeds/table")
     */
    public function getAll(Request $request)
    {
        if ($request->filled('export') && $request->export == 'excel') {
            $model = ProceedsModel::join('member_profile','proceeds.member_id','=','member_profile.member_id')
                ->join('divisions','proceeds.division_id','=','divisions.id')
                ->select(DB::raw('
                    proceeds.sn, proceeds.status, proceeds.pay_type, proceeds.money_receivable, proceeds.paid_at, 
                    proceeds.confirmed_at, proceeds.created_at, proceeds.remark, proceeds.user_name,
                    member_profile.nick_name, member_profile.mobile, 
                    divisions.name as division_name
                '))
                ->get();
            return $this->export(
                $model->toArray(),
                [
                    'sn' => '交易编号',
                    'status' => '状态',
                    'pay_type' => '支付方式',
                    'division_name' => '所属门店',
                    'nick_name' => '学员昵称',
                    'mobile' => '学员手机',
                    'money_receivable' => '应付款',
                    'paid_at' => '支付时间',
                    'confirmed_at' => '认款时间',
                    'created_at' => '记录时间',
                    'user_name' => '认款人',
                    'remark' => '备注',
                ], '收款明细'
            );
        }

        $model = ProceedsModel::with('division','log','member');
        if ($request->filled('search_sn'))
            $model->where('sn',$request->search_sn);
        if ($request->filled('search_mobile'))
            $model->where('member_id', MemberProfile::where('mobile',$request->search_mobile)->value('member_id'));
        if($request->filled('search_user'))
            $model->where('user_id', UserProfile::where('username', $request->search_user)->value('user_id'));
        if ($request->filled('search_division')) {
            $model->where('division_id',$request->search_division);
        }
        if ($request->filled('sort_by')) {
            switch ($request->sort_by) {
                case 'receivable':
                    $model->orderBy('money_receivable', $request->input('sort_type', 'asc'));
                    break;
                case 'member':
                    $model->orderBy('member_name', $request->input('sort_type', 'asc'));
                    break;
                default:
                    break;
            }
        } else {
            $model->orderBy('id', 'desc');
        }
        if ($request->filled('page'))
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $res = $model->get();
        return $this->fetchResource(ProceedsResource::collection($res));
    }
    // 认款
    public function handleConfirm(Request $request)
    {
        $this->validateParam($request->all() , [
            'sn' => 'required',
            'money' => 'required',
            'division' => 'required',
        ]);
        if(ProceedsLogic::confirm($request))
            return $this->success('已认款');
    }
    /**
     * 收款
     * @Post("/proceeds/receive")
     */
    public function receive(Request $request)
    {

        $this->validateParam($request->all() , [
            'sn' => 'required',
            'money' => 'required',
            'name' => 'required',
            'mobile' => 'required',
            'mode' => 'required',
        ],[
            'sn.required' => '缺少编号',
            'money.required' => '缺少金额',
            'name.required' => '缺少交款人姓名',
            'mobile.required' => '缺少交款人手机号',
            'mode.required' => '缺少收款方式'
        ]);
        if(ProceedsLogic::receive($request))
            return $this->success('已收款');
    }
    /**
     * 我的应收款
     * @Get("/proceeds/mine")
     */
//    public function mine(Request $request)
//    {
//        $model = ProceedsModel::whereHas('money', function ($query) {
//            $query->where('user_receiver', UserService::getUserName());
//        })->with('money','member')->orderBy('updated_at', 'desc');
//
//        if ($request->filled('search_sn'))
//            $model->where('sn',$request->search_sn);
//        if ($request->filled('search_member'))
//            $model->where('member_name',$request->search_member);
//        if ($request->filled('sort_by')) {
//            switch ($request->input('sort_by')) {
//                case 'receivable':
//                    $model->orderBy('money_receivable', $request->input('sort_type', 'asc'));
//                    break;
//                case 'member':
//                    $model->orderBy('member_name', $request->input('sort_type', 'asc'));
//                    break;
//                default:
//                    break;
//            }
//        }
//        if ($request->filled('page'))
//            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
//        else
//            $res = $model->get();
//        return $this->fetchResource(ProceedsResource::collection($res));
//    }
    /**
     * 发起收款
     * @Post("/proceeds/create")
     */
//    public function create(Request $request)
//    {
//        $this->validateParam($request->all() , [
//            'members' => 'required',
//            'items' => 'required',
//            'money_receivable' => 'required',
//            'division' => 'required',
//        ],[
//            'members.required'=>'学员须必选',
//            'items.required'=>'未选择项目',
//            'money_receivable.required'=>'缺少应收金额',
//            'division.required'=>'未选择归属门店',
//        ]);
//        if(ProceedsLogic::save($request))
//            return $this->success('已发起收款');
//    }
    /**
     * 收款项目列表
     * @Get("/proceeds/create")
     */
//    public function getItems(Request $request)
//    {
//        $model = ProceedsItemModel::latest();
//        if ($request->filled('search_name'))
//            $model->where('name',$request->search_name);
//        if ($request->filled('active') && $request->active == 'yes')
//            $model->where('status',1);
//        if ($request->filled('sort_by')) {
//            $model->orderBy($request->input('sort_by'), $request->input('sort_type', 'asc'));
//        }
//        if ($request->filled('page'))
//            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
//        else
//            $res = $model->get();
//        return $this->fetchResource(ProceedsItemResource::collection($res));
//    }


    /**
     * 追加款项
     * @Post("/proceeds/receive")
     */
//    public function verify(Request $request)
//    {
//        $this->validateParam($request->all() , [
//            'sn' => 'required',
//            'state' => 'required',
//        ],['sn.required' => '缺少参数', 'state.required' => '缺少审核类型']);
//        if(ProceedsLogic::verify($request))
//            return $this->success('操作成功');
//    }
    /**
     * 创建收款项目
     * @Post("/proceeds/createitem")
     */
//    public function createItem(Request $request)
//    {
//        $this->validateParam($request->all() , [
//            'name' => 'required',
//            'money' => 'required',
//        ],['name.required' => '缺少名称','money.required' => '缺少金额']);
//        if(ProceedsLogic::saveItem($request))
//            return $this->success('已创建');
//    }
    /**
     * 编辑收款项目
     * @Post("/proceeds/edititem")
     */
//    public function editItem(Request $request)
//    {
//        $this->validateParam($request->all() , [
//            'id' => 'required',
//            'name' => 'required',
//            'money' => 'required',
//        ],['name.required' => '缺少名称','money.required' => '缺少金额']);
//        if(ProceedsLogic::saveItem($request, ['id' => $request->id]))
//            return $this->success('已修改');
//    }
    /**
     * 删除收款项目
     * @Post("/proceeds/deleteitem")
     */
//    public function deleteItem(Request $request)
//    {
//        $this->validateParam($input = $request->all() , [ 'id' => 'required' ]);
//        if(ProceedsLogic::deleteItem($input['id']))
//            return $this->success('已删除');
//    }
}