<?php
namespace App\Service\Api;

use App\Exceptions\ApiException;
use App\Model\ApiUser\UserModel;
use App\Model\Organization;
use App\Model\UserProfile;

class UserService
{
    static $user;
    static $profile;
    static $org;

    public static function getUser()
    {
        return self::$user ?: (self::$user = auth('api')->user());
        //        $user = app('Dingo\ApiUser\Auth\Auth')->user(); // 获取api里通过权限的用户
//        return auth('api')->user();
    }
    // 默认是当前登录的user，如果有uid则是查询
    public static function getUserInfo()
    {
        $user = self::getUser();
        $user->profile = self::$profile ?: (self::$profile = UserProfile::where('username',$user->username)->first());
        return $user;
    }
    public static function getUserId()
    {
        return self::getUserInfo()->id;
    }
    public static function getUserName()
    {
        return self::getUserInfo()->username;
    }
    public static function getUserProfileName()
    {
        return self::getUserInfo()->profile->name;
    }
    // 获取所在默认门店id
    public static function getDivisionId()
    {
        $user = self::getUserInfo();
        return $user->profile ? $user->profile->division_id : null;
    }
//    // 检查一个账号的profile是否存在，如果不存在则生成profile记录 // todo 增加org_id条件
//    public static function checkProfileExistOrCreate($username)
//    {
//        $created_user = UserModel::where('username',$username)->first();
//        if(UserProfile::where('user_id',$created_user->id)->first() == null){
//            if(UserProfile::insert(['user_id'=>$created_user->id]) == false)
//                Log::error('会员注册生成profile失败', ['username' => $created_user->username]);
//        }
//    }
    // 检查用户是否存在
    public static function checkExist($username)
    {
        $user = self::getUserInfo();
        $check_user = UserModel::where([
            'username' => $username ,
        ])->first();
        if($check_user) return $check_user;
        else throw new ApiException('账号不存在');
    }
    // 检查orgid是否与会员的一致
    public static function checkOrgId($org_id)
    {
        if($org_id == self::getOrganizationId()) return true;
        else throw new ApiException('无权请求');
    }
    // 是否为组织管理员
    public static function isManager()
    {
        return self::getUserInfo()->profile->getOriginal('is_manager') == 1;
    }
}
