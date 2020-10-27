<?php
/**
 * Created by PhpStorm.
 * User: jianguoxu
 * Date: 2018/11/4
 * Time: 16:39
 */

namespace Vod;
use Qcloud\Cos\Client;
use TencentCloud\Common\Credential;
use TencentCloud\Common\Exception\TencentCloudSDKException;
use TencentCloud\Vod\V20180717\Models\ApplyUploadRequest;
use TencentCloud\Vod\V20180717\Models\CommitUploadRequest;
use TencentCloud\Vod\V20180717\VodClient;
use Vod\Common\FileUtil;
use Vod\Exception\VodClientException as VodClientException;
use Vod\Model\VodUploadRequest;
use Vod\Model\VodUploadResponse;
use \DateTime;

error_reporting(E_ALL ^ E_NOTICE);

/**
 * 点播上传客户端
 *
 * Class VodUploadClient
 * @package Vod
 */
class VodUploadClient
{
    private $secretId;

    private $secretKey;

    private $ignoreCheck;

    private $retryTime;

    private $logPath;

    public function __construct($secretId, $secretKey)
    {
        $this->secretId = $secretId;
        $this->secretKey = $secretKey;
        $this->ignoreCheck = false;
        $this->retryTime = 3;
        $this->logPath = './vod_upload.log';
        date_default_timezone_set('PRC');
    }

    /**
     * 上传
     *
     * @param $region
     * @param $uploadRequest
     * @return VodUploadResponse
     */
    public function upload($region, $uploadRequest) {
        if (!$this->ignoreCheck) {
            $this->prefixCheckAndSetDefaultVal($region, $uploadRequest);
        }
        $credential = new Credential($this->secretId, $this->secretKey);
        $vodClient = new VodClient($credential, $region);

        $this->log("INFO", "Upload Request = ".$uploadRequest->toJsonString());
        $uploadRequestData = $uploadRequest->serialize();
        $applyUploadRequest = new ApplyUploadRequest();
        $applyUploadRequest->deserialize($uploadRequestData);
        $applyUploadResponse = $this->applyUpload($vodClient, $applyUploadRequest);
        $this->log("INFO", "ApplyUpload Response = ".$applyUploadResponse->toJsonString());

        $cosCredential = array();
        if (isset($applyUploadResponse->TempCertificate)) {
            $certificate = $applyUploadResponse->TempCertificate;
            $cosCredential['secretId'] = $certificate->SecretId;
            $cosCredential['secretKey'] = $certificate->SecretKey;
            $cosCredential['token'] = $certificate->Token;
        } else {
            $cosCredential['secretId'] = $this->secretId;
            $cosCredential['secretKey'] = $this->secretKey;
        }
        $cosClient = new Client(array(
            'region' => $applyUploadResponse->StorageRegion,
            'credentials' => $cosCredential
        ));

        if (!empty($uploadRequest->MediaType) && !empty($applyUploadResponse->MediaStoragePath)) {
            $this->uploadCos(
                $cosClient,
                $uploadRequest->MediaFilePath,
                $applyUploadResponse->StorageBucket,
                $applyUploadResponse->MediaStoragePath
            );
        }
        if (!empty($uploadRequest->CoverType) && !empty($applyUploadResponse->CoverStoragePath)) {
            $this->uploadCos(
                $cosClient,
                $uploadRequest->CoverFilePath,
                $applyUploadResponse->StorageBucket,
                $applyUploadResponse->CoverStoragePath
            );
        }

        $commitUploadRequest = new CommitUploadRequest();
        $commitUploadRequest->VodSessionKey = $applyUploadResponse->VodSessionKey;
        $commitUploadRequest->SubAppId = $uploadRequest->SubAppId;
        $commitUploadResponse = $this->commitUpload($vodClient, $commitUploadRequest);
        $this->log("INFO", "CommitUpload Response = ".$commitUploadResponse->toJsonString());

        $commitUploadResponseData = $commitUploadResponse->serialize();
        $uploadResponse = new VodUploadResponse();
        $uploadResponse->deserialize($commitUploadResponseData);

        return $uploadResponse;
    }

    private function uploadCos($cosClient, $localPath, $bucket, $cosPath) {
        $cosClient->Upload($bucket, $cosPath, fopen($localPath, 'rb'));
    }

    /**
     * 申请上传
     *
     * @param $vodClient
     * @param $uploadRequest
     * @return mixed
     * @throws TencentCloudSDKException
     */
    private function applyUpload($vodClient, $uploadRequest) {
        $err = null;
        for ($i = 0; $i < $this->retryTime; $i++) {
            try {
                $applyUploadResponse = $vodClient->ApplyUpload($uploadRequest);
                return $applyUploadResponse;
            } catch (TencentCloudSDKException $e) {
                if (empty($e->getRequestId())) {
                    $err = $e;
                    continue;
                }
                throw $e;
            }
        }
        throw $err;
    }

    /**
     * 确认上传
     *
     * @param $vodClient
     * @param $commitUploadRequest
     * @return mixed
     * @throws TencentCloudSDKException
     */
    private function commitUpload($vodClient, $commitUploadRequest) {
        $err = null;
        for ($i = 0; $i < $this->retryTime; $i++) {
            try {
                $commitUploadResponse = $vodClient->CommitUpload($commitUploadRequest);
                return $commitUploadResponse;
            } catch (TencentCloudSDKException $e) {
                if (empty($e->getRequestId())) {
                    $err = $e;
                    continue;
                }
                throw $e;
            }
        }
        throw $err;
    }

    /**
     * 前置检查及设置默认值
     *
     * @param $region
     * @param $uploadRequest
     * @throws VodClientException
     */
    private function prefixCheckAndSetDefaultVal($region, VodUploadRequest $uploadRequest) {
        if (empty($region)) {
            throw new VodClientException("lack region");
        }
        if (empty($uploadRequest->MediaFilePath)) {
            throw new VodClientException("lack media path");
        }
        if (!file_exists($uploadRequest->MediaFilePath)) {
            throw new VodClientException("media path is invalid");
        }
        if (empty($uploadRequest->MediaType)) {
            $mediaType = FileUtil::getFileType($uploadRequest->MediaFilePath);
            if (empty($mediaType)) {
                throw new VodClientException("lack media type");
            }
            $uploadRequest->MediaType = $mediaType;
        }
        if (empty($uploadRequest->MediaName)) {
            $uploadRequest->MediaName = FileUtil::getFileName($uploadRequest->MediaFilePath);
        }

        if (!empty($uploadRequest->CoverFilePath)) {
            if (!file_exists($uploadRequest->CoverFilePath)) {
                throw new VodClientException("cover path is invalid");
            }
            if (empty($uploadRequest->CoverType)) {
                $coverType = FileUtil::getFileType($uploadRequest->CoverFilePath);
                if (empty($coverType)) {
                    throw new VodClientException("lack cover type");
                }
                $uploadRequest->CoverType = $coverType;
            }
        }
    }

    private function log($level, $message) {
        $t = microtime(true);
        $micro = sprintf("%06d",($t - floor($t)) * 1000000);
        $d = new DateTime(date('Y-m-d H:i:s.' . $micro, $t));
        error_log('[' . $d->format("Y-m-d H:i:s.u") . '][' . $level . ']' . $message . "\n", 3, $this->logPath);
    }

    /**
     * @return mixed
     */
    public function getSecretId()
    {
        return $this->secretId;
    }

    /**
     * @param mixed $secretId
     */
    public function setSecretId($secretId)
    {
        $this->secretId = $secretId;
    }

    /**
     * @return mixed
     */
    public function getSecretKey()
    {
        return $this->secretKey;
    }

    /**
     * @param mixed $secretKey
     */
    public function setSecretKey($secretKey)
    {
        $this->secretKey = $secretKey;
    }

    /**
     * @return mixed
     */
    public function getIgnoreCheck()
    {
        return $this->ignoreCheck;
    }

    /**
     * @param mixed $ignoreCheck
     */
    public function setIgnoreCheck($ignoreCheck)
    {
        $this->ignoreCheck = $ignoreCheck;
    }

    /**
     * @return mixed
     */
    public function getRetryTime()
    {
        return $this->retryTime;
    }

    /**
     * @param mixed $retryTime
     */
    public function setRetryTime($retryTime)
    {
        $this->retryTime = $retryTime;
    }

    /**
     * @return string
     */
    public function getLogPath()
    {
        return $this->logPath;
    }

    /**
     * @param $logPath
     */
    public function setLogPath($logPath)
    {
        $this->logPath = $logPath;
    }
}