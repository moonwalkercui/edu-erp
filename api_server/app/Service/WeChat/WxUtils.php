<?php
namespace App\Service\WeChat;

use App\Exceptions\ApiException;
use Illuminate\Support\Facades\Storage;

class WxUtils extends MiniProgram
{
    // 参考 https://www.easywechat.com/docs/master/mini-program/app_code#app
//    // 生成组织的小程序码
//    public function qrcode()
//    {
//        $response = $this->program->app_code->getUnlimit('code.'. API_PREFIX , [
//            'path' => 'page/authorize',
//            'width' => 600,
//        ]);
//
//        $image_name = '/public/qrcode/' . API_PREFIX . '.png';
//        if ($response instanceof \EasyWeChat\Kernel\Http\StreamResponse) {
//            Storage::put($image_name, $response->getBodyContents());
//        }
//        return $response;
//    }
    // 生成组织的小程序码并返回url
    public function getQrcodeImg($uname)
    {
//        $this->qrcode();
//        $image_name = 'public/qrcode/' . API_PREFIX . '.png';
//        try{
//            $url = Storage::url($image_name);
//        } catch (\Exception $e) {
//            if($e instanceof \Illuminate\Contracts\Filesystem\FileNotFoundException) {
//                throw new ApiException('未找到文件');
//            }
//        }
//        return $url;
        $response = $this->program->app_code->getUnlimit('code.' . API_PREFIX. '.' . $uname, [
            'path' => 'pages/home',
            'width' => 600,
        ]);
        $image_name = 'upload/' . API_PREFIX . '/qr_'.API_PREFIX. '_' . $uname .'.png';
        if ($response instanceof \EasyWeChat\Kernel\Http\StreamResponse) {
            Storage::put('/public/'. $image_name, $response->getBodyContents());
        }
        $url = Storage::url($image_name);
        return API_DOMAIN_FULL . $url;
    }

    // 生成小程序端的分享二维码  $attachment 附加信息
    public function shareQrcode($member_id, $attachment = [])
    {
        $scene = 'share.' . API_PREFIX . '.' . $member_id;
        $image_name = 'upload/' . API_PREFIX . '/share_'. $member_id;
        if(isset($attachment['product_id']) && $attachment['product_id']) {
            $scene .= '.'. $attachment['product_id'];
            $image_name .= '_'. $attachment['product_id'];
        }
        $image_name .= '.png';

        $response = $this->program->app_code->getUnlimit($scene, [
            'path' => 'pages/home',
            'width' => 600,
        ]);
//        $path = 'shareqr/' . API_PREFIX;
//        if ($response instanceof \EasyWeChat\Kernel\Http\StreamResponse) {
//            $filename = $response->saveAs($path, 'share_' . $member_id.'.png');
//        }
//        return getTheDomain() . '/' . $path .'/'. $filename;
        if ($response instanceof \EasyWeChat\Kernel\Http\StreamResponse) {
            Storage::put('/public/'. $image_name, $response->getBodyContents());
        }
        $url = Storage::url($image_name);
//        return $url;
        return API_DOMAIN_FULL . $url;
    }

}