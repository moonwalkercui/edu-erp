<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\OrganizationLogic;
use App\Logic\ApiUser\RbacLogic;
use App\Model\Divisions as DivisionsModel;
use App\Model\Resources\DivisionResource;
use App\Service\Api\UserService;
use Illuminate\Http\Request;
use Illuminate\Validation\Rule;
use App\Api\V1\Controllers\Base;

class OrganizationApi extends Base
{

    public function edit(Request $request)
    {
        $this->validateParam($request->all() , [
            'code' => 'required',
            'region.0' => 'required',
            'region.1' => 'required',
            'region.2' => 'required',
            'major' => 'required',
            'target' => 'required',
            'type' => 'required',
            'organization_name' => ['required','min:4','max:50',Rule::unique('organizations')->ignore(UserService::getOrganizationId()),]
//            'organization_name' => config('system.validate.organization'),
        ],[
            'region.0.required' => '未选择省',
            'region.1.required' => '未选择市',
            'region.2.required' => '未选择区',
            'major.required' => '未选择教育内容',
            'target.required' => '未选择针对人群',
            'type.required' => '未选择组织性质',
        ]);

        if ( OrganizationLogic::save($request, [ 'code' => $request->input('code') ] ) )
            return $this->success('已更新');
    }

    // 一个用户只有一个组织
    public function myOrganization()
    {
//        $user = UserService::getUserInfo();
//        return $this->fetchResource(new OrganizationResource(OrganizationModel::find($user->organization_id)));
    }
    // 退出组织
    public function quitOrganization()
    {
        if($organization = OrganizationLogic::quit())
            return $this->success('已退出');
    }
    // 注销组织
    public function deleteOrganization()
    {
        if($organization = OrganizationLogic::delete(UserService::getOrganizationId()))
            return $this->success('已永久注销');
    }
    // 内部组织列表 门店列表
    public function divisions()
    {
        $model = DivisionsModel::orderBy('sort','desc');
        $division_ids = RbacLogic::getViewDivisionIds( UserService::getUser() );
        if($division_ids) $model->whereIn('id',$division_ids);
//        if($request->filled('with') && $request->input('with') == 'classes'){
//            $model->with('classes');
//        }
        return $this->fetchResource(DivisionResource::collection($model->get()));
    }
    public function divisionFind(Request $request)
    {
        $this->validate($request, [
            'id' => 'required',
        ]);
        return $this->fetchResource(new DivisionResource( DivisionsModel::find($request->id) ));
    }
    // 创建内部组织 门店
    public function divisionCreate(Request $request)
    {
        $this->validateParam($request->all() , [
            'name' => 'required|max:50',
        ]);
        if ( OrganizationLogic::saveDivision($request) )
            return $this->success('已创建');
    }
    public function divisionEdit(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
            'name' => 'required|max:50',
            'region_id' => 'required',
        ]);
        if ( OrganizationLogic::saveDivision($request, [ 'id' => $request->input('id') ] ) )
            return $this->success('已更新');
    }
    public function divisionEditCoordinate(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
            'coordinate' => 'required',
        ]);
        if ( OrganizationLogic::saveCoordinate($request->coordinate, [ 'id' => $request->id] ) )
            return $this->success('已更新');
    }
    // 删除门店
    public function divisionDelete(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
        ]);
        if(OrganizationLogic::deleteDivision($request->input('id')))
            return $this->success('已删除');
    }
    // 删除门店
    public function divisionSetDefault(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
        ]);
        if(OrganizationLogic::setDefault($request->input('id')))
            return $this->success('已设置');
    }

}