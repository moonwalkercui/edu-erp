<?php
namespace App\Logic\ApiMember;

use App\Model\Member;
use App\Model\MemberProduct;
use App\Model\MemberProfile;
use App\Service\Api\MemberService;

class MemberLogic
{
    public static function save($request)
    {
//        if($request->filled('mobile')) {
////            if (MemberService::getMemberMobile() == false) { // 手机号不可以修改？
//                $data['mobile'] = $request->input('mobile');
////            }
//        }

        // 性别生日身高职业
        if($request->filled('height')) $data['height'] = intval($request->input('height'));
        if($request->filled('gender')) $data['gender'] = is_numeric($request->input('gender')) ? $request->input('gender') : getStatusValue($request->input('gender'),'gender');
        if($request->filled('nick_name')) $data['nick_name'] = cleanXss($request->input('nick_name'));
        if($request->filled('name')) $data['name'] = cleanXss($request->input('name'));
        if($request->filled('email')) $data['email'] = cleanXss($request->input('email'));
        if($request->filled('industry')) $data['industry'] = getStatusValue($request->input('industry'),'industry');
        if($request->filled('source')) $data['source'] = getStatusValue($request->input('source'),'source');
        if($request->filled('birthday')) $data['birthday'] = cleanXss($request->input('birthday'));
        if($request->filled('city')) $data['city'] = cleanXss($request->input('city'));
        if($request->filled('province')) $data['province'] = cleanXss($request->input('province'));
        if($request->filled('avatar')) $data['avatar'] = cleanXss($request->input('avatar'));
        if($request->filled('occupation')) $data['occupation'] = getStatusValue($request->input('occupation'),'occupation');
        $title = '修改学员';
        return BaseLogic::save('学员管理', $title , '\App\Model\MemberProfile' , $data, ['member_id' => MemberService::getMemberId()] );
    }
    public static function bindMobile($profile_id, $mobile)
    {
        return MemberProfile::where('id', $profile_id)->update(['mobile' => $mobile]);
    }
    public static function topUp($money) // money 单位元
    {
//        $money = floatval($money);
//        $res = false;
//        if($res == false) throw new ApiException('充值记录添加失败');
//        return true;
    }
    public static function handleBindMobile($mobile)
    {
        $exist_profile = MemberProfile::where('mobile', $mobile)->first();
        $token = ""; // 是否有新token
        if($exist_profile) {
            // 已经注册过会员了，需要采用原会员数据，并返回新token和profile
            $openid = MemberService::getOpenId();
            Member::where('wx_open_id', $openid)->update(['wx_open_id' => $openid . '_2']);
            Member::where('id', $exist_profile->member_id)->update(['wx_open_id' => $openid]);
            $profile = MemberProfile::makeProfileRes($exist_profile);
            $token = MemberProfile::makeToken($exist_profile);
            return compact('token','profile');

        } else {
            // 该手机号没有注册过，直接更新即可。
            if(self::bindMobile(MemberService::getMemberProfileId(), $mobile))
                return compact('token');
        }
        return null;
    }

//    public static function getOneBadge($badge_id, $member_id)
//    {
//        $map = [
//            'badge_id' => $badge_id,
//            'member_id' => $member_id
//        ];
//        if($exist = DB::table('member_badge')->where($map)->first())
//            throw new ApiException('不能重复获得');
//        return DB::table('member_badge')->insert(array_merge($map,[
//            'got_time' => now()
//        ]));
//    }

}