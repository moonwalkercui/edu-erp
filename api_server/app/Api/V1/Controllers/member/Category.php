<?php
namespace App\Api\V1\Controllers\member;

use App\Model\Category as CategoryModel;
use App\Model\Resources\CategoryResource;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class Category extends Base
{
    /**
     * 课程一级分类
     */
    public function getAll()
    {
        return $this->fetchResource(CategoryResource::collection(
            CategoryModel::where('parent_id', 0)->get()
        ));
    }
    /**
     * 课程二级分类 带商品
     */
    public function getChildAndProduct(Request $request)
    {
        $this->validateParam($request->all() , [ 'cate_id' => 'required' ],[ 'cate_id.required' => '缺少参数' ]);
        $data = CategoryModel::with('products')->where('parent_id', $request->cate_id)->get();
        return $this->fetchResource(CategoryResource::collection($data));
    }
}