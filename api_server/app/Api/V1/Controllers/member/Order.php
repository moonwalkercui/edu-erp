<?php
namespace App\Api\V1\Controllers\member;

use App\Logic\ApiMember\OrderLogic;
use App\Model\Course as CourseModel;
use App\Model\Order as OrderModel;
use App\Model\Product as ProductModel;
use App\Model\Resources\OrderResource;
use App\Service\WeChat\Payment;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class Order extends Base
{

//    public function confirmForm(Request $request)
//    {
//        $course_id = $request->course;
//        $voucher_id = $request->input('voucher', null);
//        $course = CourseModel::with('division')->where('status',1)->find($course_id);
//        if($course == false) return $this->error('未知课程');
//        $product = ProductModel::find($course->product_id);
//        $specs = $product->specs;
//        return $this->fetch([
//            'name' => $product->name,
//            'address' => $course->division->address,
//            'date' => $course->date,
//            'start_at' => $course->start_at,
//            'end_at' => $course->end_at,
//            'user' => $course->user_name,
//            'specs' => $specs,
//        ]);
//    }
//    public function courseConfirm(Request $request)
//    {
//        $this->validateParam($request->all() , [
//            'course_id' => 'required',
//            'spec_id' => 'required',
//            'money' => 'required',
////            'balance' => 'required',
//        ]);
//        $course_id = $request->course_id;
//        $spec_id = $request->spec_id;
//        $voucher_id = $request->input('voucher_id', null);
////        $balance = $request->input('balance', 0);
//        $money = $request->input('money', 0);
//
////        $res = OrderLogic::make($money, $spec_id, $course_id, $voucher_id, $balance);
//        $res = OrderLogic::make($money, $spec_id, $course_id, $voucher_id);
//        return $this->fetch($res);
//    }
    // 提交
    public function productConfirm(Request $request)
    {
        $this->validateParam($request->all() , [
            'spec_id' => 'required',
            'money' => 'required',
//            'balance' => 'required',
        ]);
        $spec_id = $request->spec_id;
        $voucher_id = $request->input('voucher_id', null);
//        $balance = $request->input('balance', 0);
        $money = $request->input('money', 0);
        $red_packet = $request->input('red_packet', 0); // 使用红包

        $res = OrderLogic::make($money, $spec_id, $red_packet, null, $voucher_id, $request->pay_type);
        return $this->fetch($res);
    }
    // 取消订单
    public function cancelOrder(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
        ]);
        $res = OrderLogic::cancel($request->id);
        return $res ? $this->success('已取消') : $this->error('已取消');
    }
    public function refundOrder(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
        ]);
        $res = OrderLogic::refund($request->id);
        return $res ? $this->success('已申请退款，等待管理员确认') : $this->error('申请退款失败');
    }
    // 从我的订单里支付
    public function payOrder(Request $request)
    {
        $this->validateParam($request->all() , [
            'id' => 'required',
        ]);
        $res = OrderLogic::pay($request->id);
        return $this->fetch($res);
    }

}