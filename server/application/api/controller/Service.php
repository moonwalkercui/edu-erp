<?php

namespace app\api\controller;

use app\common\controller\BaseController;


class Service extends BaseController
{
    function sendSmsCode()
    {
        return $this->smsCode();
    }

    function uploadImg()
    {
        $file = request()->file('image');
        $upload_dir = '/up';
        $act = input('act', null);
        $info = $file->move('.' . $upload_dir);
        if ($info) {
            $url = $upload_dir . '/' . $info->getSaveName();
            if ($act && $act == "avatar") {
                $image = \think\Image::open('.' . $url);
                $image->thumb(300, 300, \think\Image::THUMB_CENTER)->save('.' . $url);
            }
            return $this->dataJson([
                'url' => $url
            ]);
        } else {
            return $this->errorJson($file->getError());
        }
//            // 成功上传后 获取上传信息
//            // 输出 jpg
//            echo $info->getExtension();
//            echo '---';
//            // 输出 20160820/42a79759f284b767dfcb2a0197904287.jpg
//            echo $info->getSaveName();
//            echo '---';
//            // 输出 42a79759f284b767dfcb2a0197904287.jpg
//            echo $info->getFilename();

    }
    function editorUpImg()
    {
        $file = request()->file('file');
        $upload_dir = DIRECTORY_SEPARATOR . 'up';
        $info = $file->move('.' .  $upload_dir);
        if ($info) {
            $url = $upload_dir . DIRECTORY_SEPARATOR . $info->getSaveName();
            return json(['location' => add_image_prefix($url)]);
        } else {
            echo $file->getError();
        }
    }
    function editorUpMedia()
    {
        $file = request()->file('file');
        $upload_dir = DIRECTORY_SEPARATOR . 'up';
        $info = $file->move('.' .  $upload_dir);
        if ($info) {
            $url = $upload_dir . DIRECTORY_SEPARATOR . $info->getSaveName();
            return json(['location' => add_image_prefix($url) ]);
        } else {
            echo $file->getError();
        }
    }
}