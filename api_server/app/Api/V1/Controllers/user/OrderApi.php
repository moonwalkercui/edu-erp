<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\OrderLogic;
use App\Model\Order as OrderModel;
use App\Model\Order;
use App\Model\OrderItem;
use App\Model\OrderRefund;
use App\Model\Resources\OrderRefundResource;
use App\Model\Resources\OrderResource;
use App\Model\UserProfile;
use App\Service\Api\UserService;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class OrderApi extends Base
{
    // 获取所有
    public function getAll(Request $request)
    {
        return $this->getData($request);
    }
    // 获取我的
//    public function getMine(Request $request)
//    {
//        return $this->getData($request,true);
//    }
    public function getData(Request $request,$is_mine = false)
    {

        if($request->filled('export') && $request->export=='excel' ) {
            $model = Order::join('order_items','orders.id', '=','order_items.order_id','right')
                ->join('member_profile','orders.member_id', '=','member_profile.member_id')
                ->leftJoin('user_profile','orders.salesman_uname', '=','user_profile.username')
                ->select('orders.sn','orders.pay_sn','orders.type','orders.pay_type','orders.cut_money','orders.status',
                    'order_items.price','order_items.remark','order_items.deal_price','order_items.courses_quantity','order_items.item_name',
                    'member_profile.nick_name','member_profile.mobile','member_profile.name','user_profile.name as user_name');

            return $this->export(
                $model->get()->toArray(),
                [
                    'sn' => '订单号',
                    'pay_sn' => '支付交易号',
                    'type' => '订单类型',
                    'item_name' => '名称',
                    'status' => '状态',
                    'pay_type' => '支付方式',
                    'nick_name' => '学员昵称',
                    'name' => '学员姓名',
                    'mobile' => '学员手机号',
                    'price' => '订单金额',
                    'cut_money' => '满减金额',
                    'deal_price' => '满减后支付金额',
                    'courses_quantity' => '课时数',
                    'remark' => '备注',
                    'user_name' => '业务员',
                ], '订单列表');
        }

        $where = [];
        if($is_mine) $where[] = ['username','=',UserService::getUserName()];
        if($request->filled('search_sn'))
            array_push($where, ['sn' , '=' , cleanXss($request->search_sn)]) ;
        if($request->filled('search_paysn'))
            array_push($where, ['pay_sn' , '=' , cleanXss($request->search_paysn)]) ;
        if($request->filled('search_user'))
            array_push($where, ['username' , '=' , cleanXss($request->search_user)]) ;
        if($request->filled('search_member'))
            array_push($where, ['member_id','=',cleanXss($request->search_member)]) ;
        if($request->filled('search_status'))
            array_push($where, ['status' , '=' , intval($request->search_status)]) ;

        $model = OrderModel::with('items.item','member')->where($where)->orderBy('id','desc');

        if($request->filled('search_product')){
            $order_ids = OrderItem::where('item_id',intval($request->search_product) )->pluck('order_id');
            $model->whereIn('id',array_unique($order_ids->toArray()));
        }
        if($request->filled('page'))
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $data = $model->get();
        return $this->fetchResource(OrderResource::collection($data));
//        return $this->fetch($data);
    }
    public function salesman(Request $request)
    {
        $where = [];
        if($request->filled('search_user'))
            array_push($where, ['o.salesman_uname' , '=' , cleanXss($request->search_user)]);
//        if($request->filled('search_status')){
//            array_push($where, ['o.status' , '=' , intval($request->search_status)]);
//        } else {
//            array_push($where, ['o.status' , '=' , 1]);
//        }
        $date = [];
        if( $request->filled('search_date') && count($request->search_date) == 2 ) {
            $date = $request->search_date;
            array_push($where, ['o.created_at' , '>' , $date[0] . ' 00:00:00']);
            array_push($where, ['o.created_at' , '<' , $date[1] . ' 23:59:59']);
        }
        $model = UserProfile::query()
            ->from('user_profile as u')
            ->leftJoin('orders as o', 'u.username', '=', 'o.salesman_uname')
            ->groupBy('u.username')
            ->selectRaw('u.username,u.name,sum(o.total_price) as total,count(o.id) as count')
            ->where([
                ['u.status','=',1],
                ['o.status','=',1]
            ])->where($where);

        if($request->filled('export') && $request->export=='excel' ) {
            return $this->export(
                $model->get()->toArray(),
                [
                    'name' => '业务员',
                    'username' => '账号',
                    'count' => '订单数量',
                    'total' => '订单金额',
                ], '业务员订单统计' . $date[0]. '~' . $date[1]);
        }

        if ($request->filled('sort_by')) {
            switch ($request->sort_by) {
                case '订单数量':
                    $model->orderBy('count', $request->input('sort_type', 'asc'));
                    break;
                case '订单金额':
                    $model->orderBy('total', $request->input('sort_type', 'asc'));
                    break;
                default:
                    break;
            }
        } else {
            $model->orderBy('total', 'desc');
        }
        return $this->fetch($model->get());
    }
    public function listBySalesman(Request $request)
    {
        $where = [];
        if($request->filled('search_user') == false) return $this->error('缺少业务员参数');
        if($request->filled('search_date') == false || count($request->search_date) != 2 ) return $this->error('查询时间参数错误');
        array_push($where, ['o.salesman_uname' , '=' , cleanXss($request->search_user)]);
        array_push($where, ['o.created_at' , '>' , $request->search_date[0] . ' 00:00:00']);
        array_push($where, ['o.created_at' , '<' , $request->search_date[1] . ' 23:59:59']);

        $model = Order::query()
            ->from('orders as o')
            ->leftJoin('member_profile as m', 'o.member_id', '=', 'm.member_id')
            ->selectRaw('o.sn,m.nick_name,o.total_price,o.created_at')
            ->where([
                ['o.status','=',1]
            ])->where($where);

        return $this->fetch($model->get());
    }

//    public function create(Request $request)
//    {
////        $this->validateMultiReqToken($request);
//        $this->validateParam($request->all(), [
//            'member' => 'required',
//            'items' => 'required',
//            'money_receivable' => 'required',
//            'money_received' => 'required',
//            'payment_mode' => 'required',
//        ],[
//            'member.required'=>'学员须必选',
//            'items.required'=>'未选择项目',
//            'money_receivable.required'=>'缺少应收金额',
//            'money_received.required'=>'缺少实收金额',
//            'payment_mode.required'=>'缺少付款方式',
//        ]);
//        if(OrderLogic::save($request))
//            return $this->success('已提交成单审核');
//    }
    public function getOne(Request $request)
    {
        $this->validateParam($input=$request->all() , [
            'sn' => 'required',
        ]);
        $product = OrderModel::where('sn',$input['sn'])->first();
        return $this->fetch($product);
    }
//    public function edit(Request $request)
//    {
//        $this->validateParam($request->all() , [
//            'sn' => 'required',
//            'mobile' => config('system.validate.member_mobile'),
//            'price' => 'required|numeric',
//        ]);
//        if(OrderLogic::edit($request))
//            return $this->success('已更新');
//    }
//    public function delete(Request $request)
//    {
//        $this->validateParam($request->all() , [
//            'id' => 'required',
//        ]);
//        if($product = OrderLogic::delete($request->input('id')))
//            return $this->success('已删除');
//    }
//    public function handleApprove(Request $request)
//    {
//        $this->validateParam($input = $request->all() , [
//            'ids' => 'required',
//        ]);
//        if(OrderLogic::handleApplication($input['ids'], 1 ,isset($input['remark']) ? $input['remark'] : ''))
//            return $this->success('已审核');
//    }
//    public function handleReject(Request $request)
//    {
//        $this->validateParam($input = $request->all() , [
//            'ids' => 'required',
//        ]);
//        if(OrderLogic::handleApplication($input['ids'], -1 ,isset($input['remark']) ? $input['remark'] : ''))
//            return $this->success('已驳回');
//    }
    public function refunds(Request $request)
    {
        $where = [];
        if($request->filled('search_sn'))
            array_push($where, ['refund_sn' , '=' , cleanXss($request->search_sn)]) ;

        if($request->filled('search_order_sn')){
            $order_id = OrderModel::where('sn', cleanXss($request->search_order_sn))->value('id');
            array_push($where, ['order_id' , '=' , $order_id]) ;
        }

        $model = OrderRefund::with('order')->where($where)->orderBy('id','desc');

        if($request->filled('page'))
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $data = $model->get();
        return $this->fetchResource(OrderRefundResource::collection($data));
    }
    public function refund(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
            'type' => 'required', // accept or reject
        ]);
        if(OrderLogic::refund($request->id, $request->type, $request->input('reason',null)))
            return $this->success('操作完成');
    }

}