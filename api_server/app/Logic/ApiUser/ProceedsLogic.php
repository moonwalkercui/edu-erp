<?php

namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\Order;
use App\Model\Proceeds as ProceedsModel;
use App\Model\Proceeds;
use App\Model\ProceedsLog;
use App\Service\Api\LogService;
use App\Service\Api\UserService;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class ProceedsLogic
{
    // 认款
    public static function confirm(Request $request)
    {

        $sn = cleanXss($request->sn);
        $money = floatval($request->money);
        $division = intval($request->division);
        $proceeds = Proceeds::where('sn', $sn)->first();
        if ($money != $proceeds->money_receivable)
            throw new ApiException('金额有误');

        $proceeds->division_id = $division;
        $proceeds->status = 2;
        $proceeds->confirmed_at = now();
        $proceeds->user_id = UserService::getUserId();
        $proceeds->user_name = UserService::getUserProfileName();
        $res = $proceeds->save();

        if ($res) {
            LogService::userLog('收款管理', '线下收款:' . $sn);
            return true;
        } else {
            throw new ApiException('认款出错');
        }
    }

    // 追加收款
    public static function receive(Request $request)
    {
        $sn = $request->sn;
        $data = [
            'money' => floatval($request->money),
            'name' => cleanXss($request->name),
            'mobile' => cleanXss($request->mobile),
            'mode' => cleanXss($request->mode),
            'remark' => cleanXss($request->input('remark', null)),
        ];
        if ($data['money'] <= 0)
            throw new ApiException('收款金额有误');

        $proceeds = ProceedsModel::where('sn', $sn)->first();
        if($proceeds->getOriginal('status') == 1) throw new ApiException('请勿重复收款');

        DB::beginTransaction();
        try {
            $proceeds->paid_at = now();
            $proceeds->status = 1;
            $proceeds->save();
            ProceedsLog::insert([
                'proceeds_id' => $proceeds->id,
                'name' => $data['name'],
                'mobile' => $data['mobile'],
                'money' => $data['money'],
                'payment_mode' => $data['mode'],
                'remark' => $data['remark'],
                'created_at' => now(),
                'user_id' => UserService::getUserId(),
                'user_name' => UserService::getUserProfileName(),
            ]);
            Order::handelPay($sn);

        } catch (\Exception $e) {
            DB::rollBack();
            throw new ApiException($e->getMessage());
        }
        DB::commit();
        LogService::userLog('收款管理', '线下收款:' . $sn);
        return true;
    }
//    public static function save(Request $request)
//    {
//        $member_mobiles     = cleanXss($request->input('members'));
//        $item_ids           = cleanXss($request->input('items'));
//        $division_id    = intval($request->input('division'));
//        $to_pay_at          = Carbon::parse($request->input('date_time', null));
//        $money_receivable   = floatval($request->money_receivable);
//        $remark             = cleanXss($request->remark);
//
//        if (empty($member_mobiles)) throw new ApiException('未选择学员');
//        if (empty($item_ids)) throw new ApiException('未选择收费项目');
//
//        $members = MemberPoolModel::whereIn('mobile', $member_mobiles)->pluck('name','mobile');
//        $items = ProceedsItemModel::whereIn('id', $item_ids)->get();
//
//        $now = now();
//        $create_item_data = [];
//        foreach ($items as $i) {
//            $create_item_data[] = [
//                'proceeds_item_id' => $i->id,
//                'proceeds_item_name' => $i->name,
//                'proceeds_item_money' => $i->money,
//                'created_at' => $now,
//            ];
//        }
//
//        $org_id = UserService::getOrganizationId();
//        DB::beginTransaction();
//
//        try{
//            $proceeds_rec = ProceedsReceivable::create([
//                'division_id' => $division_id,
//                'money_receivable' => $money_receivable * count($members),
//                'to_pay_at' => $to_pay_at,
//                'remark' => $remark,
//            ]);
//            $proceeds_rec->items()->createMany($create_item_data);
//            $proceeds = [];
//            foreach ($members as $mobile => $name) {
//                $proceeds[] = [
//                    'division_id' => $division_id,
//                    'sn' => makeSn(),
//                    'member_mobile' => $mobile,
//                    'member_name' => $name,
//                    'target_type' => 'ProceedsReceivableU',
//                    'target_id' => $proceeds_rec->id,
//                    'money_receivable' => $money_receivable,
//                    'remark' => $remark,
//                    'created_at' => $now,
//                    'updated_at' => $now,
//                ];
//            }
//            ProceedsModel::insert($proceeds);
//            DB::commit();
//        } catch (\Exception $e) {
//            DB::rollBack();
////            throw new ApiException('提交收款出错'); // todo 正式环境下需要开启
//            throw new ApiException($e->getMessage());
//        }
//        LogService::userLog('财务管理','发起收款:' . $money_receivable * count($members) . '元');
//        return true;
//    }

    // 认款审核
//    public static function verify(Request $request)
//    {
//        $sn = $request->sn;
//        $id = $request->input('id', null);
//        $state = $request->state;
//        $type = $request->input('type', null);
//        switch ($state) {
//            case 'pass':
//                $status = 1;
//                LogService::userLog('财务管理','确认收款:'.$sn);
//                break;
//            case 'reject':
//                $status = -1;
//                LogService::userLog('财务管理','驳回收款:'.$sn);
//                break;
//            default:
//                throw new ApiException('未知错误');
//                break;
//        }
//        if(ProceedsModel::handleVerify($sn, $id, $status, $type)) return true;
//        else throw new ApiException('操作失败');
//    }
//    public static function saveItem(Request $request , $condition = [])
//    {
//        $data['name'] = cleanXss($request->input('name'));
//        $data['remark'] = cleanXss($request->input('remark'));
//        $data['money'] = floatval($request->input('money',0));
//        $data['status'] = getStatusValue($request->input('status','正常'),'switch');
//        $title = '修改收款项目';
//        if(empty($condition)){
//            $title = '添加收款项目';
//        }
//        return BaseLogic::save('收款管理', $title.':'.$data['name'] , '\App\Model\ApiUser\ProceedsItemModel' , $data , $condition );
//    }
//    public static function deleteItem($id)
//    {
//        $item = ProceedsItemModel::find($id);
//        return BaseLogic::delete('收款管理','删除收款项目:'. $item->name  ,'\App\Model\ApiUser\ProceedsItemModel' ,['id' => $id ]);
//    }
}
