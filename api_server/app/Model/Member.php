<?php

namespace App\Model;

use Illuminate\Support\Facades\Hash;

class Member extends BaseModel
{
    protected $table = 'members';
    protected $fillable = ['wx_open_id', 'wx_union_id', 'type'];

    public function profile()
    {
        return $this->hasOne('App\Model\MemberProfile');
    }
    public function getStatusAttribute($value)
    {
        return getStatusText($value, 'switch');
    }

    // 通过微信的id获取member模型 暂时用小程序openid
    public static function findOrCreateByWxId($open_id, $ref_id = null, $salesman_uname = null, $by_field = 'wx_open_id')
    {
        $member = self::where($by_field, $open_id)->first();
        if (!$member) {
            $member = self::create([$by_field => $open_id]);
            MemberProfile::create([
                'member_id' => $member->id,
                'referrer_id' => $member->id == $ref_id ? null : $ref_id, // 不能自己推荐自己
                'salesman_uname' => $salesman_uname,
            ]);
        }
        return $member;
    }
    // {"openId":"",
    // "nickName":"\u738b\u7acb\u7ea2-\u8d44\u6d77\u7f51\u7edc\u96c6\u56e2",
    // "gender":2,"language":"zh_CN","city":"Jinan","province":"Shandong","country":"China",
    // "avatarUrl":"",
    // "watermark":{"timestamp":1533792855,"appid":""}}
    public static function updateByWxUserInfo($user_info)
    {
        $member = self::where('miniapp_openid', $user_info['openId'])->first();
        $member->avatar = $user_info['avatarUrl'];
        $member->wx_union_id = isset($user_info['unionId']) ? $user_info['unionId'] : null;
        $member->nickname = $user_info['nickName'];
        $member->gender = $user_info['gender'];
        $member->city = $user_info['city'];
        $member->province = $user_info['province'];
        $member->save();
        return $member;
    }

    public static function checkPassword($mobile, $password)
    {
        $member = self::where('mobile', $mobile)->first();
        if ($member == false) return false;
        if (Hash::check($password, $member->password) == false) return false;
        return $member;
    }

}
