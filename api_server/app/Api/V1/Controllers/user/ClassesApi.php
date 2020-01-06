<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\ClassesLogic;
use App\Logic\ApiUser\RbacLogic;
use App\Model\Classes as ClassesModel;
use App\Model\Resources\ClassResource;
use App\Service\Api\UserService;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class ClassesApi extends Base
{
    // 获取所有
    public function getAll(Request $request)
    {
//        $res = Classes::with('members')->get()->toArray();
//        dd($res);
        $where = [];
        if($request->filled('search_name'))
            array_push($where, ['name' , 'like' , '%'.cleanXss($request->search_name).'%']) ;
        if($request->filled('search_sn'))
            array_push($where, ['sn' , '=' , cleanXss($request->search_sn)]);
        if($request->filled('search_division'))
            array_push($where, ['division_id' , '=' , intval($request->search_division)]);

        $model = ClassesModel::where($where)->orderBy('id','desc');
        if($request->filled('search_division') == false){
            $internal_ids = RbacLogic::getViewDivisionIds( UserService::getUser() );
            if($internal_ids) $model->whereIn('division_id',$internal_ids);
        }

        if($request->filled('page'))
            $res = $model->with('division')->withCount([
                'members' => function($query){
                    $query->where('status',1);
                }
            ])->withCount('courses')->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $res = $model->get();
//        return $this->fetch($res);
        return $this->fetchResource(ClassResource::collection($res));
    }
    public function create(Request $request)
    {
        $this->validateParam($request->all() , [
            'name' => 'required'
        ]);
        if(ClassesLogic::save($request))
            return $this->success('已创建');
    }
    public function edit(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'id' => 'required', 'name' => 'required' ]);
        if(ClassesLogic::save( $request, ['id' => $request->input('id') ] ))
            return $this->success('已更新');
    }
    public function saveMembers(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'id' => 'required' ]);
        $members = $request->input('members');
        ClassesLogic::saveMembers($input['id'],$members);
        return $this->success('已更新');
    }
    public function delete(Request $request)
    {
        $this->validateParam($input = $request->all() , [ 'id' => 'required' ]);
        if(ClassesLogic::delete($input['id']))
            return $this->success('已删除');
    }
    // 把班级内学员同步到签到表
//    public function synchCourse(Request $request)
//    {
//        $this->validateParam($input = $request->all() , [ 'id' => 'required' ]);
//        if( CourseSignLogic::synchClassSigns( intval($input['id']) ) )
//            return $this->success('已更新');
//    }
}