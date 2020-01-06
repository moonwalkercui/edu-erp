<?php
namespace App\Service\WeChat;

use App\Exceptions\ApiException;
use App\Model\Member;
use App\Model\MemberProfile;

class MpForMember extends MiniProgram
{
    /*
    * 通过code换区sessionId，并尝试登陆 $ref_id 扫码推荐人id
    */
    public function code2SessionIdTryGetToken($code, $ref_id = null, $salesman_uname = null)
    {
        $session = $this->program->auth->session($code);
        if(isset($session['errcode'])) throw new ApiException('系统登录出错');
//        var_dump($session);die;
//        array(2) { ["session_key"]=> string(24) "zEzDC1KzaxH1CsFN+zrRxw==" ["openid"]=> string(28) "otzfi5P8NSXX_iw5sS0IEjPPVrb8" }
        // SessionKey::insert([ 'key' => 'wxlogin', 'data' => json_encode($session) ]);
//        $session = ["session_key"=>"111111111222","expires_in"=> 7200 ,"openid"=>"hellojack"]; // code会过期
        $sid = $this->storeSessionKey($session);
        $member = Member::findOrCreateByWxId($session['openid'], $ref_id, $salesman_uname); // 判断member是否已经注册，如果没有注册则新增
        $mprofile = $member->profile;
//        $profile = $this->makeProfileRes($mprofile);
        $profile = MemberProfile::makeProfileRes($mprofile);
        $token = MemberProfile::makeToken($mprofile);
//        $token = $this->createToken($mprofile);
        // 没有token就是没有档案
        return compact('sid','token','profile','type');
    }
//    protected function createToken(MemberProfile $pool)
//    {
//        if (! $token = auth('api_member')->login($pool)) {
//            throw new ApiException('获取Token失败');
//        } return $token;
//    }
    /*
   * 获取微信用户信息并更新用户信息 只执行一次授权
   */
    public function decryptAndUpdateUserInfo($session_id, $iv, $encryptedData)
    {
        $session_key = $this->getSessionKey($session_id);
        $decrypt_info = $this->program->encryptor->decryptData($session_key, $iv, $encryptedData);
//        $decrypt_info = [
//            "openId" => "54545454545454",
//            "nickName" => "名字是什么好",
//            "gender" => 2,
//            "language" => "zh_CN",
//            "city" => "Jinan", "province" => "Shandong", "country" => "China",
//            "avatarUrl" => "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIpfhQJK81NCJMXNNmTSe5VTkSCI3f2Qvexut8920m3XpSvawhU2HtWKAYp9XoCAhQP9FTkjxsj7A/132"
//        ];
//        SessionKey::insert([
//            'key' => 'getuserinfo',
//            'data' => json_encode($decrypt_info)
//        ]);
        $member = Member::updateByWxUserInfo($decrypt_info);
        $minfo = $this->makeMinfoRes($member);
        return compact('minfo');
    }

    // 建立档案并更新用户信息
    public function createPoolAndUpdateMember($sid,$org_code,$data)
    {
        $org_id = $this->getOrgInfoByCode($org_code)->id;
        $member = $this->updateMember($this->getMemberBySessionId($sid), $data);

        $pool_info = $this->createPool($data['mobile'], $org_id, $data['name']);
//        $token = $this->createToken($pool_info);
        $token = MemberProfile::makeToken($pool_info);
        $minfo = $this->makeMinfoRes($member);
        $mpool = $this->makePoolRes($pool_info);

        return compact('minfo','mpool','token');
    }
    // 家长绑定学员档案
    public function parentBindMember($sid,$org_code,$data)
    {
        $student = Member::checkPassword($data['mobile'], $data['password']);
        if ($student == false) {
            throw new ApiException('密码错误');
        }
        $minfo = $this->makeMinfoRes($student);

        $token = $pool_info = $mpool = null;
        $parent = $this->getMemberBySessionId($sid);

        if (MemberPool::updateParentId($data['mobile'], $parent->id)) {
            $pool_info = MemberPool::where([
                'mobile' => $data['mobile'],
            ])->first();
            if ($pool_info) {
                $parent->type = 2;
                $parent->save();
//                $token = $this->createToken($pool_info);
                $token = MemberProfile::makeToken($pool_info);
                $mpool = $this->makePoolRes($pool_info);
            }
        }
        return compact('minfo','mpool','token');
    }
    // 更新密码
    public function updateMember(Member $member, $data)
    {
        if(count($data) == 0) throw new ApiException('学员更新数据缺失');
        if(isset($data['mobile'])) $member->mobile = $data['mobile'];
        if(isset($data['name'])) $member->name = $data['name'];
        if(isset($data['password'])) $member->password = bcrypt($data['password']);
//        if(isset($data['default_pool_id'])) $member->default_pool_id = $data['default_pool_id'];
        $res = $member->save();
        if ($res == false) throw new ApiException('更新学员数据失败');
        return $member;
    }
    /*
    * 创建档案，用于手机号和家长密码的绑定
    */
    protected function createPool($mobile, $org_id, $name)
    {
        try{
            $pool = MemberPool::create([
                'mobile' => $mobile,
                'name' => $name,
            ]);
            return $pool;
        } catch (\Exception $e) {
            throw new ApiException($e->getMessage());
        }
    }

    protected function getMemberBySessionId($session_id)
    {
        $open_id = $this->getSessionOpenid($session_id);
        $member = Member::where('miniapp_openid', $open_id)->first();
        if($member == false) throw new ApiException('未查询到会员记录');
        return $member;
    }
    protected function findPoolByMobile($mobile)
    {
        return MemberPool::where([ 'mobile' => $mobile ])->first();
    }
//    protected function findPoolByMobileAndOrg($mobile, $org_id)
//    {
//        // 如果非空则表示已经绑定过档案有memberpool资料
//        return MemberPool::where([
//            'mobile' => $mobile,
//        ])->first();
//    }
//
//    protected function findPoolByParentAndOrg($parend_id, $org_id)
//    {
//        // 如果非空则表示已经绑定过档案有memberpool资料
//        return MemberPool::where([
//            'parent_id' => $parend_id,
//        ])->first();
//    }
    /*
  * 家长获取pool
  */
    protected function findPoolByID($pool_id)
    {
        $pool = MemberPool::where('id',$pool_id)->first();
        if($pool)
            return $pool;
        else
            throw new ApiException('未查询到关联档案');
    }

//    // 返回的pool数据格式
//    protected function makeProfileRes($pool)
//    {
//        return [
//            'member_id' => $pool->member_id,
//            'avatar' => $pool->avatar,
//            'name' => $pool->name,
//            'mobile' => $pool->mobile
//        ];
//    }

}