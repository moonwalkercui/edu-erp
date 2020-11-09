<?php
/**
 * ZIHAIMX [ WE CAN DO IT JUST ENJOY ]
 * 短信类
 * @since   2016-12-08
 * @author  TechLee <767049337@qq.com>
 */
namespace notify;

use app\backend\model\SmsLog;
use think\Config;
use think\Request;

class Sms
{
    /**
     * 发送短信
     * @param $phone 接收手机号，用分号隔开多个手机号
     * @param $body    发送内容
     * @param $content_type短信内容类型，1为验证码类短信
     * 建周短信经过测试可以正常发送，其他平台有待测试
     */
    public static function send($phone, $body, $content_type = '1')
    {

        $body = str_replace('【', '(', $body);
        $body = str_replace('】', ')', $body);

        $smsConfig = Config::get('sms');

        $sms_user     = $smsConfig['sms_user'];
        $sms_password = $smsConfig['sms_password'];
        $sms_note     = $smsConfig['sms_note'];
        $sms_type     = $smsConfig['sms_type'];

        if(empty($sms_type)) return '短信未开通！';

        $body .= $sms_note;

        $addData['create_ip']    = Request::instance()->ip();
        $addData['body']         = $body;
        $addData['content_type'] = $content_type;
        $addData['phone']        = $phone;
        $addData['status']       = '0';

        $sms_log = new SmsLog();
        $sms_log->data($addData);
        //限制一：12小时内不得超过500
        $countphone12ha = $sms_log->where("FROM_UNIXTIME(`create_time`) >= DATE_SUB(NOW(),INTERVAL 12 HOUR) and status>0")->count('id');
        if ($countphone12ha > 500) {return '您的操作过于频繁(001)，请稍后重试。';}
        //限制二：单个手机号12小时不得超过30;
        $countphone12hs = $sms_log->where("`phone` = '{$phone}' and FROM_UNIXTIME(`create_time`) >= DATE_SUB(NOW(),INTERVAL 12 HOUR) and status>0")->count('id');
        if ($countphone12hs > 30) {return '您的操作过于频繁(002)，请稍后重试。';}
        //限制三：单个手机号300秒不得超过5条
        $countphone = $sms_log->where("`phone` = '{$phone}' and create_time > (unix_timestamp()-300)")->count('id');
        //限制四：验证码类短信，单个手机号或单个ip300秒不得超过5条
        if ($content_type == 1) {
            $countphone = $sms_log->where("(`phone` = '{$phone}' or create_ip like '%" . $addData['create_ip'] . "%') and create_time > (unix_timestamp()-300)")->count('id');
        }
        if ($countphone >= 5) {return '您的操作过于频繁(003)，请稍后重试。';}
        $newid = $sms_log->save();

        $send_id = '';
        switch ($sms_type) {
            //建周短信
            case '1':
                //建周短信使用分号隔开多个手机号
                $phone    = str_replace(',', ';', $phone);
                $postData = [
                    'account'    => $sms_user,
                    'password'   => $sms_password,
                    'destmobile' => $phone,
                    'msgText'    => $body,
                ];
                $rs      = url_request($postData, 'http://www.jianzhou.sh.cn/JianzhouSMSWSServer/http/sendBatchMessage');
                $send_id = $rs;
                $status  = $rs > 0 ? 1 : 2;
                break;
            //吉信通接口
            case '2':
                $sendurl = "http://service.winic.org:8009/sys_port/gateway/?id=" . $sms_user . "&sms_password=" . $sms_password . "&to=" . $phone . "&content=" . $body . "&time=";
                $rs      = file_get_contents($sendurl);
                $data    = explode("/", $rs);
                $status  = $data[0] == "000" ? 2 : 1;
                break;
            // 维那多
            case '3':
                $sendurl = "http://yl.phonesms.net/send/gsend.aspx?name=" . $sms_user . "&sms_password=" . $sms_password . "&dst=" . $phone . "&msg=" . $body;
                $rs      = file_get_contents($sendurl);
                $rs      = auto_charset($rs, "gbk", "utf-8");
                parse_str($rs, $rsArr);
                // $smsLog->where(array('id' => $sendId))->setField(array('successphone' => $rsArr['success'], 'successnum' => $rsArr['num'], 'failephone' => $rsArr['faile'], 'err' => $rsArr['err'], 'errid' => $rsArr['errid']));
                $status = @$rsArr['num'] <= 0 ? 2 : 1;
                break;
            // 创瑞通讯平台cr6868
            case '4':
                $flag   = 0;
                $params = ''; //要post的数据
                $argv   = [
                    'name'         => $sms_user, //必填参数。用户账号
                    'sms_password' => $sms_password, //必填参数。（web平台：基本资料中的接口密码）
                    'content'      => $body, //必填参数。发送内容（1-500 个汉字）UTF-8编码
                    'mobile'       => $phone, //必填参数。手机号码。多个以英文逗号隔开
                    'stime'        => '', //可选参数。发送时间，填写时已填写的时间发送，不填时为当前时间发送
                    'sign'         => '', //必填参数。用户签名。
                    'type'         => 'pt', //必填参数。固定值 pt
                    'extno'        => '', //可选参数，扩展码，用户定义扩展码，只能为数字
                ];
                foreach ($argv as $key => $value) {
                    if ($flag != 0) {
                        $params .= "&";
                        $flag = 1;
                    }
                    $params .= $key . "=";
                    $params .= urlencode($value); // urlencode($value);
                    $flag = 1;
                }
                $url    = "http://web.cr6868.com/asmx/smsservice.aspx?" . $params; //提交的url地址
                $con    = substr(file_get_contents($url), 0, 1); //获取信息发送后的状态
                $status = $con != '0' ? 2 : 1;
                break;
            // 互亿无线 ihuyi
            case '5':
                $url    = 'http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=' . $sms_user . '&sms_password=' . strtoupper(md5($sms_user . $sms_password)) . '&mobile=' . $phone . '&content=' . $body . '&ext=&stime=&rrid=&msgfmt=';
                $rs     = file_get_contents($url);
                $status = $rs > 0 ? 1 : 2;
                break;
            //最好的短信平台-短信宝官网
            case '6':
                $url    = 'http://api.smsbao.com/sms?u=' . $sms_user . '&p=' . md5($sms_password) . '&m=' . $phone . '&c=' . $body;
                $rs     = file_get_contents($url);
                $status = $rs != '0' ? 2 : 1;
                break;
            default:
                $status = 0;
                break;
        }
        $saveData = array('status' => $status, 'send_id' => $send_id);
        (new SmsLog)->save($saveData, ['id' => $sms_log->id]);
        return $status == 1 ? true : '发送失败！';
    }
    /**
     * 短信余额
     */
    public static function getAccount()
    {
        $smsConfig = Config::get('sms');

        $sms_user     = $smsConfig['sms_user'];
        $sms_password = $smsConfig['sms_password'];
        $sms_note     = $smsConfig['sms_note'];
        $sms_type     = $smsConfig['sms_type'];
        if(empty($sms_type)) return '短信未开通！';
        $account_money = 0;
        switch ($sms_type) {
            case '1':
                $postData = [
                    'account'  => $sms_user,
                    'password' => $sms_password,
                ];
                $rs            = url_request($postData, 'http://www.jianzhou.sh.cn/JianzhouSMSWSServer/http/getUserInfo');
                $rsArr         = xml_to_array($rs);
                $account_money = isset($rsArr['remainFee']) ? intval(@$rsArr['remainFee']) . '条' : (isset($rsArr['error']) ? $rsArr['error'] : '--');
                break;
            case '2':
                $account_money = @file_get_contents("http://service.winic.org:8009/webservice/public/remoney.asp?uid={$sms_user}&pwd={$sms_password}");
                if ($account_money < 0) {
                    $account_money = "用户名或密码错误";
                }

                break;
            case '3':
                $rs = @file_get_contents("http://yl.mobsms.net/send/getfee.aspx?name={$sms_user}&pwd={$sms_password}");
                $rs = auto_charset($rs, "gbk", "utf-8");
                parse_str($rs, $rsArr);
                $account_money = intval(@$rsArr['id']);
                break;
            case '6':
                $url = 'http://api.smsbao.com/query?u=' . $sms_user . '&p=' . md5($sms_password);
                $rs  = file_get_contents($url);
                $rs  = preg_split('/[\r\n]+/s', $rs);
                if ($rs['0'] == '0') {
                    // 发送条数,剩余条数
                    $account_money = $rs['1'] . '(发送条数,剩余条数)';
                }
                break;
            default:
                break;
        }
        return $account_money;
    }
}
