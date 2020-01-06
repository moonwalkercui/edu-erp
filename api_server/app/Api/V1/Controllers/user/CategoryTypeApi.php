<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\CategoryTypesLogic;
use App\Model\CategoryTypes as CategoryTypesModel;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class CategoryTypeApi extends Base
{
    public function getAll()
    {
        $res = CategoryTypesModel::with('properties')->get();
        return $this->fetch($res);
    }
    public function getOne(Request $request)
    {
        $this->validateParam($input=$request->all() , [ 'id' => 'required' ]);
        $product = CategoryTypesModel::find($input['id']);
        return $this->fetch($product);
    }
    public function create(Request $request)
    {
        $this->validateParam($request->all() , [ 'name' => 'required' ]);
        if(CategoryTypesLogic::save($request))
            return $this->success('已创建');
    }
    public function edit(Request $request)
    {
        $this->validateParam($request->all() , [ 'id' => 'required',  'name' => 'required' ]);
        if(CategoryTypesLogic::save($request,['id' => $request->input('id')]) )
            return $this->success('已更新');
    }
    public function delete(Request $request)
    {
        $this->validateParam($request->all() , ['id' => 'required',]);
        if($product = CategoryTypesLogic::delete($request->input('id')))
            return $this->success('已删除');
    }
    public function createProperty(Request $request)
    {
        // id是类型的id
        $this->validateParam($request->all() , [ 'id' => 'required', 'name' => 'required'],['name.required' => '缺少属性名']);
        if(CategoryTypesLogic::createProperty($request)) return $this->success('已添加');
    }
    public function editProperty(Request $request)
    {
        $this->validateParam($request->all() , [ 'id' => 'required', 'name' => 'required'],['name.required' => '缺少属性名']);
        if(CategoryTypesLogic::updateProperty($request)) return $this->success('已更新');
    }
    public function deleteProperty(Request $request)
    {
        $this->validateParam($request->all() , ['id' => 'required']);
        if(CategoryTypesLogic::deleteProperty($request->input('id'))) return $this->success('已删除');
    }
}