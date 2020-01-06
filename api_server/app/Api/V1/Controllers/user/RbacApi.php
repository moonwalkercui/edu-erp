<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\RbacLogic;
use App\Model\Permission as PermissionModel;
use App\Model\Role as RoleModel;
use App\Model\User;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class RbacApi extends Base
{
    // 获取组织的所有权限
    public function orgRoles(Request $request)
    {
        $where = [];
        $data = RoleModel::where($where);
        if($request->filled('with') && $request->input('with') == 'permission'){
            $data->withCount('permissions');
        }
        $list = $data->get();
        return $this->fetch($list);
    }
    public function findRole(Request $request)
    {
        $this->validate($request, [
            'id' => 'required',
        ]);
        return $this->fetch(RoleModel::with('permissions')->find($request->input('id')));
    }
    public function roleEdit(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
            'name' => 'required',
            'permissions' => 'required',
            'divisions' => 'required',
        ],['permissions.required' => '未选择权限' ,'divisions.required' => '未选择门店']);

        if ( RbacLogic::roleEdit($request) )
            return $this->success('已更新');
    }
    public function roleCreate(Request $request)
    {
        $this->validateParam($request->all() , [
            'name' => 'required',
            'permissions' => 'required',
            'divisions' => 'required',
        ],['permissions.required' => '未选择权限' ,'divisions.required' => '未选择门店']);

        if ( RbacLogic::roleCreate($request) )
            return $this->success('已创建');
    }
    public function roleDelete(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
        ]);
        if( RbacLogic::roleDelete($request->input('id')) )
            return $this->success('已删除');
    }

    // 获取用户的权限
    public function findUserRoles(Request $request)
    {
        $username = $request->input('username');
        $user = User::where('username',$username)->first();
        return $this->fetch($user->roles);
    }
    // 更新用户的权限
    public function editUserRoles(Request $request)
    {
        $this->validateParam($request->all() , [
            'username' => 'required',
        ]);
        if ( RbacLogic::saveUserRoles($request))
            return $this->success('用户权限已更新');
    }

    public function permissions(Request $request)
    {
        $list = PermissionModel::orderBy('uri')->where('need_check',1)->get();
        return $this->fetch($list);
    }
}