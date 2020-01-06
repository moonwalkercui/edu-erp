<?php
/**
 * 阿里短信
 */
namespace App\Service\Sms;
use AlibabaCloud\Client\AlibabaCloud;
use AlibabaCloud\Client\Exception\ClientException;
use AlibabaCloud\Client\Exception\ServerException;

class AliSms
{
    static $accessKeyId = '1111'; // 阿里云重要信息 todo
    static $accessSecret = '1111';

    public static function send($mobile, $code)
    {
        AlibabaCloud::accessKeyClient(self::$accessKeyId, self::$accessSecret)
            ->regionId('cn-hangzhou') // replace regionId as you need
            ->asDefaultClient();
        try {
            $result = AlibabaCloud::rpc()
                ->product('Dysmsapi')
                // ->scheme('https') // https | http
                ->version('2017-05-25')
                ->action('SendSms')
                ->method('POST')
                ->options([
                    'query' => [
                        'RegionId' => "cn-hangzhou",
                        'PhoneNumbers' => $mobile,
                        'SignName' => "小猿管家",
//                        'TemplateCode' => "SMS_166867558",
                        'TemplateCode' => "SMS_167396708",
                        'TemplateParam' => "{\"code\":\"{$code}\"}",
                    ],
                ])->request();
            print_r($result->toArray());die;
            $res = $result->toArray();
            if($res['Code'] != 'OK') {
                return [
                    'code' => 0,
                    'msg' => $res['Message'],
                ];
            } else {
                return [
                    'code' => 1,
                    'msg' => '发送成功',
                ];
            }
// {
//  "Message": "OK",
//	"RequestId": "C6962887-A612-4AD0-A5EA-80ECD0D8AC26",
//	"BizId": "409315759525146973^0",
//	"Code": "OK"
// }
        } catch (ClientException $e) {
            return [
                'code' => 0,
                'msg' => $e->getErrorMessage(),
            ];
        } catch (ServerException $e) {
            return [
                'code' => 0,
                'msg' => $e->getErrorMessage(),
            ];
        }

    }

}