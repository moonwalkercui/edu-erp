<?php
namespace App\Service\WeChat;
use App\Exceptions\ApiException;
use App\Model\SessionKey;
use App\Model\WxMsgTemplate;
use EasyWeChat\Factory;

class MiniProgram
{
    public $program;
    public function __construct()
    {
        $config = Config::appConfig();
        $this->program = Factory::miniProgram($config);
    }
    /*
    * 获取微信手机号码
    */
    public function decryptPhone($session_id, $iv, $encryptedData)
    {
        $session_key = $this->getSessionKey($session_id);
        $decrypt_phone = $this->program->encryptor->decryptData($session_key, $iv, $encryptedData);
//        $decrypt_phone["purePhoneNumber"] = "15666323771";
        $mobile = $decrypt_phone ["purePhoneNumber"]; // 有时候是获取不到手机号的，比如没有手机注册的微信。
        return ['mobile' => $mobile];
        // array(4) {
        // ["phoneNumber"]=> string(11) "15666323771" // 用户绑定的手机号（国外手机号会有区号）
        // ["purePhoneNumber"]=> string(11) "15666323771" // 没有区号的手机号
        // ["countryCode"]=> string(2) "86"
        // ["watermark"]=> array(2) {
        //     ["timestamp"]=> int(1533722655)
        //     ["appid"]=> string(18) "wx987219883b344bc3"
        //   }
        // }
    }
    // 返回的pool数据格式
//    protected function makeOrgRes($org)
//    {
//        return [
//            'code' => $org->code,
//            'name' => $org->organization_name,
//            'level' => $org->level
//        ];
//    }
    protected function storeSessionKey($session)
    {
        return SessionKey::store($session);
    }
    protected function getSessionKey($session_id)
    {
        return SessionKey::getSessionKey($session_id);
    }
    public function getSessionOpenid($session_id)
    {
        return SessionKey::getSessionOpenid($session_id);
    }
    // 获取学校门店信息
    public function getOrgInfoByCode($org_code)
    {
        if( $org = Organization::where('code', $org_code)->first())
            return $org;
        else throw new ApiException('未知组织');
    }
    // 发送模板消息
    public function sendTemplateMsg($openid, $form_id, $template_sn, $msg_data, $page = 'pages/products')
    {
        switch ($template_sn) {
            case 'order_paid':
                $info = WxMsgTemplate::where('temp_sn', $template_sn)->first();
                break;
            default: throw new ApiException('缺少template_sn');
        }

        $this->program->template_message->send([
            'touser' => $openid,
            'template_id' => $info->temp_id,
            'page' => $page,
            'form_id' => $form_id,
            'data' => $msg_data,
        ]);



//        $this->program->template_message->send([
//            'touser' => 'user-openid',
//            'template_id' => 'template-id',
//            'page' => 'index',
//            'form_id' => 'form-id',
//            'data' => [
//                'keyword1' => 'VALUE',
//                'keyword2' => 'VALUE2',
//                // ...
//            ],
//        ]);
    }
}