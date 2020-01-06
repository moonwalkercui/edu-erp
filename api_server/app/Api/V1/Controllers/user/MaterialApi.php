<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\MaterialLogic;
use App\Logic\ApiUser\RbacLogic;
use App\Model\MaterialLog as MaterialLogModel;
use App\Model\Material as MaterialModel;
use App\Model\Resources\MaterialLogResource;
use App\Model\Resources\MaterialResource;
use App\Service\Api\UserService;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class MaterialApi extends Base
{
    public function getAll(Request $request)
    {
        $where = [];
        if($request->filled('search_name'))
            array_push($where, ['name' , 'like' , '%'.cleanXss($request->search_name).'%']) ;
        if($request->filled('search_division'))
            array_push($where, ['division_id' , '=' , intval($request->search_division)]) ;

        $model = MaterialModel::with(['logs'=>function($query){
            $query->orderBy('id','desc');
        }])->where($where)->orderBy('id','desc');

        if($request->filled('search_division') == false){
            $internal_ids = RbacLogic::getViewDivisionIds( UserService::getUser() );
            if($internal_ids) $model->whereIn('division_id',$internal_ids);
        }

        if($request->filled('page'))
            $res = $model->with('division')->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $res = $model->get();
        return $this->fetchResource(MaterialResource::collection($res));
    }
    public function create(Request $request)
    {
        $this->validateParam($request->all() , [
            'name' => 'required',
            'division' => 'required',
            'quantity' => 'required'
        ],['quantity.required' => '缺少数量']);
        if(MaterialLogic::save($request))
            return $this->success('已创建');
    }
    public function log(Request $request)
    {
        $where = [];
        if($request->filled('search_material'))
            array_push($where, ['material_id' , '=' , $request->input('search_material')]) ;
        if($request->filled('search_user'))
            array_push($where, ['username' , '=' , $request->input('search_user')]) ;
        if($request->filled('search_status'))
            array_push($where, ['status' , '=' , $request->input('search_status')]) ;
        $model = MaterialLogModel::where($where)->orderBy('id','desc');
        if($request->filled('page'))
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $res = $model->get();
        return $this->fetchResource(MaterialLogResource::collection($res));
    }
    public function edit(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'id' => 'required', 'division' => 'required','name' => 'required' ]);
        if(MaterialLogic::save( $request, ['id' => $request->input('id') ] ))
            return $this->success('已更新');
    }
    public function delete(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'id' => 'required' ]);
        if(MaterialLogic::delete($input['id']))
            return $this->success('已删除');
    }
//    物料申请
    public function apply(Request $request)
    {
        $this->validateParam($request->all() ,
            [ 'material' => 'required' ,'quantity' => 'required' ],
            [ 'material.required' => '未选择物料' ,'quantity.required' => '未输入数量' ]
        );
        $id = intval($request->input('material'));
        $quantity = intval($request->input('quantity'));
        $money = intval($request->input('money', 0)); // 申请物料缴纳的费用 单价
        $remark = cleanXss($request->input('remark'));
        if( MaterialLogic::apply($id, $quantity, $money, $remark) )
            return $this->success('已申请');
    }
    public function changeQuantity(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'id' => 'required','number' => 'required|numeric' ],['number.required'=>'缺少数量','number.numeric'=>'数量须是数字']);
        if($input['number'] <= 0) return $this->error('入库数须大于零');
        if(MaterialLogic::changeQuantity($input['id'],$input['number'],isset($input['money']) ? $input['money'] : 0)){
            return $this->success($input['number'] > 0 ? '已入库' : '已出库');
        }
    }
//    物料审核通过
    public function handleApprove(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'ids' => 'required' ]);
        if($res = MaterialLogic::handleApplication($input['ids'], 1 ,isset($input['remark']) ? $input['remark'] : ''))
            return $this->success("已审核通过 $res 条记录");
        else return $this->error("未完成任何审核");
    }
//    物料审核驳回
    public function handleReject(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'ids' => 'required' ]);
        if($res = MaterialLogic::handleApplication($input['ids'], -1 ,isset($input['remark']) ? $input['remark'] : ''))
            return $this->success("已驳回 $res 条记录");
        else return $this->error("未完成任何审核");
    }

}