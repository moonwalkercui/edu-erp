<?php
namespace app\api\Model;

use app\common\model\User;

class UserModel extends User
{
//    // 注册
//    static function register($user_phone, $password, $nickname = '', $refcode = '')
//    {
//        if (!$password) {
//            exception('密码设置错误');
//        }
//        // 验证手机号
//        if (self::where(compact('user_phone'))->find()) {
//            exception('该手机号已存在');
//        }
//        $id = self::insertGetId([
//            'user_phone' => $user_phone,
//            'username' => $nickname ?: $user_phone,
//            'password' => self::cryptPw($password),
//            'reg_time' => time(),
//            'referrer_id' => $refcode ? ($referrer_uid = self::refcode2Id($refcode)) : null
//        ]);
//        MemberMoney::insert([ 'uid' => $id ]);
//        MemberSpace::insert([ 'uid' => $id ]);
//
//        return JwtService::enToken([
//            'id' => $id,
//            'name' => $nickname ?: $user_phone,
//        ]);
//    }
//
//    static function loginByMob($user_phone)
//    {
//        $member = self::where(compact('user_phone'))->find();
//        static::checkUnlock($member);
//        if (!$member) {
//            exception('请先注册');
//        }
//        $member->last_login_time = time();
//        $member->last_login_ip = request()->ip();
//        $member->save();
//        return [ $member['id'], JwtService::enToken([
//            'id' => $member['id'],
//            'name' => $member['username'],
//        ])];
//    }
//
//    static function loginByMobAndPw($user_phone, $password)
//    {
//        $member = self::where(compact('user_phone'))->find();
//        static::checkUnlock($member);
//        if (!$member) {
//            exception('请先注册');
//        }
//        if (self::verifyPw($password, $member['password']) == false) {
//            exception('登录信息错误');
//        }
//        $member->last_login_time = time();
//        $member->last_login_ip = request()->ip();
//        $member->save();
//
//        return [ $member['id'], JwtService::enToken([
//            'id' => $member['id'],
//            'name' => $member['username'],
//        ])];
//    }
//    //add by gby 获取我的会员信息
//    static function getInfo($uid)
//    {
//        $info = self::where(array('id' => $uid))->find();
//        return $info;
//    }
//    //add by gby 修改会员名称
//    static function changeUsername($mid, $gender)
//    {
//        $updata['username'] = $gender;
//        return self::where(array('id' => $mid))->update($updata);
//    }
//    //add by gby 绑定与解绑手机号
//    static function changePhone($mid, $user_phone)
//    {
//        $updata['user_phone'] = $user_phone;
//        $updata['is_bindphone'] = $user_phone ? 1 : 0;
//        return self::where(array('id' => $mid))->update($updata);
//    }
//
//    function changePwd($mid, $user_phone, $password)
//    {
//        $update['password'] = password_hash($password, PASSWORD_DEFAULT);
//        return self::where(array('id' => $mid, 'user_phone' => $user_phone))->update($update);
//    }
//
//    static function changeWx($mid, $wx)
//    {
//        $updata['wx'] = $wx;
//        $updata['is_bindwx'] = $wx ? 1 : 0;
//        return self::where(array('id' => $mid))->update($updata);
//    }
//
//    static function bindStudent($mid)
//    {
//        $updata['s_student'] = 1;
//        return self::where(array('id' => $mid))->update($updata);
//    }
//
//    static function unbindStudent($mid)
//    {
//        $updata['s_student'] = 1;
//        return self::where(array('id' => $mid))->update($updata);
//    }
//
//    static function changePw($id, $new_pw, $re_pw)
//    {
//        $validate = Validate::make([
//            'pwd|新密码' => 'require|length:4,20|alphaNum',
//            'repwd|重复密码' => 'require|confirm:pwd',
//        ]);
//        $vali_res = $validate->check([
//            'pwd' => $new_pw,
//            'repwd' => $re_pw,
//        ]);
//        if (!$vali_res) {
//            exception($validate->getError());
//        }
//        $member = self::where(compact('id'))->find();
//        if (!$member) {
//            exception('账户信息有误');
//        }
//        $member->password = self::cryptPw($new_pw);
//        if ($member->save()) return true;
//        else {
//            exception('数据更新错误');
//        }
//    }
//
//    static function cryptPw($pw_str)
//    {
//        return password_hash($pw_str, PASSWORD_DEFAULT);
//    }
//
//    static function verifyPw($password, $hash_str)
//    {
//        return password_verify($password, $hash_str);
//    }
//
//    static function resetPw($user_phone, $pswd)
//    {
//        $member = self::where(compact('user_phone'))->find();
//        if (!$member) {
//            exception('账户信息有误');
//        }
//        $member->password = self::cryptPw($pswd);
//        if ($member->save()) return true;
//        else {
//            exception('数据更新错误');
//        }
//    }
//    static function resetPayPw($user_phone, $pswd)
//    {
//        $member = self::where(compact('user_phone'))->find();
//        if (!$member) {
//            exception('账户信息有误');
//        }
//        $member->pay_password = self::cryptPw($pswd);
//        if ($member->save()) return true;
//        else {
//            exception('数据更新错误');
//        }
//    }
//    static function checkPayPw($id, $pswd)
//    {
//        $member = self::where(compact('id'))->find();
//        if (!$member) {
//            exception('账户信息有误');
//        }
//        if (!$member->pay_password) {
//            exception('未设置支付密码 请先设置');
//        }
//        if (self::verifyPw($pswd, $member->pay_password)) return true;
//        else {
//            exception('支付密码错误');
//        }
//    }
//
//    static function checkUnlock($member)
//    {
//        if($member['unlock_time'] > time()) exception("您的账号冻结到".date('Y-m-d H:i:s', $member['unlock_time'] ));
//    }

}