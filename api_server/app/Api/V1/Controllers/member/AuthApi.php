<?php
namespace App\Api\V1\Controllers\member;
use App\Logic\ApiMember\MemberLogic;
use App\Model\Member;
use App\Model\MemberProfile;
use App\Service\Api\MemberService;
use App\Service\ApiTrait;
use App\Service\WeChat\MpForMember;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

/**
 * # C 端权限类
 */
// jwt参考文档：http://jwt-auth.readthedocs.io/en/develop/auth-guard/#attempt
// 生成接口文档命令：php artisan api:docs --output-file ApiDocs.md
class AuthApi extends Base
{
    use ApiTrait;
//    public function __construct()
//    {
//        $this->middleware('auth:api_member', ['except' => ['login', 'updateUserInfo', 'getUserMobile']]);
//    }

    /**
     * 小程序onlaunch登录
     * 小程序端维护login状态，后台不做code缓存
     * @Get("/mauth/login")
     * @Versions({"v1"})
     * @Parameters({
     *      @Parameter("code", description="wxlogin code", required=true),
     * })
     * @Response(200, body={
     *     "sid": "code换取的sessionid",
     *     "token": "如果有token，没有token就是没有建档，需要进入建档页面",
     *     "minfo": "学员信息，肯定有的，如果数据库里原来没有，那么会自动新建",
     *     "mpool": "学员档案信息，有档案才会有的数据",
     *     "oinfo": "组织信息，肯定会有，如果没有会异常",
     *     "type": "1学员 2家长",
     * })
     */
    public function login(Request $request)
    {
        $this->validateParam($request->all() , [
            'code' => 'required',
        ],[
            'code.required'=>'缺少数据code1',
        ]);
        $code = $request->input('code');
        $refid = $request->input('refid', null);
        $salid = $request->input('salid', null);
        $program = new MpForMember();
        return $this->fetch($program->code2SessionIdTryGetToken($code, $refid, $salid));
    }
    /**
     * 获取并更新用户信息
     * 使用wx.getuserinfo返回数据
     * @Get("/mauth/updateuserinfo")
     * @Versions({"v1"})
     * @Parameters({
     *      @Parameter("encryptedData", required=true),
     *      @Parameter("iv", required=true),
     *      @Parameter("sid", required=true),
     * })
     * @Response(200, body={"avatar": "string", "mobile": "string", "nick_name": "string"})
     */
    public function updateUserInfo(Request $request)
    {
        $this->validateParam($request->all() , [
            'encryptedData' => 'required',
            'iv' => 'required',
            'sid' => 'required',
        ],[
            'encryptedData.required'=>'缺少数据1',
            'iv.required'=>'缺少数据2',
            'sid.required'=>'缺少数据',
        ]);
        $encryptedData = $request->input('encryptedData');
        $iv = $request->input('iv');
        $sid = $request->input('sid');
        $program = new MpForMember();
        return $this->fetch($program->decryptAndUpdateUserInfo($sid, $iv, $encryptedData));
    }
    /**
     * 获取用户手机号码
     * @Get("/mauth/getusermobile")
     * @Versions({"v1"})
     * @Parameters({
     *      @Parameter("encryptedData", required=true),
     *      @Parameter("iv", required=true),
     *      @Parameter("sid", required=true),
     * })
     * @Response(200, body={"mobile": "string"})
     */
    public function getUserMobile(Request $request)
    {
        $this->validateParam($request->all() , [
            'encryptedData' => 'required',
            'iv' => 'required',
            'sid' => 'required',
        ],[
            'encryptedData.required'=>'缺少数据1',
            'iv.required'=>'缺少数据2',
            'sid.required'=>'缺少数据',
        ]);
//
        $encryptedData = $request->input('encryptedData');
        $iv = $request->input('iv');
        $sid = $request->input('sid');
        $program = new MpForMember();
        return $this->fetch($program->decryptPhone($sid, $iv, $encryptedData));
    }

    /**
     * 增加档案
     * @Get("/mauth/createpool")
     * @Versions({"v1"})
     * @Parameters({
     *      @Parameter("sid", required=true),
     *      @Parameter("password", required=true),
     *      @Parameter("name", required=true),
     *      @Parameter("mobile", required=true),
     *      @Parameter("orgcode", required=true),
     * })
     * @Response(200, body={"minfo": "string","mpool": "string","token": "string"})
     */
    public function createPool(Request $request)
    {
        $this->validateParam($request->all() , [
            'sid' => 'required',
            'password' => 'required',
            'name' => 'required',
            'mobile' => 'required',
            'orgcode' => 'required',
        ],[
            'sid.required'=>'缺少数据1',
            'password.required'=>'缺少数据2',
            'name.required'=>'缺少数据3',
            'mobile.required'=>'缺少数据4',
            'orgcode.required'=>'缺少数据5',
        ]);
        $input = cleanXss($request->all());
        $sid = $input['sid'];
        $orgcode = $input['orgcode'];
        $data['password'] = $input['password'];
        $data['name'] = $input['name'];
        $data['mobile'] = $input['mobile'];
        $program = new MpForMember();
        return $this->fetch($program->createPoolAndUpdateMember($sid,$orgcode,$data));
    }
    /**
     * 家长绑定学员账号
     * @Get("/mauth/parentbindmember")
     * @Versions({"v1"})
     * @Parameters({
     *      @Parameter("sid", required=true),
     *      @Parameter("password", required=true),
     *      @Parameter("mobile", required=true),
     * })
     * @Response(200, body={"mobile": "string"})
     */
    public function parentBindMember(Request $request)
    {
        $this->validateParam($request->all() , [
            'sid' => 'required',
            'password' => 'required',
            'mobile' => 'required',
            'orgcode' => 'required',
        ],[
            'sid.required'=>'缺少数据1',
            'password.required'=>'缺少数据2',
            'mobile.required'=>'缺少数据4',
            'orgcode.required'=>'缺少数据5',
        ]);
        $input = cleanXss($request->all());
        $sid = $input['sid'];
        $orgcode = $input['orgcode'];
        $data['password'] = $input['password'];
        $data['mobile'] = $input['mobile'];
        $program = new MpForMember();
        return $this->fetch($program->parentBindMember($sid,$orgcode,$data));
    }

}