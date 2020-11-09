<?php
namespace service;

use think\Exception;
use think\exception\ValidateException;
use think\facade\Env;
use think\Image;

class UploadService
{
    private static $imageValidate = ['size'=>2097152,'ext'=>'jpg,jpeg,png,gif','mime'=>'image/jpeg,image/gif,image/png'];
    private static $fileValidate = [
        'size'=>20 * 2048 * 2048,
        'ext'=>'jpg,jpeg,png,gif,mp3,mp4,wav,srt,lrc,pdf,zip,xls,xlsx,ppt,pptx,doc,docx',
        'mime'=>'image/jpeg,image/gif,image/png,audio/mp3,video/mp4,audio/wav,application/octet-stream,application/pdf,application/x-zip-compressed,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.presentationml.presentation'
    ];

    public static function uploadImage($input_name, $dir = 'uploads' )
    {
        $file = request()->file($input_name);

        if($file == null)
            return [
                'code' => 0,
                'msg' => '上传文件有误',
            ];
        $info = $file->validate(self::$imageValidate)->move($dir);
        if($info){
            $file_info = $info->getinfo();
            return [
                'code' => 1,
                'msg' => '已上传',
                'name' => $info->getFilename(),
                'size' => $file_info['size'],
                'ext' => $info->getExtension(),
                'path' => '/' . $dir . '/' . str_replace('\\','/',$info->getSaveName()),
            ];
//            dump($file_info);
//            dump($info->getExtension());
//            dump($info->getSaveName());
//            dump($info->getFilename());
//            dump([
//                $file_info['name'],
//                $file_info['size'],
//                $file_info['type'],
//                $dir,
//                '', // 压缩后的地址
//                $pid
//            ]);
        }else{
            // 上传失败获取错误信息
            return [
                'code' => 0,
                'msg' => $file->getError(),
            ];
        }
    }
    public static function uploadFile($input_name, $dir = 'uploads' )
    {
        $file = request()->file($input_name);
//        $temp_info = $file->getInfo(); halt($temp_info);
        if($file == null)
            return [
                'code' => 0,
                'msg' => '上传文件有误',
            ];
        $info = $file->validate(self::$fileValidate)->move($dir);

        if($info){
            $file_info = $info->getinfo();
            return [
                'code' => 1,
                'msg' => '已上传',
                'f_name' => $file->getInfo()['name'],
                'name' => $info->getFilename(),
                'size' => $file_info['size'],
                'ext' => $info->getExtension(),
                'path' => '/' . $dir . '/' . str_replace('\\','/',$info->getSaveName()),
            ];
        }else{
            return [
                'code' => 0,
                'msg' => $file->getError(),
            ];
        }
    }
    public static function uploadImageBase64($base64_image_content, $dir = 'uploads' ){
        if (preg_match('/^(data:\s*image\/(\w+);base64,)/', $base64_image_content, $result) == false)
            return [
                "code" => "0",
                "msg" => "文件内容有误",
            ];

        //图片后缀
        $type = $result[2];
        if($type=='jpeg') $type='jpg';
        //保存位置--图片名
        $image_name= date('ymdHis').str_pad(mt_rand(1, 99999), 5, '0', STR_PAD_LEFT).".".$type;
        $image_url = '/' . $dir . '/'.date('Ymd').'/'. $image_name;
        if(false ==FileService::mk_dir(dirname('.'.$image_url))){
            return [
                "code" => "0",
                "msg" => "创建文件夹失败",
            ];
        }
//        if(!is_dir(dirname('.'.$image_url))){
//            mkdir(dirname('.'.$image_url));
//            chmod(dirname('.'.$image_url), 0700);
//        }
        //解码
        $decode = base64_decode(str_replace($result[1], '', $base64_image_content));
        if (file_put_contents('.'.$image_url, $decode)){
            $img_len = strlen($base64_image_content);
            return [
                'code' => 1,
                'msg' => '已上传',
                'name' => $image_name,
                'size' => $img_len - ($img_len / 8) * 2,
                'ext' => $type,
                'path' => $image_url,
            ];
        }else{
            return [
                "code" => "0",
                "msg" => "上传失败",
            ];
        }
    }
    // 制作缩略图 $from_url 是public目录里的，没有.开头
    public static function makeImgThumb($from_url, $width=800, $height=800)
    {
        $from_url = ltrim($from_url,'.');
        $image = Image::open(Env::get('root_path') . 'public' .  $from_url);
//        echo '<br>'  . $from_url;
        $image->thumb($width,$height, Image::THUMB_CENTER)->save('.'. $from_url);
        $thumb_attr = explode('/', $from_url);
        $thumb_name = 't_' . array_pop($thumb_attr);
//        echo '<br>'  . $thumb_name;
//        var_dump($thumb_attr);
        $new_path = implode('/',$thumb_attr) . '/' . $thumb_name;
//        echo '<br>'  . $new_path;        die;
        $image->thumb(100, 100, Image::THUMB_CENTER)->save('.'. $new_path);
    }
}