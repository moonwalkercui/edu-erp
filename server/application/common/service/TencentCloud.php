<?php
namespace app\common\service;

use TencentCloud\Common\Credential;
use TencentCloud\Common\Profile\ClientProfile;
use TencentCloud\Common\Profile\HttpProfile;
use TencentCloud\Common\Exception\TencentCloudSDKException;
use TencentCloud\Vod\V20180717\Models\DeleteMediaRequest;
use TencentCloud\Vod\V20180717\VodClient;
use TencentCloud\Vod\V20180717\Models\SearchMediaRequest;

// 腾讯云服务
class TencentCloud
{
    const CACHE_VOD_LIST = "vod_list_cache";
    private static $config = [
        'secret_id' => '111',
        'secret_key' => '222',
        'sub_appid' => 1500001788 // 腾讯云子应用id
    ];
    static function signature()
    {
        // 确定 App 的云 API 密钥
        $secret_id = self::$config['secret_id'];
        $secret_key = self::$config['secret_key'];

        // 确定签名的当前时间和失效时间
        $current = time();
        $expired = $current + 86400;  // 签名有效期：1天

        // 向参数列表填入参数
        $arg_list = array(
            "secretId" => $secret_id,
            "currentTimeStamp" => $current,
            "expireTime" => $expired,
            "random" => rand(),
            "vodSubAppId" => self::$config['sub_appid'] // 子应用id
        );

        // 计算签名
        $original = http_build_query($arg_list);
        $signature = base64_encode(hash_hmac('SHA1', $original, $secret_key, true).$original);

        return $signature;
//        echo $signature;
//        echo "\n";
    }

    // 点播列表
    static function vodList($page = 1, $per_page = 10)
    {
        try {
//            $cache = cache(TencentCloud::CACHE_VOD_LIST);
//            if($cache)
//                return $cache;
            $cred = new Credential(self::$config['secret_id'], self::$config['secret_key']);
            $httpProfile = new HttpProfile();
            $httpProfile->setEndpoint("vod.tencentcloudapi.com");
            $clientProfile = new ClientProfile();
            $clientProfile->setHttpProfile($httpProfile);
            $client = new VodClient($cred, "", $clientProfile);
            $req = new SearchMediaRequest();
            $params = [
                'Offset' => ($page - 1 ) * $per_page,
                'Filters' => ["basicInfo"],
                'SubAppId' => self::$config['sub_appid']
            ];
            $req->fromJsonString(json_encode($params));
            $resp = $client->SearchMedia($req);
            $vod_list = $resp->toJsonString();
            cache(TencentCloud::CACHE_VOD_LIST, $vod_list, 60);
            return $vod_list;
        }
        catch(TencentCloudSDKException $e) {
            exception($e->getMessage());
        }
    }

    // 删除点播视频
    static function vodDelMedia($file_id)
    {
        try {

            $cred = new Credential(self::$config['secret_id'], self::$config['secret_key']);
            $httpProfile = new HttpProfile();
            $httpProfile->setEndpoint("vod.tencentcloudapi.com");

            $clientProfile = new ClientProfile();
            $clientProfile->setHttpProfile($httpProfile);
            $client = new VodClient($cred, "", $clientProfile);

            $req = new DeleteMediaRequest();

            $params = array(
                'FileId' => $file_id,
                'SubAppId' => self::$config['sub_appid']
            );
            $req->fromJsonString(json_encode($params));
            return $client->DeleteMedia($req);
//            print_r($resp->toJsonString());
            /*
             * {
              "Response": {
                "Error": {
                  "Code": "ResourceNotFound",
                  "Message": "file not exist!"
                },
                "RequestId": "cd5afd6a-e1f5-45a9-b239-32ae4e020a79"
              }
            }*/
        }
        catch(TencentCloudSDKException $e) {
            exception($e->getMessage());
        }
    }
}