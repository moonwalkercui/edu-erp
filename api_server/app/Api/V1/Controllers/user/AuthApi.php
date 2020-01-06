<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\WechatLogic;
use App\Model\Organization;
use App\Model\User;
use App\Service\Api\UserService;
use App\Service\ApiTrait;
use App\Service\WeChat\MpForUser;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Cache;
use Mews\Captcha\Facades\Captcha;

/**
 * # 用户权限类
 *
 */
// jwt参考文档：http://jwt-auth.readthedocs.io/en/develop/auth-guard/#attempt
// 生成接口文档命令：php artisan api:docs --output-file ApiDocs.md
class AuthApi extends Controller
{
    use ApiTrait;
    public function __construct()
    {
        $this->middleware('auth:api', ['except' => [
            'login',
            'register',
            'wxLogin',
            'wxUsernameLogin',
            'wxUsernameRegister'
        ]]);
    }

    /**
    * 登录接口
    * 通过账号密码登陆
    * @Post("/auth/login")
    * @Parameters({
    *      @Parameter("username", description="账号", required=true),
    *      @Parameter("password", description="密码", required=true),
    * })
    */
    public function login(Request $request)
    {

//        $res = CourseSign::create([
//            'organization_id' => 9,
//            'member_mobile' => '121212',
//            'member_name' => 'haha',
//            'course_id' => 90,
//        ]);
//        echo '创建结果完毕';
//        die;
//        $this->validateParam($request->all() , [
//            'username' => 'required',
//            'password' => 'required',
//            'captcha' => 'required|captcha',
//        ],[
//            'captcha.required' => '缺少验证码', // trans('validation.required')
//            'captcha.captcha' => '验证码错误' //trans('validation.captcha'),
//        ]);


        $this->validateParam($request->all() , [
            'username' => 'required',
            'password' => 'required',
            'code' => 'required',
            'captcha' => 'required',
        ], [
            'code.required' => '缺少CODE',
            'captcha.required' => '缺少验证码',
        ]);

        $captcha = new \App\Service\Api\Captcha();
        if($captcha->validateCode($request->captcha, $request->code) == false) {
            return $this->error('验证码错误');
        }

        $credentials = request(['username', 'password']);
        if (!$token = auth('api')->attempt($credentials)) {
            return $this->error('账号或密码错误');
        }
        $userInfo = $this->getUserInfo();
//        if(UserService::isManager() == false) return $this->error('该账号不是管理员');
        User::where('username',$credentials['username'])->update(['lastlogin_at'=>now()]);
        return $this->fetch(compact('token','userInfo'));
    }
    /**
     * 小程序code自动登录
     * 通过opengid登陆
     * @Get("/auth/wxlogin")
     * @Parameters({
     *      @Parameter("code", description="wx:login返回的code", required=false),
     * })
     * @Response(200, body={
     *     "token": "如果有token，说明有user记录，否则需要注册；如果有token，needapply为true，说明需要申请加入org;如果有token，needapply为false，说明已经加入了orginfo对应的org",
     *     "sid": "code换取的sessionid",
     *     "uinfo": "用户信息，如果是null说明这个微信没有注册过或没有加入这个org；name是从profile里获取的",
     *     "oinfo": "组织信息，如果为null说明没有orgcode或者新用户没有登陆过任何组织，导致没有获取到",
     *     "needapply": "是否需要引导进申请加入组织页面，用于登录orgcode对应的组织但没有加入的情况",
     * })
     */
    public function wxLogin(Request $request)
    {
        $this->validateParam($request->all() , [
            'code' => 'required',
        ],[
            'code.required'=>'缺少数据code1',
        ]);
        $code = $request->input('code');
        $org_code = $request->input('orgcode', null);
        $program = new MpForUser();
        return $this->fetch($program->code2SessionIdTryGetToken($code, $org_code));
    }
    /**
     * 小程序账号密码登陆
     * 通过账号，密码，sid，orgcode登录
     * @Get("/auth/wxlogin")
     * @Parameters({
     *      @Parameter("username", required=true),
     *      @Parameter("password", required=true),
     *      @Parameter("sid", required=true),
     *      @Parameter("orgcode", required=false),
     * })
     * @Response(200, body={
     *     "token": "必定返回，除非用户密码不对",
     *     "uinfo": "用户信息，如果是null说明没有orgcode参数，或用户没有加入过任何组织",
     *     "oinfo": "组织信息，如果是null说明同上",
     *     "needapply": "是否需要引导进申请加入组织页面，用于没有orgcode或用户没有加入过任何组织的情况",
     * })
     */
    public function wxUsernameLogin(Request $request)
    {
        $this->validateParam($request->all() , [
            'username' => 'required',
            'password' => 'required',
            'sid' => 'required',
        ],[
            'sid.required'=>'缺少数据3',
        ]);
        $credentials = request(['username', 'password']);
        if (!$token = auth('api')->attempt($credentials)) {
            return $this->error('登录失败');
        }
        $data = cleanXss($request->all());
        $program = new MpForUser();
        $user = auth('api')->user();
        $user->miniapp_openid = $program->getSessionOpenid($data['sid']);
        return $this->fetch($program->buildResDataLogin(
            $user, $token, isset($data['orgcode']) ? $data['orgcode'] : null
        ));
    }
    /**
     * 小程序账号密码注册
     * 通过账号，密码，sid，orgcode登录，如果有orgcode则表示是通过分享来的，如果没有，则是扫码注册阶段。
     * @Get("/auth/wxlogin")
     * @Parameters({
     *      @Parameter("username", required=true),
     *      @Parameter("password", required=true),
     *      @Parameter("nickname", required=true),
     *      @Parameter("sid", required=true),
     *      @Parameter("orgcode", required=false),
     * })
     * @Response(200, body={
     *     "token": "注册成功必定返回",
     *     "uinfo": "用户信息，如果是null说明没有orgcode参数",
     *     "oinfo": "组织信息，如果是null说明同上",
     *     "needapply": "是否需要引导进申请加入组织页面，用于没有orgcode注册的情况",
     * })
     */
    public function wxUsernameRegister(Request $request)
    {
        $this->validateParam($request->all() , [
            'username' => 'required|unique:users',
            'password' => 'required',
            'nickname' => 'required',
            'sid' => 'required'
        ],[
            'nickname.required'=>'缺少昵称',
            'sid.required'=>'缺少数据3'
        ]);
        $data = cleanXss($request->all());
        $program = new MpForUser();
        $user = User::register([
            'miniapp_openid' => $program->getSessionOpenid($data['sid']),
            'username' => $data['username'],
            'password' => bcrypt($request->password),
            'nickname' => $data['nickname'],
            'gender' => $data['userinfo']['gender'],
            'city' => $data['userinfo']['city'],
            'province' => $data['userinfo']['province'],
            'avatar' => $data['userinfo']['avatarUrl'],
            'lastlogin_at' => now(),
        ]);
        return $this->fetch($program->buildResDataReg($user, isset($data['orgcode']) ? $data['orgcode'] : null));
    }
    /**
     * 小程序encryptData登录并更新用户信息
     * 通过encryptData里的UnionId登陆
     * @Get("/auth/wxdecryptlogin")
     * @Parameters({
     *      @Parameter("userInfo", description="wx:login返回的userInfo", required=true),
     * })
     */
//    public function wxUpdateUserInfo(Request $request)
//    {
//        $this->validateParam($request->all() , [
//            'encryptedData' => 'required',
//            'iv' => 'required',
//            'sid' => 'required',
//        ],[
//            'encryptedData.required'=>'缺少数据1',
//            'iv.required'=>'缺少数据2',
//            'sid.required'=>'缺少数据3',
//        ]);
//        $encryptedData = $request->input('encryptedData');
//        $iv = $request->input('iv');
//        $sid = $request->input('sid');
//        $program = new MpForUser();
//        return $this->fetch($program->decryptAndUpdateUserInfo($sid, $iv, $encryptedData));
//    }
    /**
     * 小程序encryptData登录
     * 通过encryptData里的UnionId登陆
     * @Get("/auth/wxdecryptlogin")
     * @Parameters({
     *      @Parameter("userInfo", description="wx:login返回的userInfo", required=true),
     * })
     */
    public function wxDecryptLogin(Request $request)
    {
        $user = WechatLogic::decryptLogin($request->userInfo);
        $token = auth('api')->login($user);
        $userInfo = $this->getUserInfo();
        return $this->fetch(compact('token','userInfo'));
    }
    /**
     * 小程序encryptData登录
     * 通过encryptData里的UnionId登陆
     * @Get("/auth/user")
     */
    public function getUser()
    {
        $data = $this->getUserInfo();
        return $this->fetch($data);
    }
    public function getUserInfo()
    {
        $user = UserService::getUserInfo();
//        $org = Organization::where('id',$user->organization_id)->first();
        return [
            'uname' => $user->username,
//            'ocode' => $org ? $org->code : null, // 组织代码
//            'oname' => $org ? $org->organization_name : null,
            'division' => $user->profile ? $user->profile->division_id : null,
            'name' => $user->profile ? $user->profile->name : null,
            'avatar' => $user->profile ? $user->profile->avatar : null,
            'pms' => UserService::isManager() ? -1 : $user->getPermissions() // -1表示是组织管理员
        ];
    }
    public function refreshToken()
    {
        $token = auth('api')->refresh();
        return $this->fetch(compact('token'));
    }
    /**
     * 登出
     * @Get("/auth/logout")
     */
    public function logout()
    {
        auth('api')->logout();
        return $this->success('已退出');
    }
    /**
     * 获取用户手机验证码
     */
//    public function getRandKey()
//    {
//        // 获取手机号码
//        $payload = app('request')->only('phone');
//        $phone = $payload['phone'];
//        if(empty($phone)) return $this->response->array(['error' => 'Send Sms Error']);
//        // 获取验证码
//        $randNum = $this->__randStr(6, 'NUMBER');
//        // 验证码存入缓存 1 分钟
//        $expiresAt = 2;
//        Cache::put($phone, $randNum, $expiresAt);
//        // 短信内容
//        $smsTxt = '验证码为：' . $randNum . '，请在 60 秒内使用！';
//        // 发送验证码短信
//        $res = $this->_sendSms($phone, $smsTxt);
//        // 发送结果
//        if ($res) {
//            return $this->response->array(['success' => 'Send Sms Success']);
//        } else {
//            return $this->response->array(['error' => 'Send Sms Error']);
//        }
//    }

    /**
     * 注册
     * @Post("/auth/register")
     * @Parameters({
     *      @Parameter("username", description="账号", required=true),
     *      @Parameter("password", description="密码", required=true),
     *      @Parameter("nickname", description="称呼", required=true),
     * })
     */
    public function register(Request $request)
    {

//        $user = User::with('profile')->find(60);
//
//        var_dump($user->toArray());

        die('注册方法已关闭');

        $payload = $request->only('username', 'password' ,'nickname');
        $this->validateParam($payload, [
            'username' => config('system.validate.username'),
            'password' => config('system.validate.password'),
            'nickname' => 'required',
        ],['nickname.required'=>'缺少昵称']);
//        // 验证手机验证码
//        if (Cache::has($payload['phone'])) {
//            $key = Cache::get($payload['key']);
//            if ($key != $payload['key']) {
//                return $this->response->array(['error' => '验证码错误']);
//            }
//        } else {
//            return $this->response->array(['error' => '验证码错误']);
//        }
        // 验证格式
        // 创建用户
        $result = User::create([
            'username' => $payload['username'],
            'password' => bcrypt($payload['password']),
            'nickname' => cleanXss($payload['nickname'])
        ]);
        if ($result) {
            return $this->success('感谢您的注册');
        } else {
            return $this->error('注册失败');
        }
    }

}