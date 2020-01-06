<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\RbacLogic;
use App\Logic\ApiUser\RoomLogic;
use App\Model\Room as RoomModel;
use App\Model\Resources\RoomResource;
use App\Service\Api\UserService;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class RoomApi extends Base
{
    public function getAll(Request $request)
    {
        $where = [];
        if($request->filled('search_division'))
            array_push($where, ['division_id' , '=' , $request->input('search_division')]) ;

        $model = RoomModel::with('division')->where($where);

        if($request->filled('search_division') == false){
            $internal_ids = RbacLogic::getViewDivisionIds( UserService::getUser() );
            if($internal_ids) $model->whereIn('division_id',$internal_ids);
        }

        if($request->filled('page'))
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $res = $model->get();
        return $this->fetchResource(RoomResource::collection($res));
    }
    public function create(Request $request)
    {
        $this->validateParam($request->all() , [ 'name' => 'required' ]);
        if(RoomLogic::save($request)) return $this->success('已创建');
    }
    public function edit(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'id' => 'required', 'name' => 'required' ]);
        if(RoomLogic::save( $request, ['id' => $request->input('id') ] )) return $this->success('已更新');
    }
    public function delete(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'id' => 'required' ]);
        if(RoomLogic::delete($input['id'])) return $this->success('已删除');
    }
}