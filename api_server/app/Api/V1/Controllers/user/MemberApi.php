<?php
namespace App\Api\V1\Controllers\user;

use App\Exceptions\ApiException;
use App\Logic\ApiMember\MemberLogic;
use App\Logic\ApiUser\MemberProfileLogic;
use App\Logic\ApiUser\ProductLogic;
use App\Model\Classes as ClassesModel;
use App\Model\MemberProfile as MemberProfileModel;
use App\Model\MemberProduct;
use App\Model\Resources\ClassResource;
use App\Model\Resources\MemberProductResource;
use App\Model\Resources\MemberResource;
use App\Service\Api\UserService;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;
use Illuminate\Support\Facades\DB;

class MemberApi extends Base
{
    public function getAll(Request $request)
    {

        if ($request->filled('export') && $request->export == 'excel') {
            $model = MemberProfileModel::join('member_product', 'member_profile.member_id', '=', 'member_product.member_id','left')
                ->select('member_product.member_id', 'member_profile.name',
                    'member_profile.name', 'member_profile.nick_name', 'member_profile.mobile',
                    'member_profile.gender', 'member_profile.birthday', 'member_profile.city', 'member_profile.points',
                    DB::raw('sum(member_product.total_quantity) as total_quantity, sum(member_product.remaining_quantity) as remaining_quantity')
                )->groupBy('member_product.member_id')
            ;
            return $this->export(
                $model->get()->toArray(),
                [
                    'nick_name' => '昵称',
                    'name' => '姓名',
                    'mobile' => '手机号',
                    'gender' => '性别',
                    'birthday' => '生日',
                    'city' => '城市',
                    'points' => '小星星数',
                    'total_quantity' => '总购买课时数',
                    'remaining_quantity' => '剩余课时数',
                ], '已购买学员列表');
        }

        $where = [];
        if($request->filled('search_mobile'))
            array_push($where, ['mobile' , '=' , cleanXss($request->search_mobile)]) ;
        if($request->filled('search_name'))
            array_push($where, ['name' , '=' , cleanXss($request->search_name)]) ;
        if($request->filled('search_nickname'))
            array_push($where, ['nick_name' , 'like' , cleanXss($request->search_nickname)]) ;
        if($request->filled('search_status') && $request->input('search_status') != 'all')
            array_push($where, ['status' , '=' , intval($request->search_status)]) ;
        if($request->filled('only_student') && $request->input('only_student') == 1)
            array_push($where, ['is_student' , '=' , 1]) ;

        $model = MemberProfileModel::where($where)->withCount(['deal as deal_course' => function($query){
            $query->select(DB::raw('sum(total_quantity)'));
        },'deal as remaining_course' => function($query){
            $query->select(DB::raw('sum(remaining_quantity)'));
        }])->withCount('signs')->with('salesman')->orderBy('id','desc');

        if($request->filled('page'))
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $data = $model->get();
//        return $this->fetch($data);
        return $this->fetchResource(MemberResource::collection($data));
    }

    public function ranking(Request $request)
    {
        $where = [];
        if($request->filled('search_mobile')){
            array_push($where, ['member_id' , '=' ,
                MemberProfileModel::where('mobile', cleanXss($request->search_mobile))->value('member_id')
            ]) ;
        }
        if($request->filled('search_name')){
            array_push($where, ['member_id' , '=' ,
                MemberProfileModel::where('name', cleanXss($request->search_name))->value('member_id')
            ]) ;
        }
        if($request->filled('search_nickname')){
            array_push($where, ['member_id' , '=' ,
                MemberProfileModel::where('nick_name', cleanXss($request->search_nickname))->value('member_id')
            ]) ;
        }
        if($request->filled('search_product') && $request->search_product){
            array_push($where, ['product_id', '=', $request->search_product]);
        }
        $model = MemberProduct::with('member','product')->where($where);

        if ($request->filled('sort_by')) {
            switch ($request->sort_by) {
                case 'total':
                    $model->orderBy('total_quantity', $request->input('sort_type', 'asc'));
                    break;
                case 'remaining':
                    $model->orderBy('remaining_quantity', $request->input('sort_type', 'asc'));
                    break;
                default:
                    break;
            }
        } else {
            $model->orderBy('id','desc');
        }

        if($request->filled('page'))
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $data = $model->get();
        return $this->fetchResource(MemberProductResource::collection($data));
    }
    public function getByClass(Request $request)
    {
        $this->validateParam($request->all() , [ 'class' => 'required' ] , ['class.required' => '缺少班级参数']);
        return $this->fetchResource(
            new ClassResource(ClassesModel::find($request->input('class')))
        );
    }
    // 获取一个客户信息
    public function getOne(Request $request)
    {
        $this->validateParam($input=$request->all() , [
            'id' => 'required',
        ]);
        return $this->fetchResource(new MemberResource(
            MemberProfileModel::where(['id'=>$request->id])->first()
        ));
    }
//    // 获取一个用户的所有客户
//    public function myMembers(Request $request)
//    {
//        $where[] = ['owner','=',UserService::getUserName()];
//        if($request->filled('search_name'))
//            array_push($where, ['name' , '=' , $request->input('search_name')]) ;
//
//        $model = MemberPoolModel::where($where)->orderBy('id','desc');
//        if($request->filled('keyword')){
//            $keyword = $request->keyword;
//            $model->where(function ($query) use ($keyword)  {
//                $query->where('mobile', 'like', $keyword . '%')->orWhere('name', 'like', '%' . $keyword . '%');
//            });
//        }
//        if($request->filled('page'))
//            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
//        else
//            $data = $model->get();
//        return $this->fetchResource(MemberResource::collection($data));
//    }

    // 支持批量
    public function changeSalesman(Request $request) {
        $this->validateParam($request->all() , [
            'salesman' => 'required',
            'customers.*' => 'required',
        ],['salesman.required'=>'未选择业务员']);
        if ( MemberProfileLogic::changeChangeSalesman($request->salesman, $request->customers) )
            return $this->success('更改成功');
    }
    // 创建客户以及会员客户关系
    public function create(Request $request)
    {
        $this->validateParam($request->all() , [
            'member_name' => config('system.validate.member_name'),
            'mobile' => config('system.validate.member_mobile'),
        ]);
        if ( MemberProfileLogic::save($request) )
            return $this->success('已创建');
    }
    public function edit(Request $request)
    {
        $this->validateParam($request->all() , [
            'member_name' => config('system.validate.member_name'),
            'mobile' => config('system.validate.member_mobile'),
        ]);
        if ( MemberProfileLogic::save($request, [ 'id' => $request->input('id') ] ) )
            return $this->success('已更新');
    }
    // 修改单个字段
    public function editColumn(Request $request)
    {
        $this->validateParam($request->all() , [
            'mobile' => config('system.validate.member_mobile')
        ]);
        if ( MemberProfileLogic::save($request, [ 'mobile' => $request->input('mobile') , 'owner' => UserService::getUserName() ] ) )
            return $this->success('已更新');
    }
    public function delete(Request $request)
    {
        $this->validateParam($request->all() , [
            'mobile' => 'required',
        ]);
        if ( MemberProfileLogic::delete($request->input('mobile')))
            return $this->success('已删除');
    }
    public function productQuantityEdit(Request $request)
    {
        $this->validateParam($request->all() , [
            'member' => 'required',
            'product' => 'required',
            'total' => 'required|numeric|min:0',
            'remaining' => 'required|numeric|min:0',
        ]);
        if ( ProductLogic::editMemberProduct($request->all()))
            return $this->success('已提交');
    }
    public function productQuantityAdd(Request $request)
    {
        $this->validateParam($request->all() , [
            'member' => 'required',
            'product' => 'required',
            'total' => 'required|numeric|min:0',
            'remaining' => 'required|numeric|min:0',
        ]);
        if ( ProductLogic::addMemberProduct($request->all()))
            return $this->success('已提交');
    }
}