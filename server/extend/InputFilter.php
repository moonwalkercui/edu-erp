<?php
    //PHP整站防注入程序，需要在公共文件中require_once本文件
    //判断magic_quotes_gpc状态
    if (@get_magic_quotes_gpc()) {
        $_GET = sec($_GET);
        $_POST = sec($_POST);
        $_COOKIE = sec($_COOKIE);
        $_FILES = sec($_FILES);
    }
    $_SERVER = sec($_SERVER);
    function sec(&$array)
    {
        //如果是数组，遍历数组，递归调用
        if (is_array($array)) {
            foreach ($array as $k => $v) {
                $array [$k] = sec($v);
            }
        } elseif (is_string($array)) {
            //使用addslashes函数来处理
            $array = addslashes($array);
        } elseif (is_numeric($array)) {
            $array = intval($array);
        }
        return $array;
    }

    //整型过滤函数
    function num_check($id)
    {
        if (! $id) {
            die('参数不能为空！');
        } //是否为空的判断
        elseif (inject_check($id)) {
            die('非法参数');
        } //注入判断
        elseif (! is_numetic($id)) {
            die('非法参数');
        }
        //数字判断
        $id = intval($id);
        //整型化
        return $id;
    }
    //字符过滤函数
    function str_check($str)
    {
        if (inject_check($str)) {
            die('非法参数');
        }
        //注入判断
        $str = htmlspecialchars($str);
        //转换html
        return $str;
    }
    function search_check($str)
    {
        $str = str_replace("_", "_", $str);
        //把"_"过滤掉
        $str = str_replace("%", "%", $str);
        //把"%"过滤掉
        $str = htmlspecialchars($str);
        //转换html
        return $str;
    }
    //表单过滤函数
    function post_check($str, $min, $max)
    {
        if (isset($min) && strlen($str) < $min) {
            die('最少$min字节');
        } elseif (isset($max) && strlen($str) > $max) {
            die('最多$max字节');
        }
        return stripslashes_array($str);
    }
    //防注入函数
    function inject_check($sql_str)
    {
        $pattern = '/script|inert|update|delete|../|UNION|into|load_file|outfile/i';
        return preg_replace($pattern, '', $sql_str);
    }

    function stripslashes_array(&$array)
    {
        if (is_array($array)) {
            foreach ($array as $k => $v) {
                $array [$k] = stripslashes_array($v);
            }
        } elseif (is_string($array)) {
            $array = stripslashes($array);
        }
        return $array;
    }