<?php
namespace App\Api\V1\Controllers\member;

use App\Model\Category as CategoryModel;
use App\Model\CategoryTypes;
use App\Model\ProductDivision;
use App\Model\ProductProperty;
use App\Model\Resources\ProductResource;
use App\Model\Product as ProductModel;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class Product extends Base
{
    /**
     * 列表
     * @Get("/mproduct/list")
     */
    public function getAll(Request $request)
    {
        $where = [];
        if($request->filled('show_by')) {
            switch ($request->show_by) {
                case "rec":  $where['recommend'] = 1; break;
                default: break;
            }
        }
        if($request->filled('search_name'))
            array_push($where, ['name' , 'like' , '%'.cleanXss($request->search_name).'%']) ;

        $model = ProductModel::where($where)->with('users','specs')->where(function($query){
            $query->whereNull('start_at')->orWhere(function ($q) {
                    $today =  date('Y-m-d',time());
                    $q->where('start_at', '<=', $today)->where('end_at', '>=', $today);
                });
        }); // ->select(config('system.fields.productList')); //
        $model->orderBy('recommend','desc')->orderBy('sort','desc')->latest('updated_at');

        if($request->filled('properties')){ // 根据属性查询默认用于查询短期课，所以长期条件为false
            $props = explode(',',$request->properties);
            $ids = ProductProperty::whereIn('property_id',$props)->pluck('product_id')->toArray();
            $model->whereIn('id', array_unique($ids));
        }

        if($request->filled('division')){
            $props = explode(',',$request->division);
            $ids = ProductDivision::whereIn('division_id',$props)->pluck('product_id')->toArray();
            $model->whereIn('id', array_unique($ids));
        }

        if($request->filled('search_cate'))
            $model->whereRaw('FIND_IN_SET(?,category_ids)', intval($request->search_cate));
        if($request->filled('page'))
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $res = $model->get();

//        $model = ProductSpecification::whereHas('product', function ($query) use ($request) {
//
//            $where = [];
//            if($request->filled('show_by')) {
//                switch ($request->show_by) {
//                    case "rec":  $where['recommend'] = 1; break;
//                    default: break;
//                }
//            }
//            if($request->filled('search_name'))
//                array_push($where, ['name' , 'like' , '%'.cleanXss($request->search_name).'%']) ;
//
//            $query->where($where);
//
//            if($request->filled('properties')){
//                $props = explode(',',$request->properties);
//                $ids = ProductProperty::whereIn('property_id',$props)->pluck('product_id')->toArray();
//                $query->whereIn('id', array_unique($ids));
//            }
//            if($request->filled('division')){
//                $props = explode(',',$request->division);
//                $ids = ProductDivision::whereIn('division_id',$props)->pluck('product_id')->toArray();
//                $query->whereIn('id', array_unique($ids));
//            }
//
//            if($request->filled('search_cate'))
//                $query->whereRaw('FIND_IN_SET(?,category_ids)', intval($request->search_cate));
//
//        })->with(['product'])->orderBy('id','desc'); // 'product.linkMaterials'

        return $this->fetch($res);
    }

    /**
     * 课程详情
     *
     * @Get("/mproduct/detail")
     * @Parameters({
     *      @Parameter("id", description="课程id", required=true),
     * })
     */
    public function detail(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'id' => 'required']);
//        return $this->fetchResource(new ProductResource(
//            ProductModel::with(['specs','courses'=> function ($query) {
//                $query->limit(4);
//            },'users','images'])->where('id', $request->id)->first()
//        ));
        return $this->fetchResource(new ProductResource(
            ProductModel::with('specs','images','users')
                ->withCount('deal')
                ->where('id', $request->id)
                ->first()
        ));
    }

    public function propertyTree()
    {
//        $data = \App\Model\Category::whereHas('types')->with('types','types.properties')->get();
        $data = CategoryTypes::whereHas('categories')->with('properties')->get();
        return $this->fetch($data);
    }
    public function getPropertyRelation()
    {
        $data = ProductProperty::all();
        return $this->fetch($data);
    }
    public function categories()
    {
        $res = CategoryModel::where('parent_id',0)->select('id','name as title')->get();
        return $this->fetch($res);
    }
//    public function makeAppointment(Request $request)
//    {
//        $where = [
//            'item_type' => 'Product',
//            'item_id' => $request->product,
//        ];
//        $order_item = OrderItem::where($where)->orderBy('id','desc')->orderBy('id','desc')->first();
//        if($order_item == false) return $this->error('您还未购买该课程');
//        if($order_item->courses_quantity <= 0) return $this->error('您的购买的课时数已用完');
//
//        $res = OrderItem::where($where)->decrement('courses_quantity');
//        return $res ? $this->success('已成功预定') : $this->error('您的购买的课时数已用完');
//    }
}