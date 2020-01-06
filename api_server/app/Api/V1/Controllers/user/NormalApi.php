<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\NormalLogic;
use App\Model\Advertisement;
use App\Model\Course;
use App\Model\GiftOrder;
use App\Model\Goods;
//use App\Model\Help;
use App\Model\Help;
use App\Model\MemberProfile;
use App\Model\Notification;
use App\Model\Order;
use App\Model\OrderRefund;
use App\Model\Proceeds;
use App\Model\Region;
use App\Model\Resources\NotificationResource;
use App\Model\Resources\ZoneResource;
use App\Model\UserProfile;
use App\Model\Voucher;
use App\Model\Zone;
use App\Service\Api\UserService;
use App\Service\MultiRequestToken;
use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class NormalApi extends Base
{
    public function systemNumber()
    {
        $data['orders'] = Order::count();
        $data['members'] = MemberProfile::count();
        $data['users'] = UserProfile::count();
        $data['courses'] = Course::count();
        $data['goods'] = Goods::count();
        $data['vouchers'] = Voucher::count();
        return $this->fetch($data);
    }
    public function advertisements(Request $request)
    {
        $where = [];
        $model = Advertisement::where($where);
        $data = $model->get();
        return $this->fetch($data);
    }

    public function advertisementSave(Request $request)
    {
        $this->validateParam($request->all(), [
            'title' => 'required',
            'position' => 'required',
        ],[
            'title.required'=>'标题必填',
            'position.required'=>'位置必选',
        ]);
        if ($request->filled('id')) $condition['id'] = $request->id;
        else $condition = [];
        if(NormalLogic::advertisementSave($request, $condition))
            return $this->success('已保存');
    }
    public function advertisementDel(Request $request)
    {
        if(NormalLogic::advertisementDel($request->id))
            return $this->success('已删除');
    }

    public function notifications(Request $request)
    {
        $where = [];
        $model = Notification::where($where)->orderBy('is_top','desc')->orderBy('id','desc');
        if($request->filled('page'))
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $data = $model->get();
        return $this->fetchResource(NotificationResource::collection($data));
    }
    public function notificationSave(Request $request)
    {
        $this->validateParam($request->all(), [
            'title' => 'required',
            'content' => 'required',
        ],[
            'title.required'=>'标题必填',
            'content.required'=>'内容必填',
        ]);
        if ($request->filled('id')) $condition['id'] = $request->id;
        else $condition = [];
        if(NormalLogic::notificationSave($request, $condition))
            return $this->success('已保存');
    }
    public function notificationDel(Request $request)
    {
        if(NormalLogic::notificationDel($request->id))
            return $this->success('已删除');
    }
    // 圈子
    public function zone(Request $request)
    {
        $where = [];
        $model = Zone::where($where)->with('user')->withCount('logs')->orderBy('edit_time','desc');
        if($request->filled('page'))
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $data = $model->get();
        return $this->fetchResource(ZoneResource::collection($data));
    }
    public function zoneSave(Request $request)
    {
        $this->validateParam($request->all(), [
            'content' => 'required',
        ],[
            'content.required'=>'内容必填',
        ]);
        if ($request->filled('id')) $condition['id'] = $request->id;
        else $condition = [];
        if(NormalLogic::zoneSave($request, $condition))
            return $this->success('已保存');
    }
    public function zoneDel(Request $request)
    {
        if(NormalLogic::zoneDel($request->id))
            return $this->success('已删除');
    }
    public function regions()
    {
        return $this->fetch(Region::getList());
    }
    public function helper(Request $request)
    {
        $key = cleanXss($request->key);
        if(!$key) return $this->error('缺少KEY');
        return $this->fetch(Help::where('key', $key)->first());
    }

    // 创建组织
//    public function createOrg(Request $request)
//    {
//        $this->validateParam($request->all() , [
//            'region.0' => 'required',
//            'region.1' => 'required',
//            'region.2' => 'required',
//            'major' => 'required',
//            'target' => 'required',
//            'type' => 'required',
//            'organization_name' => config('system.validate.organization'),
//        ],[
//            'region.0.required' => '未选择省',
//            'region.1.required' => '未选择市',
//            'region.2.required' => '未选择区',
//            'major.required' => '未选择教育内容',
//            'target.required' => '未选择针对人群',
//            'type.required' => '未选择组织性质',
//        ]);
//
//        if ( OrganizationLogic::save($request) )
//            return $this->success('组织已创建');
//    }
//    // 查找组织
//    public function findOrg(Request $request)
//    {
//        $this->validateParam($request->all(), [
//            'code' => 'required',
//        ], ['code.required' => '请输入组织编码'] );
//        $org = Organization::withCount('users')->where('code',$request->input('code'))->first();
//        $data = $org ? $org->only(['organization_name','users_count']) : null;
//        return $this->fetch($data);
//    }
//    // 判断用户是否可以继续创建组织
//    public function canCreateOrg()
//    {
//        if(UserLogic::canKeepCreateOrg()) return $this->success('ok');
//        else return $this->error('您创建的组织数量超过系统限制');
////        OrganizationLogic::checkCanCreate();
//    }
//    // 判断用户是否可以继续加入组织
//    public function canJoinOrg()
//    {
//        if(UserLogic::canKeepJoinOrg()) return $this->success('ok');
//        else return $this->error('您加入的组织数量超过系统限制');
////        OrganizationLogic::checkCanJoin();
//    }
//    // 申请加入到组织
//    public function orgApply(Request $request)
//    {
//        $this->validateParam($input = $request->all() , [
//            'code' => 'required',
//        ],[
//            'code.required' => '缺少组织编码'
//        ]);
//        if(JoinLogic::application( Organization::where('code',$input['code'])->first() ))
//            return $this->success('已发送申请');
//    }
//    public function getAppliedOrg(Request $request)
//    {
//        return $this->fetch(
//            Organization::where('manage_username',UserService::getUserName())
//                ->whereNotIn('status',[1,0])
//                ->select('code','organization_name','created_at','status','reject_reason')
//                ->get()
//        );
//    }
    /**
     * 获取表单提交验证码
     * @Post("/normal/mreqtoken")
     */
    public function makeMultiReqToken()
    {
        return $this->fetch(MultiRequestToken::make());
    }
    // 检查是否有通知 与通知内容
    public function checkNotice(Request $request)
    {
        if($request->filled('from_time')){ // 时间戳
            $from_at = $request->from_time < strtotime("today") ? strtotime("today") : $request->from_time ;
            $from_time = date("Y-m-d H:i:s", $from_at);
            $today = date("Y-m-d", $from_at);
        } else {
            $from_time = Carbon::today();
            $today = date("Y-m-d", time());
        }
        $uid = UserService::getUserId();
//        $from_time = Carbon::today();
        $order_count = Order::where('created_at' , '>' , $from_time)->count();
        $refund_count = OrderRefund::where('created_time' , '>' , $from_time)->where("status", 0)->count();
        $course_count = Course::whereDate('date', $today)->where('user_id', $uid)->count();
        $proceed_count = Proceeds::where('created_at' , '>' , $from_time)->where("status", 0)->count();
        $gift_count = GiftOrder::where('created_at' , '>' , $from_time)->where("stage", 0)->count();

        return $this->fetch([
            [
                'show' => $order_count > 0 ? 1 : 0,
                'type' => 'neworder',
                'title' => "有新订单了",
                'subject' => '新订单数:'. $order_count,
            ], [
                'show' => $refund_count > 0 ? 1 : 0,
                'type' => 'newrefund',
                'title' => "有退款申请需要处理",
                'subject' => '退款申请人次:'. $refund_count,
            ], [
                'show' => $course_count > 0 ? 1 : 0,
                'type' => 'newcourse',
                'title' => "今天有你的课",
                'subject' => '今日课时数:'. $course_count,
            ], [
                'show' => $proceed_count > 0 ? 1 : 0,
                'type' => 'newproceed',
                'title' => "有新的账目需要处理",
                'subject' => '账目处理信息数:'. $proceed_count,
            ], [
                'show' => $gift_count > 0 ? 1 : 0,
                'type' => 'newgiftapply',
                'title' => "有新的礼品申请需要处理",
                'subject' => '礼品申请人次:'. $gift_count,
            ],
        ]);

    }
}