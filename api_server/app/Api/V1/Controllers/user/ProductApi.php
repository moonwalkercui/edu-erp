<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\ProductLogic;
use App\Model\Product as ProductModel;
use App\Model\MemberProduct;
use App\Model\ProductSpecification;
use App\Model\Resources\MemberProductResource;
use App\Model\Resources\ProductResource;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class ProductApi extends Base
{
    public function getAll(Request $request)
    {

//        $model = ProductSpecificationModel::whereHas('product', function ($query) use ($request) {
//            $where = [];
//            if($request->filled('search_name'))
//                array_push($where, ['name' , 'like' , '%'.cleanXss($request->search_name).'%']) ;
//            $query->where($where);
//            if($request->filled('search_cate'))
//                $query->whereRaw('FIND_IN_SET(?,category_ids)', $request->search_cate);
//
//        })->with(['product'])->withCount('courses')->orderBy('id','desc'); //  'product.linkMaterials'
//
//        if($request->filled('page'))
//            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
//        else
//            $res = $model->get();
//
//        return $this->fetchResource(ProductSpecificationResource::collection($res));
        $where = [];
        if($request->filled('export') && $request->export=='excel' ) {

            $model = ProductSpecification::join('products','product_specifications.product_id','=','products.id');

//            if($request->filled('search_name'))
//                array_push($where, ['products.name' , 'like' , '%'.cleanXss($request->search_name).'%']) ;

            return $this->export(
                $model->where($where)->get()->toArray(),
                [
                    'name' => '课程名称',
                    'price' => '价格',
                    'quantity' => '名额',
                    'courses_quantity' => '课时数',
                    'status' => '状态',
                    'start_at' => '报名开始时间',
                    'end_at' => '报名结束时间',
                    'expired_at' => '过期时间',
                    'updated_at' => '更新时间',
                    'slogan' => '广告语',
                    'description' => '说明',
                    'target' => '针对对象',
                    'purpose' => '目的',
                    'attention' => '注意事项',
                ], '课程列表');
        }


        if($request->filled('search_name'))
            array_push($where, ['name' , 'like' , '%'.cleanXss($request->search_name).'%']) ;

        $model = ProductModel::where($where)->with('specs')->withCount('courses','groupbuy')->withCount('members')
            ->orderBy('recommend','desc')->orderBy('sort')->latest('updated_at'); //->withCount('course')

//        if($request->filled('search_cate'))
//            $model->whereRaw('FIND_IN_SET(?,category_ids)', $request->search_cate);

        if($request->filled('page'))
            $res = $model->paginate();
        else
            $res = $model->get();
//        dump($res);die;
        return $this->fetchResource(ProductResource::collection($res));
    }
//    public function getByCategory(Request $request)
//    {
//        $this->validateParam($input=$request->all() , [
//            'cate_id' => 'required',
//        ]);
//        if($request->filled('page'))
//            $res = ProductModel::getByCate($request->cate_id , $request->input('per_page', config('system.number_paginate')));
//        else
//            $res = ProductModel::getByCate($request->cate_id);
//        return $this->fetch($res);
//    }
    public function getOne(Request $request)
    {
        $this->validateParam($input=$request->all() , [
            'id' => 'required',
        ]);
        return $this->fetchResource(new ProductResource(
            ProductModel::with('images','properties','divisions','specs','groupbuy')->find($input['id'])
        ));
    }
    // 课程的购买者列表
    public function members(Request $request)
    {
        $model =  MemberProduct::with('member','product');

        if($request->filled('search_member'))
            $model->where('member_id', $request->search_member);

        return $this->fetchResource(MemberProductResource::collection(
            $model->get()
        ));
    }
    public function create(Request $request)
    {
        $this->validateParam($request->all() , [
            'name' => 'required',
            'cate' => 'required',
            'type' => 'required',
            'division' => 'required',
            'specifications' => 'required',
//            'price' => 'required|numeric',
        ],['specifications.required' => '规格设置错误']);
        if(ProductLogic::save($request))
            return $this->success('已添加');
    }
    public function editGoupbuy(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
            'is_groupbuy' => 'required',
            'number' => 'required|gte:2',
            'start' => 'required|date',
            'end' => 'required|date',
            'groupbuy.*.spec_id' => 'required',
            'groupbuy.*.price' => 'required',
        ],[
            'number.gte' => '成团人数最少为2',
            'start.required' => '开始时间为必填',
            'end.required' => '开始时间为必填',
            'groupbuy.*.spec_id|required' => '团购价格缺少参数',
            'groupbuy.*.price|required' => '团购价格缺少价格参数',
        ]);
        if(ProductLogic::saveGroupbuy( $request , ['id' => intval($request->id)]) )
            return $this->success('已更新');
    }
    public function edit(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
            'name' => 'required',
            'cate' => 'required',
//            'price' => 'required|numeric',
            'division' => 'required',
        ]);
        if(ProductLogic::save( $request , ['id' => intval($request->id)]) )
            return $this->success('已更新');
    }
    public function delete(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
        ]);
        if($product = ProductLogic::delete($request->input('id')))
            return $this->success('已删除');
    }
    public function stop(Request $request)
    {
        $this->validateParam($request->all() , [ 'ids' => 'required' ],[ 'ids.required' => '产品未选']);
        if ( ProductLogic::stop($request) )
            return $this->success('已下架');
    }
    public function open(Request $request)
    {
        $this->validateParam($request->all() , [ 'ids' => 'required' ],[ 'ids.required' => '产品未选']);
        if ( ProductLogic::open($request) )
            return $this->success('已上架');
    }

}