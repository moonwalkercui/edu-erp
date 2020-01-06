<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\DepartmentLogic;
use App\Model\Department as DepartmentModel;
use App\Model\Divisions as DivisionsModel;
use App\Service\Api\UserService;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class DepartmentApi extends Base
{
    public function getAll()
    {
        $res = DepartmentModel::all();
        return $this->fetch(unlimitedLevel($res));
    }
    public function getAllByDivision(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required'
        ]);
        $res = DepartmentModel::where('division_id', intval($request->id))->get();
        return $this->fetch(unlimitedLevel($res));
    }
    public function getAllWithChild(Request $request)
    {
        $where=[];
        if($request->filled('search_division'))
            array_push($where, ['division_id' , '=' , intval($request->search_division)] );
        else{
            array_push($where, ['division_id' , '=' , UserService::getDivisionId() ]);
        }
        $res = DepartmentModel::with('division')->where($where)->get()->toArray();
        return $this->fetch(unlimitedChild($res));
    }
    public function getAllWithDivision()
    {
        $res = DivisionsModel::getDepartmentTree();
        return $this->fetch($res);
    }
    // 新建部门
    public function create(Request $request)
    {
        $this->validateParam($request->all() , [
            'name' => 'required'
        ]);
        if(DepartmentLogic::save($request))
            return $this->success('已创建');
    }
    // 编辑部门
    public function edit(Request $request)
    {
        $this->validateParam($input = $request->all() , [
            'id' => 'required',
            'name' => 'required',
        ]);
        if(DepartmentLogic::save( $request, ['id' => $request->input('id') ] ))
            return $this->success('已更新');
    }
    // 删除部门
    public function delete(Request $request)
    {
        $this->validateParam($input = $request->all() , [
            'id' => 'required'
        ]);
//        var_dump($input['id']);die;
        if(DepartmentLogic::delete($input['id']))
            return $this->success('已删除');
    }
    public function saveUsers(Request $request)
    {
        $this->validateParam($input = $request->all() , [
            'id' => 'required',
            'users' => 'required'
        ],['users.required' => '请选择成员']);

        if(DepartmentLogic::saveUsersDepartment($input['id'],$input['users']))
            return $this->success('已更新');

    }
}