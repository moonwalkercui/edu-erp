<?php
namespace App\Api\V1\Controllers\user;

use App\Exceptions\ApiException;
use App\Logic\ApiUser\UserLogic;
use App\Model\UserProfile as UserProfileModel;
use App\Model\Resources\UserProfileResource;
use App\Model\UserProfile;
use App\Service\Api\UserService;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class UserApi extends Base
{
    public function getAll(Request $request)
    {
        if ($request->filled('export') && $request->export == 'excel') {
            $model = UserProfileModel::get();
            return $this->export(
                $model->toArray(),
                [
                    'username' => '账号',
                    'name' => '姓名',
                    'email' => '邮箱',
                    'phone' => '电话',
                    'birthday' => '生日',
                    'gender' => '性别',
                    'qq' => 'QQ',
                    'married' => '婚姻',
                    'education' => '学历',
                    'created_at' => '创建时间',
                    'intro' => '简介',
                ], '已成交学员列表');
        }

        $where = [];
        if($request->filled('search_user'))
            array_push($where, ['username' , '=' , cleanXss($request->search_user)]) ;
//        if($request->filled('search_division'))
//            array_push($where, ['division_id' , '=' , intval($request->search_division)]) ;

        if($request->filled('search_status')){
            array_push($where, ['status' , '=' , intval($request->search_status)]) ;
        }
        if($request->filled('department')) { // 只通过部门id获取部门下的
            array_push($where, ['department_id' , '=' , intval($request->department)]) ;
        }

        $model = UserProfileModel::where($where)->orderBy('sort','desc')->orderBy('created_at','desc');

        if($request->filled('keyword')){
            $keyword = cleanXss($request->keyword);
            $model->where(function ($query) use ($keyword)  {
                $query->where('username', 'like', $keyword . '%')->orWhere('name', 'like', '%' . $keyword . '%');
            });
        }

//        if($request->filled('search_division') == false){
//            $internal_ids = RbacLogic::getViewDivisionIds( UserService::getUser() );
//            if($internal_ids) $model->whereIn('division_id',$internal_ids);
//        }
        if($request->filled('search_division')) {
            $model->where('division_id',$request->search_division);
        }

        if ( $request->filled('search_department') ) { // 通过店铺id搜索部门与子部门在内的所有的
            $model->whereRaw('FIND_IN_SET(?,department_ids)', [$request->search_department]);
        }

        if($request->filled('page'))
            $res = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $res = $model->get();

//        var_dump($res);die;
        return $this->fetchResource(UserProfileResource::collection($res));
    }
//    public function getOne(Request $request)
//    {
//        $user = UserOrganization::where('username',$request->input('uname'))->first();
//        if($user == null) throw new ApiException('未查询到账号');
//        $user->profile;
//        return $this->fetch($user);
//    }

    public function getProfile(Request $request)
    {
        $data = UserProfileModel::with(['division','department'])
            ->where('username',$request->input('uname'))
            ->select([
                'address', 'avatar', 'birthday', 'city', 'created_at', 'department_id', 'department_ids',
                'education', 'email', 'gender', 'graduation_date', 'graduation_school', 'idcard_number',
                'view_divisions', 'intro','sort', 'married', 'name', 'native_place', 'phone', 'qq', 'status', 'updated_at', 'username'
            ])
            ->first();
        return $this->fetch($data);
    }
    // 修改
    public function edit(Request $request)
    {
        $this->validateParam($request->all() , [
            'uname' => 'required',
            'name' => 'required',
        ],[
            'uname.required' => '未知会员',
            'name.required' => '未设置姓名',
        ]);

        if(UserLogic::update($request)) return $this->success('已更新');
    }
    public function editMe(Request $request)
    {
        $rule['uname'] = 'required';
        $msg['uname.required'] = '未知会员';
        if ($request->type != 'single') {
            $rule['name'] = 'required';
            $msg['name.required'] = '未设置姓名';
        }
        $this->validateParam($request->all() , $rule, $msg);
        if(UserService::getUserName() != $request->input('uname')) throw new ApiException('权限不足');
        if(UserLogic::update($request)) return $this->success('已更新');
    }
    // 切换组织
    public function switchover(Request $request)
    {
        $this->validateParam($request->all() , [
            'code' => 'required',
        ],[
            'code.required' => '缺少组织编号',
        ]);
        if (UserLogic::switchover($request->input('code'))) {
            return $this->success('已切换组织');
        } else {
            return $this->error('切换失败');
        }
    }
//    // 加入部门
//    public function setDepartment(Request $request)
//    {
//        $this->validateParam($input = $request->all() , [
//            'username' => 'required',
//            'department' => 'required', // 加密后的
//        ]);
//        if(DepartmentLogic::setUserDepartment($input['username'],$input['department']))
//            return $this->success('操作成功');
//    }

    // 添加账号
    public function create(Request $request)
    {
        $this->validateParam($request->all() , [
            'username' => 'required|min:4|max:25|unique:users|alpha_num',
            'password' => 'required',
            'name' => 'required',
        ]);
        if ( UserLogic::create($request->username, $request->password, $request->name) )
            return $this->success('已创建');
    }
    // 修改密码
    public function changePw(Request $request)
    {
        $this->validateParam($request->all() , [
            'username' => 'required',
            'password' => 'required',
        ]);
        if ( UserLogic::changePw($request->username, $request->password) )
            return $this->success('已修改');
    }
}