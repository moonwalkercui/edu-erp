<?php
namespace App\Api\V1\Controllers\user;

use App\Api\V1\Controllers\Base;
use App\Logic\ApiUser\GiftLogic;
use App\Model\GiftCategory as GiftCategoryModel;
use App\Model\Gift as GiftModel;
use App\Model\GiftOrder as GiftOrderModel;
use App\Model\Resources\GiftOrderResource;
use App\Model\Resources\GiftResource;
use Illuminate\Http\Request;

class GiftApi extends Base
{
    // 列表
    public function getAll(Request $request)
    {
        $where = [];
        if($request->filled('search_name'))
            array_push($where, ['name' , 'like' , '%'.cleanXss($request->search_name).'%']) ;
        if($request->filled('search_category'))
            array_push($where, ['category_id' , '=' , intval($request->search_category)]) ;

        $model = GiftModel::with('category')->where($where)->orderBy('id','desc');

        if($request->filled('page'))
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $res = $model->get();
        return $this->fetchResource(GiftResource::collection($res));
    }
    // 保存gift
    public function save(Request $request)
    {
        $this->validateParam($request->all() , [
            'name' => 'required',
            'category' => 'required',
            'points' => 'required',
            'image' => 'required',
            'storage' => 'required',
        ],[
            'name.required' => '缺少名称',
            'category.required' => '分类未选',
            'point.required' => '缺少小星星数',
            'image.required' => '缺少图片',
            'storage.required' => '缺少库存数',
        ]);
        if(GiftLogic::save($request, $request->id ? ['id' => $request->id] : []))
            return $this->success($request->id ? '已更新' : '已创建');
    }
    public function delete(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'id' => 'required' ]);
        if(GiftLogic::delete($input['id']))
            return $this->success('已删除');
    }
    // 分类
    public function categories()
    {
        return $this->fetch(GiftCategoryModel::orderBy('sort','desc')->select('id','title as name')->get());
    }
    // 保存分类
    public function saveCategory(Request $request)
    {
        $this->validateParam($request->all() , [
            'name' => 'required',
        ],[
            'name.required' => '缺少名称',
        ]);
        if(GiftLogic::saveCategory($request, $request->id ? ['id' => $request->id] : []))
            return $this->success($request->id ? '已更新' : '已创建');
    }
    // 申请
    public function orders(Request $request)
    {
        $where = [];
        if($request->filled('search_member'))
            array_push($where, ['member_id' , '=' , $request->search_member]) ;
        if($request->filled('search_gift'))
            array_push($where, ['gift_id' , '=' , $request->search_gift]) ;

        $model = GiftOrderModel::with('gift','member')->where($where)->orderBy('id','desc');

        if($request->filled('page'))
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $res = $model->get();
        return $this->fetchResource(GiftOrderResource::collection($res));
    }
    // 通过
    public function approve(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
        ],[
            'id.required' => '缺少参数',
        ]);
        if(GiftLogic::approve($request->id))
            return $this->success('已通过');
    }
    // 驳回
    public function reject(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
        ],[
            'id.required' => '缺少参数',
        ]);
        if(GiftLogic::reject($request->id))
            return $this->success('已驳回');
    }
}