<?php
namespace App\Service\Api;

use Illuminate\Support\Facades\Cache;

class Captcha
{
    private $width,$height,$codenum;
    public $checkcode;     //产生的验证码
    private $checkimage;    //验证码图片
    private $disturbColor = ''; //干扰像素

    function __construct($width='80',$height='20',$codenum='4')
    {
        $this->width=$width;
        $this->height=$height;
        $this->codenum=$codenum;
    }
    // $code是随机码
    function cacheCode($random_code, $check_code)
    {
        \Illuminate\Support\Facades\Cache::put('captcha_' . $random_code, $check_code, 1);
    }
    function validateCode($input_code, $random_code)
    {
        $cache = \Illuminate\Support\Facades\Cache::pull('captcha_' . $random_code);
//        \Illuminate\Support\Facades\Log::notice('Cache验证码：'.$random_code.'-'.$cache);
        return $input_code && $random_code && $cache && strtoupper($input_code) == $cache;
    }
    function outImg()
    {
        ob_clean();
        ob_start();
//        flush();
        //输出头
//        $this->outFileHeader();
        //产生验证码
        $this->createCode();
        //产生图片
        $this->createImage();
        //设置干扰像素
        $this->setDisturbColor();
        //往图片上写验证码
        $this->writeCheckCodeToImage();
        imagepng($this->checkimage);
        imagedestroy($this->checkimage);
        return ob_get_clean();
    }
//    private function outFileHeader(){
//        header("Content-type: image/png");
//        ob_clean();//清除缓存
//        flush();//清除缓存
//    }

    private function createCode()
    {
        $characters = '123456789acdefghjkmnprstvwxy';
        $randomString = '';
        for ($i = 0; $i < $this->codenum; $i++) {
            $randomString .= $characters[rand(0, strlen($characters) - 1)];
        }
        $this->checkcode = strtoupper($randomString);
//        $this->checkcode = strtoupper(substr(md5(rand()),0,$this->codenum));
    }

    private function createImage()
    {
        $this->checkimage = @imagecreate($this->width,$this->height);
        $back = imagecolorallocate($this->checkimage,255,255,255);
        $border = imagecolorallocate($this->checkimage,250,250,250);
        imagefilledrectangle($this->checkimage,0,0,$this->width - 1,$this->height - 1,$back); // 白色底
        imagerectangle($this->checkimage,0,0,$this->width - 1,$this->height - 1,$border);   // 黑色边框
    }

    private function setDisturbColor()
    {
        for ($i=0;$i<=200;$i++)
        {
            $this->disturbColor = imagecolorallocate($this->checkimage, rand(0,255), rand(0,255), rand(0,255));
            imagesetpixel($this->checkimage,rand(2,128),rand(2,38),$this->disturbColor);
        }
    }

    private function writeCheckCodeToImage()
    {
        for ($i=0; $i<$this->codenum; $i++)
        {
            $bg_color = imagecolorallocate ($this->checkimage, rand(0,255), rand(0,128), rand(0,255));
            $x = floor($this->width/$this->codenum) * $i;
            $y = rand(0,$this->height-15);
            imagechar($this->checkimage, rand(5,8), $x, $y, $this->checkcode[$i], $bg_color);
        }
    }
    function __destruct()
    {
        unset($this->width,$this->height,$this->codenum);
    }
}