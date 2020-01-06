<?php
namespace App\Service\Api;

use App\Model\Member;

class MemberService
{
    // 判断是否登录了
    public static function isLoggedIn()
    {
        return self::getMember() != null;
    }
    public static function getMember()
    {
        return auth('api_member')->user();
    }
    public static function getMemberId()
    {
        return self::getMember()->member_id;
    }
    public static function getMemberProfileId()
    {
        return self::getMember()->id;
    }
    public static function getOpenId()
    {
        return Member::where('id', self::getMemberId())->value('wx_open_id');
    }
    public static function getMemberMobile()
    {
        return self::getMember()->mobile;
    }
}
