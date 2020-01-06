<?php
namespace App\Service\WeChat;

use App\Exceptions\ApiException;
use App\Model\User;

class MpForUser extends MiniProgram
{
    /*
    * 通过code换区sessionId，并尝试登陆 。orgcode在有些情况下是没有的
    */
    public function code2SessionIdTryGetToken($code, $org_code = null)
    {
        // {"session_key":"","expires_in":7200,"openid":""}
        // todo 这里正式后开启
//        $session = $this->program->auth->session($code);
        $session = ["session_key"=>"111111111222","expires_in"=> 7200 ,"openid"=>"54545454545454"]; // code会过期
        // SessionKey::insert([ 'key' => 'wxlogin', 'data' => json_encode($session) ]);
        $sid = $this->storeSessionKey($session);

        $user = User::findByWxId($session['openid']);

        if($org_code) {
            // 如果有code，那么orginfo从code获取，
            $org_info = $this->getOrgInfoByCode($org_code);
            $oinfo = $this->makeOrgRes($org_info);
        } else{
            // 否则是没有orginfo信息的
            $oinfo = null;
//            throw new ApiException('无学校数据');
        }
        if ($user) {
            // 如果有user和oinfo，那么uinfo就不是null，但有可能是空的profile
            $uinfo = $oinfo ? $this->makeUinfoRes($user) : null;
            $token = $this->createToken($user);
        } else {
            $uinfo = null;
            $token = null;
        }
        if (isset($uinfo['name']) && $uinfo['name']) {
            // 有userinfo里的name设置了，并不是空则说明有profile, 也必定有orginfo
            $needapply = false;
        } else {
            $needapply = true;
        }
        if ($user) {
            $user->lastlogin_at = now();
            $user->save();
        }
        return compact('sid','token','uinfo','oinfo','needapply');
    }
    // 拼接返回数据 orgcode = null 的情况是扫码注册的时候
    public function buildResDataLogin($user, $token, $orgcode = null)
    {
        $uinfo = $org_info = null;
        $needapply = true;
        if ($orgcode) { // 如果有 orgcode说明是扫码登陆的，如果没有orgcode则是pc注册扫码
            $org_info = $this->getOrgInfoByCode($orgcode);
            $uinfo = $this->makeUinfoRes($user);
            if ($uinfo['name']) { // 有名字说明有profile
                $needapply = false; // 如果没有profile，那么就得申请了
            }
        }
        $oinfo = $org_info ? $this->makeOrgRes($org_info) : null;
        $user->lastlogin_at = now();
        $user->save();
        return compact('token', 'uinfo', 'oinfo', 'needapply');
    }
    // 拼接返回数据 orgcode = null 的情况是扫码注册的时候
    public function buildResDataReg($user, $orgcode = null)
    {
        $uinfo = $org_info = null;
        $needapply = true;
        if ($orgcode) { // 如果有 orgcode说明是扫码登陆的，如果没有orgcode则是pc注册扫码
            $org_info = $this->getOrgInfoByCode($orgcode);
            $uinfo = $this->makeUinfoRes($user);
            if ($uinfo['name']) { // 有名字说明有profile
                $needapply = false; // 如果没有profile，那么就得申请了
            }
        }
        $oinfo = $org_info ? $this->makeOrgRes($org_info) : null;
        $token = $this->createToken($user);
        return compact('token', 'uinfo', 'oinfo', 'needapply');
    }
    /*
    * 获取微信用户信息并更新用户信息 只执行一次授权
    */
    public function decryptAndUpdateUserInfo($session_id, $iv, $encryptedData)
    {
        // todo 正式后开启
        $session_key = $this->getSessionKey($session_id);
        $decrypt_info = $this->program->encryptor->decryptData($session_key, $iv, $encryptedData);
//        $decrypt_info = [
//            "openId" => "54545454545454",
//            "nickName" => "888名字是什么好111",
//            "gender" => 2,
//            "language" => "zh_CN",
//            "city" => "Jinan", "province" => "Shandong", "country" => "China",
//            "avatarUrl" => "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIpfhQJK81NCJMXNNmTSe5VTkSCI3f2Qvexut8920m3XpSvawhU2HtWKAYp9XoCAhQP9FTkjxsj7A/132"
//        ];
//        SessionKey::insert([
//            'key' => 'getuserinfo',
//            'data' => json_encode($decrypt_info)
//        ]);
        $user = User::updateByWxUserInfo($decrypt_info);
        $uinfo = $this->makeUinfoRes($user);
        return compact('uinfo');
    }
    public function createToken(User $user)
    {
        if (! $token = auth('api')->login($user)) {
            throw new ApiException('获取Token失败');
        } return $token;
    }
    private function makeUinfoRes(User $user)
    {
        if($profile = User::getProfile($user->username)) {
            return [
                'mobile' => $user->username,
                'nickname' => $user->nickname,
                'name' => $profile->name,
                'avatar' => $profile->avatar ? $profile->avatar : $user->avatar
            ];
        } else {
            return [
                'mobile' => $user->username,
                'nickname' => $user->nickname,
                'name' => null,
                'avatar' => $user->avatar
            ];
        }
    }
    public function updateUser(User $user, $data)
    {
        if(count($data) == 0) throw new ApiException('用户更新数据缺失');
        $res = $user->save();
        if ($res == false) throw new ApiException('更新用户数据失败');
        return $user;
    }
}