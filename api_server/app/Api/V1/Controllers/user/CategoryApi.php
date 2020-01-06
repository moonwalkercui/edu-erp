<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\CategoryLogic;
use App\Model\Category as CategoryModel;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class CategoryApi extends Base
{
    public function getAll(Request $request)
    {
        $res = CategoryModel::with('types')->get();
        return $this->fetch(unlimitedChild($res));
    }
    public function getOne(Request $request)
    {
        $this->validateParam($input=$request->all() , [
            'id' => 'required',
        ]);
        $product = CategoryModel::find($input['id']);
        return $this->fetch($product);
    }
    public function create(Request $request)
    {
        $this->validateParam($request->all() , [
            'name' => 'required',
        ],['name.required'=>'缺少名称']);
        if(CategoryLogic::save($request))
            return $this->success('已创建');
    }
    public function edit(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
            'name' => 'required',
        ],[
            'name.required'=>'缺少名称'
        ]);
        if(CategoryLogic::save( $request, ['id' => $request->input('id')] ))
            return $this->success('已更新');
    }
    public function delete(Request $request)
    {
        $this->validateParam($request->all() , [ 'id' => 'required' ]);
        if($product = CategoryLogic::delete($request->input('id')))
            return $this->success('已删除');
    }
    public function getProperties(Request $request)
    {
        $this->validateParam($request->all() , [ 'ids' => 'required' ]);
        $ids = $request->ids;
        if(end($ids) == 0) return $this->fetch([]);
        else return $this->fetch( CategoryLogic::getProperties(end($ids)) );
    }

}