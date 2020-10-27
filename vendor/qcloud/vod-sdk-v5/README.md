## 简介

基于PHP语言平台的服务端上传的SDK，通过SDK和配合的Demo，可以将视频和封面文件直接上传到腾讯云点播系统，同时可以指定各项服务端上传的可选参数。

## 使用方式

### Composer
* 引入依赖

```json
{
    "require": {
        "qcloud/vod-sdk-v5": "v2.4.0"
    }
}
```
* 调用示例

```php
require 'vendor/autoload.php';

use Vod\VodUploadClient;
use Vod\Model\VodUploadRequest;

$client = new VodUploadClient("your secretId", "your secretKey");
$req = new VodUploadRequest();
$req->MediaFilePath = "the path to media file";
$req->CoverFilePath = "the path to cover file";
try {
    $rsp = $client->upload("ap-guangzhou", $req);
    echo "FileId => ". $rsp->FileId . "\n";
    echo "MediaUrl -> ". $rsp->MediaUrl . "\n";
    echo "CoverUrl -> ". $rsp->CoverUrl . "\n";
} catch (Exception $e) {
    // 处理上传异常
    echo $e;
}
```
上传成功后将获取文件的播放地址和 FileId

### 通过源码包安装

* 前往 [Github 代码托管地址](https://github.com/tencentyun/vod-php-sdk-v5/raw/master/packages/vod-sdk.zip) 下载源码压缩包并解压到项目当中
* 调用示例
```php
require 'vod-sdk-v5/autoload.php';

use Vod\VodUploadClient;
use Vod\Model\VodUploadRequest;

$client = new VodUploadClient("your secretId", "your secretKey");
$req = new VodUploadRequest();
$req->MediaFilePath = "the path to media file";
$req->CoverFilePath = "the path to cover file";
try {
    $rsp = $client->upload("ap-guangzhou", $req);
    echo "FileId => ". $rsp->FileId . "\n";
    echo "MediaUrl -> ". $rsp->MediaUrl . "\n";
    echo "CoverUrl -> ". $rsp->CoverUrl . "\n";
} catch (Exception $e) {
    // 处理上传异常
    echo $e;
}
```
上传成功后将获取文件的播放地址和 FileId
