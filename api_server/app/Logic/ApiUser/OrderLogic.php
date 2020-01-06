<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\OrderRefund;
use App\Model\Setting;
use App\Service\WeChat\Refund;

class OrderLogic
{

//    public static function delete($id)
//    {
//        return BaseLogic::delete('订单管理', '删除订单' , '\App\Model\ApiUser\OrderModel' , ['id' => $id ] , function($condition){
//        });
//    }
//    // 批量处理申请
//    public static function handleApplication($ids,$status,$remark='')
//    {
//        BaseLogic::checkStatus($status,'order');
//        $res = OrderModel::whereIn('id' , $ids )->update([ 'status' => $status ]);
//        if($res){
//            LogService::userLog('报单审核','处理结果:'.getStatusText($status,'applicant'));
//            return true;
//        }else{
//            throw new ApiException('操作失败');
//        }
//    }
    // 操作退款
    public static function refund($refund_id, $type, $reason = '')
    {
        if($type == 'accept') {
            if(Setting::checkWxPay() == false) throw new ApiException('未开通微信支付 暂不能退款');
            // 走退款接口

            $res = Refund::refund($refund_id);
            if($res === true) {
                return OrderRefund::accept($refund_id);
            } else {
                $msg = isset($res['err_code_des']) ? $res['err_code_des'] : '退款发起失败';
                throw new ApiException($msg);
            }
        } elseif ($type == 'reject') {
            return OrderRefund::reject($refund_id, $reason);
        } else {
            throw new ApiException('type参数错误');
        }
    }
}
