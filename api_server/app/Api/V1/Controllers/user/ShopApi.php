<?php
namespace App\Api\V1\Controllers\user;

use App\Model\GoodsCategory as GoodsCategoryModel;
use App\Model\Goods as GoodsModel;
use App\Model\Resources\GoodsResource;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class ShopApi extends Base
{
    // 获取所有
    public function goods(Request $request)
    {
        $where = [];
        if($request->filled('search_name'))
            array_push($where, ['name' , 'like' , '%'.$request->search_name.'%']) ;

        $model = GoodsModel::with('category')->where($where)->orderBy('updated_at','desc');

        if($request->filled('page')) {
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        }else{
            $res = $model->get();
        }
        return $this->fetchResource(GoodsResource::collection($res));
    }
    public function findGoods(Request $request)
    {
        $res = GoodsModel::find($request->id);
        return $this->fetch($res);
    }
    public function saveGoods(Request $request)
    {
        if($request->filled('id')) {
            $res = GoodsModel::where('id', $request->id)->update([
                'image' => cleanImagePrefix($request->image),
                'category_id' => intval($request->cate),
                'name' => cleanXss($request->name),
                'price' => floatval($request->price),
                'sort' => intval($request->sort),
                'status' => getStatusValue($request->status,'product'),
                'storage' => intval($request->storage),
                'updated_at' => now(),
            ]);
        } else {
            $res = GoodsModel::insert([
                'image' => cleanImagePrefix($request->image),
                'category_id' => intval($request->cate),
                'name' => cleanXss($request->name),
                'price' => floatval($request->price),
                'sort' => intval($request->sort),
                'status' => getStatusValue($request->status,'product'),
                'storage' => intval($request->storage),
                'created_at' => now(),
                'updated_at' => now(),
            ]);
        }
        return $res ? $this->success('已保存') : $this->error('保存出错');
    }
    public function category()
    {
        $res = GoodsCategoryModel::orderBy('sort','desc')->get();
        return $this->fetch($res);
    }
    public function saveCategory(Request $request)
    {
        if($request->filled('id')) {
            $res = GoodsCategoryModel::where('id', $request->id)->update([
                'name' => $request->name,
                'sort' => intval($request->sort),
            ]);
        } else {
            $res = GoodsCategoryModel::insert([
                'name' => $request->name,
                'sort' => intval($request->sort),
            ]);
        }
        return $res ? $this->success('已保存') : $this->error('保存出错');
    }
    public function delGoods(Request $request)
    {
        if($request->filled('id')) {
            $res = GoodsModel::where('id', $request->id)->delete();
            return $res ? $this->success('已删除') : $this->error('删除出错');
        } else {
            return $this->error('缺少参数');
        }
    }
    public function delCategory(Request $request)
    {
        if($request->filled('id')) {
            $res = GoodsCategoryModel::where('id', $request->id)->delete();
            return $res ? $this->success('已删除') : $this->error('删除出错');
        } else {
            return $this->error('缺少参数');
        }

    }

}