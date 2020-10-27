<?php
namespace YunPrinter\driver;

class JiaBo extends Driver
{
    protected $host = 'http://api.poscom.cn'; //接口IP或域名
    protected $port = '80';                   //接口端口
    protected $apiKey='9BALOEGL';                     //api密钥
    protected $memberCode='cd265356a1c14a4088397a5844d8e190';                 //商户编码
    protected $mode = '2';                    //打印类型
    protected $charset = 1;                   //编码格式 （票据机型请选择 1：gb18030，标签机型请选择 4：utf-8）
    protected $action = 'sendMsg';            //发送数据到打印机
//    protected $securityCode='';               //安全检验码
//    protected $deviceNo = '';                 //设备编码
//    protected $msgNo = '';                    //订单编号
//    protected $msgDetail = '';                //打印内容
//    protected $DemoMsgDetail = '';            //打印内容

    public function __construct()
    {
        parent::__construct();
    }
    public function runPrinter($deviceNo, $msgNo, $msgDetail)
    {
        $reqTime = $this->getMillisecond();
        $securityCode = md5($this->memberCode.$deviceNo.$msgNo.$reqTime.$this->apiKey);
        $url = $this->host.':'.$this->port.'/apisc/sendMsg';
        $content['charset'] = $this->charset;
        $content['reqTime'] = $reqTime;
        $content['memberCode'] = $this->memberCode;
        $content['deviceNo'] = $deviceNo;
        $content['securityCode'] = $securityCode;
        // 打印内容
        $content['msgDetail'] = $msgDetail;
        $content['msgNo'] = $msgNo;
        // reprint 是否验证订单编号，1：不验证订单编号，可重新打印订单
        $content['reprint'] = 1;
        // multi 是否多订单模式，默认0：为单订单模式，1：多订单模式，
        // 多订单模式下 $msgDetail 为json格式，格式为{"ordernum001":"msgDetail001","ordernum002":"msgDetail002"}
        // 多订单模式下订单编号不能重复，必须填写。建议最大订单数为50
        $content['multi'] = 0;
        // 打印类型
        $content['mode'] = $this->mode;
        // 打印联数
        $content['times'] = '';
        $res = request_post($url, $content);

        return json_decode($res,true);

    }

    public function addPrinter($deviceNo,$deviceName = '设备名称')
    {
        $reqTime = $this->getMillisecond();
        //安全校验码 md5(memberCode+reqTime+apiKey+deviceNo)
        $securityCode = md5($this->memberCode.$reqTime.$this->apiKey.$deviceNo);
//        $securityCode = md5($memberCode.$reqTime.$apiKey.$deviceNo);
        $url = $this->host.':'.$this->port.'/apisc/adddev';
        $content['memberCode'] = $this->memberCode;
        $content['reqTime'] = $reqTime;
        $content['securityCode'] = $securityCode;
        $content['deviceID'] = $deviceNo;
        $content['devName'] = $deviceName;
        $content['grpID'] = ''; //分组ID
        $content['mPhone'] = '';
        $content['nPhone'] = '';
        $content['cutting'] = '';
        $res = request_post($url, $content);
        $code = json_decode($res,true);
        if($code['code'] != 1) {
            $this->error = $code['msg'];
            return false;
        } else return true;
    }

    public function delPrinter($deviceNo)
    {
        $reqTime = $this->getMillisecond();
        $securityCode = md5($this->memberCode.$reqTime.$this->apiKey.$deviceNo);
        $url = $this->host.':'.$this->port.'/apisc/deldev';
        $content['memberCode'] = $this->memberCode;
        $content['reqTime'] = $reqTime;
        $content['securityCode'] = $securityCode;
        $content['deviceID'] = $deviceNo;
        $res = request_post($url, $content);
        $code = json_decode($res,true);
        if($code['code'] != 1) {
            $this->error = $code['msg'];
            return false;
        } else return true;
    }

    // 查询打印机信息
    public function printerStatus($deviceNo)
    {
        $reqTime = $this->getMillisecond();
//        $securityCode = md5($this->memberCode.$reqTime.$this->apiKey.$deviceNo);
//        $url = $this->host.":".$this->port."/apisc/device";
//        $content['memberCode'] = $this->memberCode;
//        $content['reqTime'] = $reqTime;
//        $content['securityCode'] = $securityCode;
//        $content['deviceID'] = $deviceNo;
//        $res = request_post($url, $content);
//        return json_decode($res,true);
        $securityCode = md5($this->memberCode.$reqTime.$this->apiKey);
        $url = $this->host.':'.$this->port.'/apisc/getStatus';
        $content['memberCode'] = $this->memberCode;
        $content['reqTime'] = $reqTime;
        $content['securityCode'] = $securityCode;
        $content['deviceID'] = $deviceNo;
        $res = request_post($url, $content);
        $code = json_decode($res,true);
        $info = [];
        if($code['code'] != 1) {
            $info['查询结果'] = $code['msg'];
        } else {
            $s = [
                0 => '上线',
                1 => '正常',
                2 => '缺纸',
                3 => '其他异常',
                4 => '过热',
                5 => '开盖',
                8 => '暂停',
                9 => '打印中',
            ];
            $info['查询结果'] = $code['msg'];
            $info['在线状态'] = $code['statusList'][0]['online'] == 1 ? "在线" : "离线";
            $info['终端状态'] = $s[$code['statusList'][0]['status']];
            $info['上次离线时间'] = $code['statusList'][0]['outtime'];
            $info['打印数量'] = $code['statusList'][0]['printnum'];
        }
        return $info;
    }

    private function getMillisecond() {
        list($t1, $t2) = explode(' ', microtime());
        return (float)sprintf('%.0f',(floatval($t1)+floatval($t2))*1000);
    }
    // 所有打印状态
    public function printerStatusAll()
    {
        //安全校验码 md5(memberCode+reqTime+apiKey)
        $reqTime = $this->getMillisecond();
        $securityCode = md5($this->memberCode.$reqTime.$this->apiKey);
        $url = $this->host.':'.$this->port.'/apisc/listDevice';
        $content['memberCode'] = $this->memberCode;
        $content['reqTime'] = $reqTime;
        $content['securityCode'] = $securityCode;
        $res = request_post($url, $content);
        $code = json_decode($res,true);
        if($code['code'] != 1) {
            $this->error = $code['msg'];
            return [];
        } else {
            return $code['deviceList'];
        }
    }
}