<?php
namespace App\Api\V1\Controllers\member;

use App\Logic\ApiMember\MemberLogic;
use App\Logic\ApiMember\VoucherLogic;
use App\Logic\RedPacket;
use App\Model\ApiMember\CourseSignModel;
use App\Model\Order as OrderModel;
use App\Model\MemberProduct;
use App\Model\MemberProfile;
use App\Model\MemberVoucher;
use App\Model\RedPacketLog;
use App\Model\Setting;
use App\Model\TempMsgFormId;
use App\Model\TopupStage;
use App\Model\Voucher;
use App\Service\Api\MemberService;
use App\Service\WeChat\WxUtils;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class Mcenter extends Base
{
    public function index(Request $request)
    {
        $member = MemberService::getMember();
        $data['mobile'] = $member->mobile;
        $data['points'] = $member->points;
        $data['order_count'] = $member->order()->count();
        $data['red_packet'] = $member->red_packet;

        $data['voucher_count'] = MemberVoucher::join('vouchers','member_voucher.voucher_id','=','vouchers.id')->where([
            'member_voucher.member_id' => $member->member_id,
            'member_voucher.be_used' => 0,
        ])->where('vouchers.invalid_at','>', now())->sum('vouchers.price');
        $data['course_count'] = MemberProduct::where('member_id', $member->member_id)->sum('remaining_quantity');
        $data['sign_count'] = $member->signs()->count();
//        $data['badges'] = $member->badges()->limit(3)->orderBy('got_time','desc')->get();
//        $data['badge_count'] = $member->badges()->count();
        return $this->fetch($data);
    }
    public function myOrders(Request $request)
    {
        $where = [];
        if ($request->filled('stage')) {
            $where['status'] = getStatusValue($request->stage, 'order');
        }
//        '本月', '近三月', '近半月', '近一年'
        $model = OrderModel::with('items','refunds')->where('member_id', MemberService::getMemberId())->whereIn('type',[1,2])->where($where)->orderBy('updated_at','desc');
        if($request->filled('date_type')) {
            $between = makeTimeBetweenArray($request->date_type);
            if(!empty($between))
                $model->whereBetween('created_at', $between);
        }
        if($request->filled('page'))
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $data = $model->get();
        return $this->fetch($data);
    }
    public function redPacketLog(Request $request)
    {
        $where = [];
        if ($request->filled('status')) {
            $where['handle_status'] = $request->status;
        }
        $model = RedPacketLog::where('member_id', MemberService::getMemberId())
            ->where('created_at','>',now()->subMonths(6))
            ->where($where)
            ->orderBy('id','desc');
        if($request->filled('page'))
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $data = $model->get();
        return $this->fetch($data);

    }
//    public function redPacketFriends()
//    {
//        $friend_ids = RedPacketLog::where('member_id', MemberService::getMemberId())->where('created_at', '>', now()->subMonth(6))
//            ->pluck('friend_id')->toArray();
//        if(empty($friend_ids)) return $this->fetch([]);
//        $data = MemberProfile::whereIn("member_id", array_unique($friend_ids))->limit(100)->get();
//        return $this->fetch($data);
//    }
    // 给推荐人发红包啊
    public function makeRedPacketByShare(Request $request)
    {
        if($request->refid) {
            if($request->refid == MemberService::getMemberId()) return $this->error("不能为自己助力");
            $money = RedPacket::income($request->refid, MemberService::getMemberId(), 'share');
        } else {
            return $this->error("领取失败 未获取到用户信息");
        }

        return $this->fetch([
            'money' => $money,
            'user' => MemberProfile::where("member_id", $request->refid)->select("avatar","nick_name")->first(),
            'friends' => RedPacketLog::getHelpFriends($request->refid)
        ]);
    }
    public function myCoursesQuantity(Request $request)
    {
        $where = [];
        $model = MemberProduct::with('product')->where('member_id', MemberService::getMemberId())->where($where);
        if($request->filled('page'))
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $data = $model->get();

        return $this->fetch($data);
    }
    public function mySigns(Request $request)
    {
        $where = [];

        $model = CourseSignModel::with('course','course.division','course.homework')->with(['product' => function($query){
            $query->select('id','name');
        }])->where('member_id', MemberService::getMemberId())->where($where);
//        if($request->filled('date_type')) {
//            $between = makeTimeBetweenArray($request->date_type);
//            if(!empty($between))
//                $model->whereBetween('course.date', $between);
//        }
        if($request->filled('page'))
            $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        else
            $data = $model->get();

        return $this->fetch($data);
    }
    public function myProfile()
    {
        return $this->fetch(MemberService::getMember());
    }
    public function editProfile(Request $request)
    {
        if( MemberLogic::save($request) )
            return $this->success('已更新');
    }
    public function myVouchers(Request $request)
    {
        $member = MemberService::getMember();

        if($request->filled('catch_price')) {
            $model = $member->vouchers()->where('catch_price', '<=' , $request->catch_price);
        } else {
            $model = $member->vouchers();
        }

        if($request->filled('used')) {
            $model->where('be_used', $request->used);
        }

        $data = $model->orderBy('be_used')->orderBy('invalid_at')->orderBy('got_time','desc')->get()->toArray();
        foreach ($data as &$v) {
            $v['is_expired'] = time() > strtotime($v['invalid_at'] ) ? 1 : 0;
            $v['is_used'] = $v['pivot']['be_used'];
        }
        return $this->fetch($data);
    }
    public function getVoucher(Request $request)
    {

        $this->validateParam($request->all() , ['id' => 'required' ]);
        VoucherLogic::getOne($request->id);
        return $this->success('已获得');
    }
    public function useVoucher(Request $request)
    {
        $this->validateParam($request->all() , ['id' => 'required' ]);
        VoucherLogic::useOne($request->id);
        return $this->success('成功使用');
    }
//    public function topUpStage(Request $request)
//    {
//        $data = TopupStage::get();
//        return $this->fetch($data);
//    }
    public function shareQr(Request $request)
    {
        $attach = [];
        if($request->filled('product')) {
            $attach['product_id'] = $request->product;
        }
        $wx = new WxUtils();
        $data = [
          'qrcode' => $wx->shareQrcode(MemberService::getMemberId(), $attach),
          'info' => '快让好友来扫码助力领红包~',
        ];
        return $this->fetch($data);
    }
    // 收集form_id
    public function collectFromIds(Request $request)
    {
        if($request->filled('form_ids') && is_array($request->form_ids) && count($request->form_ids) > 0) {
            foreach ($request->form_ids as $id) {
                TempMsgFormId::createItem(API_PREFIX, MemberService::getMemberId(), $id);
            }
        }

    }
    // 绘制二维码海报
    public function drawQrPoster(Request $request)
    {

        $product = \App\Model\Product::where('id', $request->product_id)->first();

        $org_info = Setting::getSetting();

        $attach = [];
        if($request->filled('product_id')) {
            $attach['product_id'] = $request->product_id;
        }
        $wx = new WxUtils();
        $qrcode = $wx->shareQrcode(MemberService::getMemberId(), $attach);

        $config = [
            'image' => [
                [
                    'url' => $org_info->org_logo,
                    'stream' => 0,
                    'left' => 15,
                    'top' => -660,
                    'right' => 0,
                    'bottom' => 0,
                    'width' => 70,
                    'height' => 70,
                    'opacity' => 100
                ],
                [
                    'url' => $product->image,
                    'stream' => 0,
                    'left' => 15,
                    'top' => -215,
                    'right' => 0,
                    'bottom' => 0,
                    'width' => 670,
                    'height' => 420,
                    'opacity' => 100
                ],
                [
                    'url' => $qrcode,
                    'stream' => 0,
                    'left' => 505,
                    'top' => -20,
                    'right' => 0,
                    'bottom' => 0,
                    'width' => 180,
                    'height' => 180,
                    'opacity' => 100
                ],
            ],

            'text' => [
                [
                    'text'=>$org_info->org_name,
                    'left'=>110,
                    'top'=>67,
                    'fontSize'=>22,
                    'fontColor'=>'40,40,40',
                    'angle'=>0,
                ],
                [
                    'text'=> mb_substr($product->name, 0, 15) . (mb_strlen($product->name) > 15 ? "..." : ""),
                    'left'=>20,
                    'top'=>580,
                    'fontSize'=>22,
                    'fontColor'=>'40,40,40',
                    'angle'=>0,
                ],
                [
                    'text'=> mb_substr($product->slogan, 0, 20) . (mb_strlen($product->slogan) > 20 ? "..." : ""),
                    'left'=>20,
                    'top'=>615,
                    'fontSize'=>16,
                    'fontColor'=>'110,110,110',
                    'angle'=>0,
                ],
                [
                    'text'=> "￥". $product->specs[0]->price .  ($product->red_packet_max > 0? ' 红包可抵￥' . $product->red_packet_max : '' ),
                    'left'=>20,
                    'top'=>690,
                    'fontSize'=>28,
                    'fontColor'=>'204,51,51',
                    'angle'=>0,
                ],
                [
                    'text'=> "右上角转发或好友扫右侧码都能领红包呦~",
                    'left'=>20,
                    'top'=>720,
                    'fontSize'=>16,
                    'fontColor'=>'110,110,110',
                    'angle'=>0,
                ],
            ],
            'background' => 'poster/bg.gif' // 背景图 700 * 800
        ];
//        $filename = 'bg/' . time() . '.jpg';
        //echo createPoster($config,$filename);
        echo createPoster($config);
    }
    public function topUp(Request $request)
    {
        $this->validateParam($request->all() , ['money' => 'required' ]);
        if( MemberLogic::topUp($request->money) )
            return $this->success('充值成功');
    }

    public function collect(Request $request)
    {
        $res = MemberProfile::storeCollection(MemberService::getMemberId(), $request->input('id'),$request->input('type','division'));
        if ($res === true) return $this->success('已收藏');
        elseif ($res === false) return $this->error('收藏失败');
        else return $this->error('已收藏过');
    }
    public function removeCollection(Request $request)
    {
        $res = MemberProfile::removeCollection(MemberService::getMemberId(), $request->input('id'),$request->input('type','division'));
        if ($res === true) return $this->success('已取消');
        else return $this->error('取消失败');
    }
    public function getVoucherByCode(Request $request)
    {
        $this->validateParam($request->all() , [
            'code' => 'required',
        ]);

        $id = Voucher::decodeId($request->code);
        $member_id = MemberService::getMemberId();

        if(MemberVoucher::where([
            'member_id' => $member_id,
            'voucher_id' => $id,
        ])->first())
            return $this->error('不能重复领取');

        $data = [
            'member_id' => $member_id,
            'voucher_id' => $id,
            'be_used' => 0,
            'got_time' => now(),
        ];
        $res = MemberVoucher::insert($data);
        return $res == false ? $this->error('领取失败') : $this->success('领取成功');
    }
}