<?php
namespace App\Model\ApiMember;

use App\Exceptions\ApiException;
use App\Model\User;
use Illuminate\Support\Facades\Hash;

class UserModel extends User
{
    public static function login($uname, $pw)
    {
        $user = self::where('username' , $uname)->first();

        if(!$user || !Hash::check($pw , $user->password)){
            throw new ApiException('登录信息错误');
        }
        if($user->getOriginal('status') != 1){
            throw new ApiException('该账号已停用!');
        }
        $data['code'] = encrypt($uname);
        $data['avatar'] = $user->profile->avatar;
        $data['name'] = $user->profile->name;
        $data['uname'] = $uname;
        return $data;
    }
    public static function checkStatus($uname)
    {
        $user = self::where('username' , $uname)->first();
        if($user->getOriginal('status') != 1){
            throw new ApiException('该账号已停用!');
        }
    }
}