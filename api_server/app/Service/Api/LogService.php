<?php
namespace App\Service\Api;

use App\Model\UserLog;
use App\Model\MemberLog;
use Carbon\Carbon;

class LogService
{
    public static function userLog($type,$remark,$username = '')
    {
        if(!$username) {
            $user_info =  UserService::getUserInfo();
            $username = $user_info->username;
        }
        UserLog::insert([
            'type' => $type,
            'remark' => $remark,
            'created_at' => Carbon::now(),
            'username' => $username
        ]);
    }
    public static function memberLog($type,$remark)
    {
        MemberLog::insert([
            'type' => $type,
            'member_id' => MemberService::getMemberId(),
            'remark' => $remark,
            'created_at' => Carbon::now(),
        ]);
    }
}
