<?php
/**
 * Created by PhpStorm.
 * User: jianguoxu
 * Date: 2018/11/4
 * Time: 17:20
 */

namespace Vod\Model;


use TencentCloud\Vod\V20180717\Models\ApplyUploadRequest;

class VodUploadRequest extends ApplyUploadRequest
{
    public $MediaFilePath;

    public $CoverFilePath;

    public $ConcurrentUploadNumber;

    public function __construct($mediaFilePath = '', $coverFilePath = '', $concurrentUploadNumber = 1)
    {
        $this->MediaFilePath = $mediaFilePath;
        $this->CoverFilePath = $coverFilePath;
        $this->ConcurrentUploadNumber = $concurrentUploadNumber;
    }
}