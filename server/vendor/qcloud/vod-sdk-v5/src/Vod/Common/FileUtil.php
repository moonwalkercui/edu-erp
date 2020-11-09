<?php
/**
 * Created by PhpStorm.
 * User: jianguoxu
 * Date: 2018/11/4
 * Time: 19:18
 */

namespace Vod\Common;

/**
 * 文件工具类
 *
 * Class FileUtil
 * @package Vod\Common
 */
class FileUtil
{
    /**
     * 获取文件类型
     *
     * @param $filePath
     * @return mixed|string
     */
    public static function getFileType($filePath)
    {
        if (empty($filePath)) {
            return '';
        }
        $tmp = explode("/", $filePath);
        $fullFileName = end($tmp);
        if (strrpos($fullFileName, ".") === false) {
            return '';
        }
        $pathArr = explode(".", $filePath);
        return end($pathArr);
    }

    /**
     * 获取文件名(不包含后缀)
     *
     * @param $filePath
     * @return bool|mixed|string
     */
    public static function getFileName($filePath)
    {
        if (empty($filePath)) {
            return '';
        }
        $tmp = explode("/", $filePath);
        $fullFileName = end($tmp);
        $pos = strrpos($fullFileName, ".");
        if ($pos === false) {
            return $fullFileName;
        }
        return substr($fullFileName, 0, $pos);
    }
}