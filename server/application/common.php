<?php

function api_exception()
{

}

function is_debug()
{
    return config('app_debug');
}

// 加密函数
function md500($str)
{
    return md5(sha1($str));
}

function now($only_date = false)
{
    return $only_date ? date('Y/m/d') : date('Y/m/d H:i:s');
}

function friendly_date($datetime)
{
    $time_format = ' H:i';
    $timeStamp = strtotime($datetime);
    $date_format = date('Y', $timeStamp) == date('Y', time()) ? "m/d" : "y/m/d";
    $time = $time_format ? date($time_format, $timeStamp) : '';
    if (date('Ymd', time()) == date('Ymd', $timeStamp)) {
        $text = '今天' . $time;
    } elseif (date('Ymd', strtotime('yesterday')) == date('Ymd', $timeStamp)) {
        $text = '昨天' . $time;
    } elseif (date('Ymd', strtotime('tomorrow')) == date('Ymd', $timeStamp)) {
        $text = '明天' . $time;
    } else {
        $text = date($date_format, $timeStamp) . $time;
    }
    return $text;
}

function friendly_date2($time)
{
    $t = time() - $time;
    $f = array(
        '31536000' => '年',
        '2592000' => '个月',
        '604800' => '星期',
        '86400' => '天',
        '3600' => '小时',
        '60' => '分钟',
        '1' => '秒'
    );
    foreach ($f as $k => $v) {
        if (0 != $c = floor($t / (int)$k)) {
            return $c . $v . '前';
        }
    }
}

/**
 * 字节转换
 */
function get_byte_size($size)
{
    $units = array(' B', ' KB', ' MB', ' GB', ' TB');
    for ($i = 0; $size > 1024; $i++) {
        $size /= 1024;
    }
    return round($size, 2) . $units[$i];
}

/**
 * textarea转换为html
 * @author TechLee
 */
function text_to_html($content, $type = '1')
{
    $str = '';
    switch ($type) {
        case '2':
            $str = '<p>' . str_replace(PHP_EOL, '</p><p>', $content) . '</p>';
            break;
        case '1':
        default:
            $str = str_replace(PHP_EOL, '<br>', $content);
            break;
    }
    return $str;
}

/**
 * xml对象转换为数组
 * @author TechLee
 */
function xml_to_array($xmlStr)
{
    $xmlObj = simplexml_load_string($xmlStr, 'SimpleXMLElement', LIBXML_NOCDATA);
    return json_decode(json_encode($xmlObj), true);
}

/**
 * 清除html标签
 * @author TechLee
 */
function clear_tags($str)
{
    $str = strip_tags($str);
    //首先去掉头尾空格
    $str = trim($str);
    $str = preg_replace("/(\s|\&nbsp\;|　|\xc2\xa0)/", "", strip_tags($str));
    //接着去掉两个空格以上的
    $str = preg_replace('/\s(?=\s)/', '', $str);
    //最后将非空格替换为一个空格
    $str = preg_replace('/[\n\r\t]/', ' ', $str);
    return $str;
}

/**
 * 清除html标签，字符串截取
 * 支持中文
 * @author TechLee
 */
function substr_cn($str, $length = 0, $start = 0, $charset = "utf-8", $suffix = true)
{
    $str = clear_tags($str);
    if (function_exists("mb_substr")) {
        if ($length > 0 && mb_strlen($str, $charset) <= $length) {
            return $str;
        }
        $slice = $length > 0 ? mb_substr($str, $start, $length, $charset) : $str;
    } else {
        $re['utf-8'] = "/[\x01-]|[�-�][�-�]|[�-�][�-�]{2}|[�-�][�-�]{3}/";
        $re['gb2312'] = "/[\x01-]|[�-�][�-�]/";
        $re['gbk'] = "/[\x01-]|[�-�][@-�]/";
        $re['big5'] = "/[\x01-]|[�-�]([@-~]|�-�])/";
        preg_match_all($re[$charset], $str, $match);
        if ($length > 0 && count($match[0]) <= $length) {
            return $str;
        }
        $slice = join("", $length > 0 ? array_slice($match[0], $start, $length) : $match[0]);
    }
    if ($suffix) {
        return $slice . "…";
    }
    return $slice;
}

function sub_strs($str, $length = 0, $start = 0)
{
    if ($length > 0 && strlen($str) <= $length) return $str;
    else
        return substr($str, 0, $length) . "...";
}

/**
 * openssl加密
 */
function do_encrypt($input)
{
    return base64_encode(openssl_encrypt(serialize($input), 'DES-ECB', '@19810618!ryan', OPENSSL_RAW_DATA));
}

/**
 * openssl解密
 */
function do_decrypt($code)
{
    return unserialize(openssl_decrypt(base64_decode($code), 'DES-ECB', '@19810618!ryan', OPENSSL_RAW_DATA));
}

function check_crypt_hash($hash, $value)
{
    return $value && do_decrypt($hash) != $value;
}

/**
 * 无限极分类
 * 将数组转换为树形结构
 * @author TechLee
 */
function list_to_tree($dataList, $parent_id = 0, $depth = 0, $prefix = '|--')
{
    $resTree = [];
    foreach ($dataList as $key => $val) {
        if ($val['parent_id'] == $parent_id) {
            $val['depth'] = $depth;
            $val['prefix'] = str_repeat($prefix, $depth);
            unset($dataList[$key]);
            $resTree[] = $val;
            $resTree = array_merge($resTree, list_to_tree($dataList, $val['id'], $depth + 1));
        }
    }
    return $resTree;
}


function list_to_html($list, $html = "└─ ", $pid = 0, $level = 0, $pid_name = "parent_id")
{
    $arr = [];
    foreach ($list as $v) {
        if ($v[$pid_name] == $pid) {
            $v['_level'] = $level + 1;
            $v['_html'] = str_repeat($html, $level);
            $arr[] = $v;
            $arr = array_merge($arr, list_to_html($list, $html, $v['id'], $level + 1, $pid_name = "parent_id"));
        }
    }
    return $arr;
}

/**
 * 把返回的数据集转换成子父类 整合数组，把子级整合到父级的_child里，形成树结构多维数组
 * @param $list
 * @param string $pk
 * @param string $pid_name
 * @param string $child_name
 * @return array
 */
function list_to_child($list, $pid = 0, $pid_name = "parent_id", $child_name = "_child")
{
    method_exists($list, 'toArray') and $list = $list->toArray();
    $arr = [];
    foreach ($list as $v) {
        if ($v[$pid_name] == $pid) {
            $v[$child_name] = list_to_child($list, $v['id'], $pid_name);
            $arr[] = $v;
        }
    }
    return $arr;
}

// 从数组里，递归查找上级分类，直到根分类
function list_parent_path($list, $cid, $pid_name = "parent_id")
{
    $arr = [];
    foreach ($list as $v) {
        if ($v['id'] == $cid) {
            $arr[] = $v;
            $arr = array_merge($arr, list_parent_path($list, $v[$pid_name]));
        }
    }
    return $arr;
}

/**
 * Curl请求，支持https，get，post，及非80,443端口。
 */
function url_request($data, $url = '', $method = 'post', $port = '80', $charset = 'utf-8')
{
    if (strstr($url, 'https://')) {
        $port = '443';
    }
    $form_data = "";
    if (is_array($data)) {
        foreach ($data as $key => $value) {
            if ($form_data == "") {
                $form_data = $key . "=" . rawurlencode($value);
            } else {
                $t = "&" . $key . '=' . rawurlencode($value);
                $form_data = $form_data . $t;
            }
        }
    } else {
        $form_data = $data;
    }

    $ch = curl_init();
    if (strtolower($charset) == 'gbk') {
        $this_header = array("content-type: application/x-www-form-urlencoded;charset=gbk");
        curl_setopt($ch, CURLOPT_HTTPHEADER, $this_header);
    }
    curl_setopt($ch, CURLOPT_PORT, $port);
    curl_setopt($ch, CURLOPT_USERAGENT, $_SERVER['HTTP_USER_AGENT']);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_HEADER, false); //设定是否输出页面内容
    if ($port == '443') {
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0); // 对认证证书来源的检查
        curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 1); // 从证书中检查SSL加密算法是否存在
    }
    if (strtolower($method) == 'post') {
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $form_data);
    } else {
        $url .= strstr($url, '?') ? '&' . $form_data : "?" . $form_data;
    }
    // echo $url;die;
    curl_setopt($ch, CURLOPT_URL, $url);
    // curl_setopt_array($ch, $option);
    $result = curl_exec($ch);
    // dump($result);
    return $result;
}

// $_FILES发生错误时返回错误信息。
function get_upfile_err($filename)
{
    if (!isset($_FILES[$filename])) return "上传格式错误！";
    switch ($_FILES[$filename]['error']) {
        case '0':
            return 0;
            break; //"没有错误发生，文件上传成功。 "; break;
        case '1':
            return "上传的文件过大！";
            break;//上传的文件超过了 PHP.ini 中 upload_max_filesize 选项限制的值。
        case '2':
            return "上传文件的大小超过了HTML表单中MAX_FILE_SIZE选项指定的值。 ";
            break;
        case '3':
            return "文件只有部分被上传。 ";
            break;
        case '4':
            return "没有文件被上传。 ";
            break;
        case '6':
            return "找不到临时文件夹。";
            break;
        case '7':
            return "文件写入失败。";
            break;
        default:
            return "未知错误。";
            break;
    }
}

/**
 * 模拟post进行url请求
 * @param string $url
 * @param array $post_data
 */
function request_post($url = '', $post_data = array())
{
    if (empty($url) || empty($post_data)) {
        return false;
    }
    $postUrl = $url;
    $curlPost = $post_data;
    $ch = curl_init();//初始化curl
    curl_setopt($ch, CURLOPT_URL, $postUrl);//抓取指定网页
    curl_setopt($ch, CURLOPT_HEADER, 0);//设置header
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);//要求结果为字符串且输出到屏幕上
    curl_setopt($ch, CURLOPT_POST, 1);//post提交方式
    curl_setopt($ch, CURLOPT_POSTFIELDS, $curlPost);
    $data = curl_exec($ch);//运行curl
    curl_close($ch);
    return $data;
}

function request_get($url = '', $params = [])
{
    if (empty($url)) return false;
    $param_str = http_build_query($params);
    $url = strpos($url, '?') ? $url . '&' . $param_str : $url . '?' . $param_str;
//    $header = [ 'Accept: application/json' ];
    $curl = curl_init();
    //设置抓取的url
    curl_setopt($curl, CURLOPT_URL, $url);
    //设置头文件的信息作为数据流输出
    curl_setopt($curl, CURLOPT_HEADER, 0);
    // 超时设置,以秒为单位
    curl_setopt($curl, CURLOPT_TIMEOUT, 1);
    // 设置请求头
//    curl_setopt($curl, CURLOPT_HTTPHEADER, $header);
    //设置获取的信息以文件流的形式返回，而不是直接输出。
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, false);
    $data = curl_exec($curl);

    if (curl_error($curl)) {
        print "Error: " . curl_error($curl);
    } else {
        curl_close($curl);
    }
    return $data;
}

// 给结果集排序
function sort_data_list($data, $field_name, $sort_type = 'asc')
{
    if ($sort_type == 'asc') {
        array_multisort(array_column($data, $field_name), SORT_ASC, $data);
    } else {
        array_multisort(array_column($data, $field_name), SORT_DESC, $data);
    }
    return $data;
}

function add_image_prefix($value)
{
    $value = str_replace('\\', '/', $value);
    if (strpos($value, '/') > 0) {
        $value = '/' . $value;
    }
    if ($value == null || strpos($value, 'http') !== false)
        $domain = '';
    else
        $domain = SCHEME_DOMAIN;
    return $domain .  $value;
}

function remove_image_prefix($value)
{
    if ($value == false) return "";
    return false !== strpos($value, MAIN_DOMAIN) ? substr($value, strpos($value, MAIN_DOMAIN) + strlen(MAIN_DOMAIN)) : $value;
}

//数字 转字母
function decimal2ABC($num)
{
    $num *= 618;
    $ABCstr = "";
    $ten = $num;
    if ($ten == 0) return "A";
    while ($ten != 0) {
        $x = $ten % 26;
        $ABCstr .= chr(65 + $x);
        $ten = intval($ten / 26);
    }
    return strrev($ABCstr);
}

//字母（26）进制转10进制
function ABC2decimal($abc)
{
    $abc = strtoupper($abc); // 防止小写
    $ten = 0;
    $len = strlen($abc);
    for ($i = 1; $i <= $len; $i++) {
        $char = substr($abc, 0 - $i, 1);
        $int = ord($char);
        $ten += ($int - 65) * pow(26, $i - 1);
    }
    return $ten / 618;
}

// 获取坐标的hash值  https://www.jianshu.com/p/02d12efc77ea  保留位数误差分别为： 4: 20km, 5: 2.4km  6: 0.61km 7: 0.076km
function get_geohash($lat, $lng)
{
    $geohash = new \GeoHash\GeoHash();
    $geohash->setLatitude($lat);
    $geohash->setLongitude($lng);
    return $geohash->getHash();
//    return substr($geohash, 0, 4);
}

// 计算2个坐标之间的距离 默认是返回公里 true返回 英里
function distance($lat1, $lng1, $lat2, $lng2, $miles = false)
{
    $pi80 = M_PI / 180;
    $lat1 *= $pi80;
    $lng1 *= $pi80;
    $lat2 *= $pi80;
    $lng2 *= $pi80;
    $r = 6372.797; // mean radius of Earth in km
    $dlat = $lat2 - $lat1;
    $dlng = $lng2 - $lng1;
    $a = sin($dlat / 2) * sin($dlat / 2) + cos($lat1) * cos($lat2) * sin($dlng / 2) * sin($dlng / 2);
    $c = 2 * atan2(sqrt($a), sqrt(1 - $a));
    $km = $r * $c;
    return ($miles ? ($km * 0.621371192) : $km);
}

function mix_str($str, $start = 3, $end = -4)
{
    $temp = abs($start) + abs($end);
    $l = mb_strlen($str);
    $r = abs($l - $temp);
    $s = mb_substr($str, 0, $start);
    $e = mb_substr($str, $end);
    return $s . str_pad("", $r, "*") . $e;
}

function format_distance($km)
{
    if ($km >= 1) return number_format($km, 1) . '公里';
    else return intval($km * 1000) . '米';
}

function int_float_val($num)
{
    return intval($num) == $num ? intval($num) : $num;
}

/*
	 * 地图计算距离
	 *  $lat1:起点纬度
	 *  $lng1 : 起点经度
	 *  $lat2:终点纬度
	 *  $lng2 : 终点经度
	 * */
//function distance($lat1,$lng1,$lat2,$lng2){
//
//    // 将角度转为狐度
//    $radLat1 = deg2rad($lat1);// deg2rad()函数将角度转换为弧度
//    $radLat2 = deg2rad($lat2);
//    $radLng1 = deg2rad($lng1);
//    $radLng2 = deg2rad($lng2);
//
//    $a = $radLat1 - $radLat2;
//    $b = $radLng1 - $radLng2;
//
//    $s = 2 * asin(sqrt(pow(sin($a / 2), 2)+cos($radLat1) * cos($radLat2) * pow(sin($b / 2), 2))) * 6378.137;
//
//    return $s;
//}

// 生成唯一码
function make_sn()
{
    $code = "ABCDEFGHIGKLMNOPQRSTUVWXYZ";

    $rand = $code[rand(0, 25)]
        . strtoupper(dechex(date('m')))
        . date('d')
        . substr(time(), -6)
        . substr(microtime(), 2, 5)
        . sprintf('%02d', rand(0, 99));
    for (
        $a = md5($rand, true),
        $s = 'ABCDEFGHIJKLMNOPQRSTUV0123456789',
        $d = '',
        $f = 0;
        $f < 8;
        $g = ord($a[$f]), // ord（）函数获取首字母的 的 ASCII值
        $d .= $s[($g ^ ord($a[$f + 8])) - $g & 0x1F], //按位亦或，按位与。
        $f++
    ) ;
    return $d;
}

// 生成唯一订单号
function build_order_no($uid)
{
    return substr(implode(NULL, array_map('ord', str_split(substr(uniqid(), 7, 13), 1))), 0, 8) . $uid;
}

// 通过状态值获得状态名
function get_status_text($status_val, $status_name = '')
{
    if ($status_name) {
        $conf = config('status.' . $status_name);
        if (empty($conf)) return '未定义状态';
        if (isset($conf[$status_val])) $text = $conf[$status_val];
        else $text = $status_val;
        return $status_val === null ? "" : $text;
    } else {
        return $status_val == 1 ? '正常' : "已禁用";
    }
}

// 通过状态名获得状态键
function get_status_val($status_text, $status_name)
{
    $conf = config('status.' . $status_name);
    foreach ($conf as $k => $v) {
        if ($v === $status_text) {
            return $k;
        }
    }
    return null;
}

function get_client_ip($type = 0)
{
    $type = $type ? 1 : 0;
    static $ip = NULL;
    if ($ip !== NULL) return $ip[$type];
    if (isset($_SERVER['HTTP_X_REAL_IP'])) { //nginx 代理模式下，获取客户端真实IP
        $ip = $_SERVER['HTTP_X_REAL_IP'];
    } elseif (isset($_SERVER['HTTP_CLIENT_IP'])) { //客户端的ip
        $ip = $_SERVER['HTTP_CLIENT_IP'];
    } elseif (isset($_SERVER['HTTP_X_FORWARDED_FOR'])) { //浏览当前页面的用户计算机的网关
        $arr = explode(',', $_SERVER['HTTP_X_FORWARDED_FOR']);
        $pos = array_search('unknown', $arr);
        if (false !== $pos) unset($arr[$pos]);
        $ip = trim($arr[0]);
    } elseif (isset($_SERVER['REMOTE_ADDR'])) {
        $ip = $_SERVER['REMOTE_ADDR']; //浏览当前页面的用户计算机的ip地址
    } else {
        $ip = $_SERVER['REMOTE_ADDR'];
    }
    // IP地址合法验证
    $long = sprintf("%u", ip2long($ip));
    $ip = $long ? array($ip, $long) : array('0.0.0.0', 0);
    return $ip[$type];
}


// 判断是否是手机登录
// $check_wx : 判断是否在微信浏览器里
function is_mob($check_wx = false)
{
    static $res;
    if (isset($res)) {
        return $res;
    }
    if (empty($_SERVER['HTTP_USER_AGENT'])) {
        $res = false;
    } elseif ($check_wx && strpos($_SERVER['HTTP_USER_AGENT'], 'MicroMessenger') !== false) {
        $res = true;
    } elseif (
        strpos($_SERVER['HTTP_USER_AGENT'], 'Mobile') !== false ||
        strpos($_SERVER['HTTP_USER_AGENT'], 'Android') !== false ||
        strpos($_SERVER['HTTP_USER_AGENT'], 'Silk/') !== false ||
        strpos($_SERVER['HTTP_USER_AGENT'], 'Kindle') !== false ||
        strpos($_SERVER['HTTP_USER_AGENT'], 'BlackBerry') !== false ||
        strpos($_SERVER['HTTP_USER_AGENT'], 'Opera Mini') !== false ||
        strpos($_SERVER['HTTP_USER_AGENT'], 'Opera Mobi') !== false
    ) {
        $res = true;
    } else {
        $res = false;
    }
    return $res;
}

function selectDataFromModel($model_name, $where, $limit, $order)
{
    $model = new $model_name();
    return $model->where($where)->limit($limit)->order($order)->select();
}

function findDataFromModel($model_name, $where)
{
    $model = new $model_name();
    return $model->where($where)->find();
}

// 转换123为ABC
function exam_transfer_label($value)
{
    return chr(64 + $value);
}

// 以下用户选择题的存储
// 输入BD返回10 字母顺序无所谓
function exam_store_answer_chr($chr)
{
    if (intval($chr) > 0) return 0;
    return array_sum(array_map(function ($i) {
        return pow(2, ord($i) - 65);
    }, str_split(strtoupper($chr))));
}

// 输入10返回BD
function exam_fetch_answer_chr($num)
{
    $c = 0;
    $res = "";
    while ($num > 0) {
        if ($num & 1 == 1) {
            $res .= chr(65 + $c);
        }
        $num = $num >> 1;
        $c++;
    }
    return $res;
}

function fmt_money($value)
{
    return '<small>￥</small>' . (floor($value) == $value ? intval($value) : $value);
}

function datetime_friendly($time_str, $show_time = true)
{
    $ts = strtotime($time_str);

    if (time() - $ts < 1800) return "刚刚";

    switch (date('Y-m-d', $ts)) {
        case date('Y-m-d', strtotime('today')):
            $date = '今天';
            break;
        case date('Y-m-d', strtotime('+2 days')):
            $date = '后天';
            break;
        case date('Y-m-d', strtotime('+1 day')):
            $date = '明天';
            break;
        case date('Y-m-d', strtotime('-1 day')):
            $date = '昨天';
            break;
        case date('Y-m-d', strtotime('-2 days')):
            $date = '前天';
            break;
        default:
            if (date('Y', $ts) === date('Y')) $date = date('m月d日', $ts);
            else  $date = date('Y年m月d日', $ts);
            break;
    }
    return $date . ($show_time ? ' ' . date('H:i', $ts) : '');
}

function number_friendly($num)
{
    if ($num < 100) {
        $res = $num;
    } elseif ($num < 1000) {
        $res = intval($num / 100) . "00+";
    } else {
        $res = intval($num / 1000) . "k+";
    }
    return $res;
}

function sys_config($key, $default = '')
{
    $value = \app\common\model\Config::getValue($key);
    return $value ?: $default;
}

function show_empty_notice($list)
{
    if ((is_array($list) && empty($list)) || ($list instanceof \think\Paginator && $list->count() == 0)) {
        echo '<div style="padding: 10px; text-align: center; width: 100%;font-size:0.9rem; color:gray">暂无记录！</div>';
    }
    if ($list instanceof \think\Paginator && $list->lastPage() == 1 && $list->lastPage() == $list->currentPage()) {
        echo '<div style="padding: 10px; text-align: center; width: 100%;font-size:0.9rem; color:gray">没有更多内容</div>';
    }
}

/**
 * 字符串截取，支持中文和其他编码
 * @static
 * @access public
 * @param string $str 需要转换的字符串
 * @param string $start 开始位置
 * @param string $length 截取长度
 * @param string $charset 编码格式
 * @param string $suffix 截断显示字符
 * @return string
 */
function msubstr($str, $start = 0, $length, $charset = "utf-8", $suffix = true)
{
    if (function_exists("mb_substr"))
        $slice = mb_substr($str, $start, $length, $charset);
    elseif (function_exists('iconv_substr')) {
        $slice = iconv_substr($str, $start, $length, $charset);
        if (false === $slice) {
            $slice = '';
        }
    } else {
        $re['utf-8'] = "/[\x01-\x7f]|[\xc2-\xdf][\x80-\xbf]|[\xe0-\xef][\x80-\xbf]{2}|[\xf0-\xff][\x80-\xbf]{3}/";
        $re['gb2312'] = "/[\x01-\x7f]|[\xb0-\xf7][\xa0-\xfe]/";
        $re['gbk'] = "/[\x01-\x7f]|[\x81-\xfe][\x40-\xfe]/";
        $re['big5'] = "/[\x01-\x7f]|[\x81-\xfe]([\x40-\x7e]|\xa1-\xfe])/";
        preg_match_all($re[$charset], $str, $match);
        $slice = join("", array_slice($match[0], $start, $length));
    }
    $fix = '';
    if (strlen($slice) < strlen($str)) {
        $fix = '...';
    }
    return $suffix ? $slice . $fix : $slice;
}

// 输入过滤
//function input_filter($input)
//{
//    return htmlspecialchars(trim($input), ENT_QUOTES);
//}
function input_str($post_name, $default = '')
{
    return input($post_name, $default, 'input_filter_str');
}

function input_int($post_name, $default = 0)
{
    return input($post_name, $default, 'intval');
}

function input_float($post_name, $default = 0)
{
    return input($post_name, $default, 'floatval');
}

function input_filter($str)
{
    $str = trim($str);
    if (!$str) return '';
    if (!get_magic_quotes_gpc()) {
        $str = addslashes($str);
    }
//    $str = str_replace("_", "\_", $str);
//    $str = str_replace("#", "\#", $str);
    $str = str_replace("%", "\%", $str);
    $str = str_replace("script", "", $str);
    $str = str_replace("eval", "", $str);
    $str = str_replace("phpinfo", "", $str);
    $str = str_replace("databases", "", $str);
    $str = preg_replace("/(insert|update|delete|show|declare|execute|drop|alter|char|union|into|load_file|outfile)/i", "_$1", $str);
    $str = nl2br($str);
    $str = htmlspecialchars($str, ENT_QUOTES);
    return $str;
}

// 根据文件格式获取文件图标
function get_file_icon($ext)
{
    switch ($ext) {
        case 'png':
        case 'jpg':
        case 'git':
        case 'jpeg':
        case 'bmp':
            $icon = 'img';
            break;
        case 'pdf':
            $icon = 'pdf';
            break;
        case 'ppt':
        case 'pptx':
            $icon = 'ppt';
            break;
        case 'doc':
        case 'docx':
            $icon = 'doc';
            break;
        case 'xls':
        case 'xlsx':
            $icon = 'xls';
            break;
        case 'txt':
            $icon = 'txt';
            break;
        case 'zip':
        case 'rar':
        case '7z':
            $icon = 'zip';
            break;
        case 'mp4':
            $icon = 'mp4';
            break;
        case 'mp3':
            $icon = 'mp3';
            break;
        default:
            $icon = 'word';
            break;
    }
    return '/static/fileext/' . $icon . '.png';
}

function get_attachment_file_icon($uri)
{
    $att = \app\common\model\Attachment::getAttByUri($uri);
    if (!$att) return '';
    return get_file_icon($att->att_type);
}

// 时间秒转换成字符 5 秒转成 00:00:05
function parse_duration_str($a)
{
    $b = "";
    $h = intval($a / 3600);
    $m = intval($a % 3600 / 60);
    $s = intval($a % 3600 % 60);
    if ($h > 0) {
        $h = $h < 10 ? '0' . $h : $h;
        $b .= $h . ":";
    }
    $m = $m < 10 ? '0' . $m : $m;
    $s = $s < 10 ? '0' . $s : $s;
    $b .= $m . ":" . $s;
    return $b;
}

// 时长转换成秒 00:00:05  秒转 5
function parse_duration_sec($d)
{
    $arr = explode(':', $d);
    $count = count($arr);
    if ($count > 3) {
        return null;
    }
    if ($count == 3) {
        $sec = intval($arr[0]) * 3600 + intval($arr[1]) * 60 + intval($arr[2]);
    } elseif ($count == 2) {
        $sec = intval($arr[0]) * 60 + intval($arr[1]);
    } else {
        $sec = intval($d);
    }
    return $sec;
}

/**
 * 将url中字符串参数变为数组
 */
function url_query2array($url)
{
    $arr = explode('?', $url);
    if (count($arr) == 1) return $url;
    $query = $arr[1];
    $queryParts = explode('&', $query);
    $params = [];
    foreach ($queryParts as $param) {
        $item = explode('=', $param);
        $params[$item[0]] = $item[1];
    }
    return [$arr[0], $params];
}

function download_file($file_path, $download_name)
{
    if (!is_file($file_path) || !is_readable($file_path)) {
        exit('无法访问该文件:' . $download_name);
    } else {
        $fileHandle = fopen($file_path, "rb");
        if ($fileHandle === false) {
            exit("无法打开该文件: $download_name");
        }
        header('Content-type:application/octet-stream; charset=utf-8');
        header("Content-Transfer-Encoding: binary");
        header("Accept-Ranges: bytes");
        Header("Content-Length: " . filesize($file_path));
        header('Content-Disposition:attachment;filename="' . urlencode($download_name) . '"');
        while (!feof($fileHandle)) {
            echo fread($fileHandle, 102400);
        }
        fclose($fileHandle);
        exit();
    }
}

/**
 * 邮件发送函数 ()
 */
function sendMail($to_email, $title, $content, $to_name = "", $remark = "提醒")
{
    $conf = [
        'email_host' => 'smtp.qq.com',
        'email_username' => '541720500@qq.com',
        'email_password' => 'lxjufzjcjdkzbchh',
        'email_from' => '541720500@qq.com',
        'email_fromname' => '律师树',
    ];
    $mail = new PHPMailer\PHPMailer\PHPMailer(true);
    try {
        $mail->isSMTP();                                            // Set mailer to use SMTP
        $mail->SMTPAuth = true;                                   // Enable SMTP authentication
        $mail->Host = $conf['email_host'];  // Specify main and backup SMTP servers
        $mail->Username = $conf['email_username'];                     // SMTP username
        $mail->Password = $conf['email_password'];                               // SMTP password
        $mail->SMTPSecure = 'ssl';                                  // Enable TLS encryption, `ssl` also accepted
        $mail->Port = 465;                                    // TCP port to connect to
        $mail->CharSet = 'UTF-8';
        $mail->setFrom($conf['email_from'], $conf['email_fromname']);
        $mail->addAddress($to_email, $to_name);     // Add a recipient
        $mail->isHTML(true);                                  // Set email format to HTML
        $mail->Subject = $title;
        $mail->Body = $content;
        $mail->AltBody = $remark;
        $mail->send();
    } catch (Exception $e) {
        exception("Message could not be sent. Mailer Error: {$mail->ErrorInfo}");
    }
}


/**
 * Passport 加密函数  与 think_encrypt 相比，这个函数每次加密后的字符串是变化的，没有失效时间。
 * @param string          等待加密的原字串
 * @param string          私有密匙(用于解密和加密)
 * @return       string          原字串经过私有密匙加密后的结果
 */
function passport_encrypt($txt, $key = '19810618')
{
    $txt = strval($txt);
    // 使用随机数发生器产生 0~32000 的值并 MD5()
    srand((double)microtime() * 1000000);
    $encrypt_key = md5(rand(0, 32000));
    // 变量初始化
    $ctr = 0;
    $tmp = '';
    // for 循环，$i 为从 0 开始，到小于 $txt 字串长度的整数
    for ($i = 0; $i < strlen($txt); $i++) {
        // 如果 $ctr = $encrypt_key 的长度，则 $ctr 清零
        $ctr = $ctr == strlen($encrypt_key) ? 0 : $ctr;
        // $tmp 字串在末尾增加两位，其第一位内容为 $encrypt_key 的第 $ctr 位，
        // 第二位内容为 $txt 的第 $i 位与 $encrypt_key 的 $ctr 位取异或。然后 $ctr = $ctr + 1
        $tmp .= $encrypt_key[$ctr] . ($txt[$i] ^ $encrypt_key[$ctr++]);
    }
    // 返回结果，结果为 passport_key() 函数返回值的 base64 编码结果
    return base64_encode(passport_key($tmp, $key));
}

/**
 * Passport 解密函数
 * @param string          加密后的字串
 * @param string          私有密匙(用于解密和加密)
 * @return       string          字串经过私有密匙解密后的结果
 */
function passport_decrypt($txt, $key = '19810618')
{
    // $txt 的结果为加密后的字串经过 base64 解码，然后与私有密匙一起，
    // 经过 passport_key() 函数处理后的返回值
    $txt = passport_key(base64_decode($txt), $key);
    // 变量初始化
    $tmp = '';
    // for 循环，$i 为从 0 开始，到小于 $txt 字串长度的整数
    for ($i = 0; $i < strlen($txt); $i++) {
        // $tmp 字串在末尾增加一位，其内容为 $txt 的第 $i 位，
        // 与 $txt 的第 $i + 1 位取异或。然后 $i = $i + 1
        $tmp .= $txt[$i] ^ $txt[++$i];
    }
    // 返回 $tmp 的值作为结果
    return $tmp;
}

/**
 * Passport 密匙处理函数
 * @param string          待加密或待解密的字串
 * @param string          私有密匙(用于解密和加密)
 * @return       string          处理后的密匙
 */
function passport_key($txt, $encrypt_key)
{
    // 将 $encrypt_key 赋为 $encrypt_key 经 md5() 后的值
    $encrypt_key = md5($encrypt_key);
    // 变量初始化
    $ctr = 0;
    $tmp = '';
    // for 循环，$i 为从 0 开始，到小于 $txt 字串长度的整数
    for ($i = 0; $i < strlen($txt); $i++) {
        // 如果 $ctr = $encrypt_key 的长度，则 $ctr 清零
        $ctr = $ctr == strlen($encrypt_key) ? 0 : $ctr;
        // $tmp 字串在末尾增加一位，其内容为 $txt 的第 $i 位，
        // 与 $encrypt_key 的第 $ctr + 1 位取异或。然后 $ctr = $ctr + 1
        $tmp .= $txt[$i] ^ $encrypt_key[$ctr++];
    }
    // 返回 $tmp 的值作为结果
    return $tmp;
}


/**
 * 短信发送（手机号、发送内容utf-8）
 * 使用示例，多个手机号以英文逗号分隔，短信内容UTF-8，可自行转码
 * 切记：发送频率限制规则，必须自行填写，规则如下：
 * 1、12小时内不得超过300
 * 2、单个手机号12小时不得超过20;
 * 3、单个手机号300秒不得超过3条
 * 4、单个手机号或单个ip300秒不得超过3条
 * $rs = sendsms('13800000000,13688888888','短信内容');
 */
function sendMobileSms($mob, $content)
{
    $config = array(
        'user' => config('conf_indb.sms_account'), //短信用户名
        'pass' => config('conf_indb.sms_pw'), //短信密码
        'note' => config('conf_indb.sms_sign'), //签名，例如：【资海科技】
    );
//    dump($config);die;

    $uid = $config['user'];
    $pwd = $config['pass'];
    $content .= '【' . $config['note'] . '】';
    //发送频率次数限制 >> 开始
    $log_db = db('sms_log');
    $user_ip = request()->ip();
    // 切记：发送频率限制规则，必须自行填写，规则如下：

    // 2、单个手机号1天不得超过100;
    $today_num = $log_db->where('mobile', $mob)->whereTime('add_time', 'd')->count();
    // 3、单个手机号300秒不得超过10条
    $mobile_num = $log_db->where('mobile', $mob)->where('add_time', 'between time', [time() - 300, time()])->count();
    // 4、单个ip300秒不得超过10条
    $ip_num = $log_db->where('ip', $user_ip)->where('add_time', 'between time', [time() - 300, time()])->count();
    if ($today_num >= 100 || $mobile_num >= 10 || $ip_num >= 10) return false;
    //发送频率次数限制 >> 结束
    // 日志
    @$log_db->insert([
        'mobile' => $mob,
        'add_time' => time(),
        'content' => $content,
        'ip' => request()->ip(),
    ]);

    $sms_type = '1'; // 1|建周短信,2|吉信通,3|维那多,4|创瑞通讯平台,5|互亿无线,6|短信宝
    $send_id = '';
    $status = 0;
    switch ($sms_type) {
        //建周短信
        case '1':
            //建周短信使用分号隔开多个手机号
            $mob = str_replace(',', ';', $mob);
            $postData = array(
                'account' => $uid,
                'password' => $pwd,
                'destmobile' => $mob,
                'msgText' => $content,
            );
            $rs = url_request($postData, 'http://www.jianzhou.sh.cn/JianzhouSMSWSServer/http/sendBatchMessage');
            $send_id = $rs;
            $status = $rs > 0 ? 1 : 2;
            break;
        //吉信通接口
        case '2':
            $sendurl = "http://service.winic.org:8009/sys_port/gateway/?id=" . $uid . "&pwd=" . $pwd . "&to=" . $mob . "&content=" . $content . "&time=";
            $rs = file_get_contents($sendurl);
            $data = explode("/", $rs);
            $status = $data[0] == "000" ? 2 : 1;
            break;
        // 维那多
        case '3':
            $sendurl = "http://yl.mobsms.net/send/gsend.aspx?name=" . $uid . "&pwd=" . $pwd . "&dst=" . $mob . "&msg=" . $content;
            $rs = file_get_contents($sendurl);
            // $rs      = auto_charset($rs, "gbk", "utf-8");
            parse_str($rs, $rsArr);
            $status = @$rsArr['num'] <= 0 ? 2 : 1;
            break;
        // 创瑞通讯平台cr6868
        case '4':
            $flag = 0;
            $params = ''; //要post的数据
            $argv = array(
                'name' => $uid, //必填参数。用户账号
                'pwd' => $pwd, //必填参数。（web平台：基本资料中的接口密码）
                'content' => $content, //必填参数。发送内容（1-500 个汉字）UTF-8编码
                'mobile' => $mob, //必填参数。手机号码。多个以英文逗号隔开
                'stime' => '', //可选参数。发送时间，填写时已填写的时间发送，不填时为当前时间发送
                'sign' => '', //必填参数。用户签名。
                'type' => 'pt', //必填参数。固定值 pt
                'extno' => '', //可选参数，扩展码，用户定义扩展码，只能为数字
            );
            foreach ($argv as $key => $value) {
                if ($flag != 0) {
                    $params .= "&";
                    $flag = 1;
                }
                $params .= $key . "=";
                $params .= urlencode($value); // urlencode($value);
                $flag = 1;
            }
            $url = "http://web.cr6868.com/asmx/smsservice.aspx?" . $params; //提交的url地址
            $con = substr(file_get_contents($url), 0, 1); //获取信息发送后的状态
            $status = $con != '0' ? 2 : 1;
            break;
        // 互亿无线 ihuyi
        case '5':
            $url = 'http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=' . $uid . '&pwd=' . strtoupper(md5($uid . $pwd)) . '&mobile=' . $mob . '&content=' . $content . '&ext=&stime=&rrid=&msgfmt=';
            $rs = file_get_contents($url);
            $status = $rs > 0 ? 1 : 2;
            break;
        //最好的短信平台-短信宝官网
        case '6':
            $url = 'http://api.smsbao.com/sms?u=' . $uid . '&p=' . md5($pwd) . '&m=' . $mob . '&c=' . $content;
            $rs = file_get_contents($url);
            $status = $rs != '0' ? 2 : 1;
            break;
        default:
            $status = 0;
            break;
    }
    return $status == 1 ? true : false;
}

function makeQRcode($data, $size = 4, $need_save = false, $file_name = '', $level = 'L')
{
    include env('extend_path') . "phpqrcode/phpqrcode.php";
    // 如果需要生成图片，则返回保存路径
    if ($need_save === true) {
        if ($file_name == '') $file_name = 'qrcode/' . time() . '.png';
        \QRcode::png($data, $file_name, $level, $size);
        return $file_name;
    } else {
        // 否则返回base64的图片编码
        ob_start();
        \QRcode::png($data, false, $level, $size);
        $imageString = base64_encode(ob_get_contents());
        ob_end_clean();
        return 'data:image/png;base64,' . $imageString;
        // eturn "<img src='data:image/png;base64,{$imageString}'>";
    }
}

// 仅支持英文和数字
//function starReplace($str){
//    $len = strlen($str);
//    if($len < 3) {
//        $star = str_repeat("*", $len-1);
//        return substr_replace($str, $star, 1);
//    } elseif ($len >= 3 && $len < 6){
//        $star = str_repeat("*", $len-2);
//        return substr_replace($str, $star, 1 ,-1);
//    } elseif ($len >= 6 && $len < 11){
//        $star = str_repeat("*", $len-4);
//        return substr_replace($str, $star, 2 ,-2);
//    }elseif ($len >= 11){
//        $star = str_repeat("*", $len-7);
//        return substr_replace($str, $star, 3 ,-4);
//    }
//}
//
//function hidecard($cardnum, $type = 1, $default = "")
//{
//    if (empty($cardnum)) {
//        return $default;
//    }
//    if ($type == 1) {
//        $cardnum = substr($cardnum, 0, 3) . str_repeat("*", 12) . substr($cardnum, strlen($cardnum) - 4);
//    } else if ($type == 2) {
//        $cardnum = substr($cardnum, 0, 3) . str_repeat("*", 5) . substr($cardnum, strlen($cardnum) - 4);
//    } else if ($type == 3) {
//        $cardnum = str_repeat("*", strlen($cardnum) - 4) . substr($cardnum, strlen($cardnum) - 4);
//    } else if ($type == 4) {
//        $cardnum = substr($cardnum, 0, 3) . str_repeat("*", strlen($cardnum) - 3);
//    } else if ($type == 5) {
//        $cardnum = mb_substr($cardnum, 0, 1, 'utf-8') . str_repeat("*", 2) . mb_substr($cardnum, -1, 1, 'utf-8');
//    }
//    //$cardnum=iconv("UTF-8", "GBK", $cardnum);
//    return $cardnum;
//}
// 支持中文星号替换
function starReplace($str, $default = '')
{
    if (empty($str)) return $default;
    $has_chinese = false; // 判断是否有中文
    if (preg_match("/[\x7f-\xff]/", $str)) $has_chinese = true;
    $len = $has_chinese ? utf8_strlen($str) : strlen($str);
    if ($len == 1) return "*";
    elseif ($len == 2) return $has_chinese ? mb_substr($str, 0, 1, 'utf-8') . "*" : substr($str, 0, 1) . "*";
    elseif ($len >= 3 && $len < 6) {
        $begin = $end = 1;
    } elseif ($len >= 6 && $len < 8) {
        $begin = $end = 2;
    } elseif ($len >= 8 && $len < 11) {
        $begin = $end = 3;
    } elseif ($len >= 11 && $len < 18) {
        // 手机号显示前3位后4位
        $begin = 3;
        $end = 4;
    } elseif ($len >= 18) {
        // 身份证显示前4位后6位
        $begin = 4;
        $end = 6;
    }
    return $has_chinese ? mb_substr($str, 0, $begin, 'utf-8') . str_repeat("*", $len - $begin - $end) . mb_substr($str, -$end, $end, 'utf-8')
        : substr($str, 0, $begin) . str_repeat("*", $len - $begin - $end) . substr($str, -$end);
}

//判断字符长度
function utf8_strlen($string = null)
{
    preg_match_all("/./us", $string, $match);
    return count($match[0]);
}

// 评价五星评价
function fiveStar($num, $red_star, $gray_star)
{
    $red = str_repeat("<img src='" . $red_star . "'>", $num);
    $gray = str_repeat("<img src='" . $gray_star . "'>", 5 - $num);
    return $red . $gray;
}

function isMob()
{
    if (preg_match('/(blackberry|configuration\/cldc|hp |hp-|htc |htc_|htc-|iemobile|kindle|midp|mmp|motorola|mobile|nokia|opera mini|opera |Googlebot-Mobile|YahooSeeker\/M1A1-R2D2|android|iphone|ipod|mobi|palm|palmos|pocket|portalmmm|ppc;|smartphone|sonyericsson|sqh|spv|symbian|treo|up.browser|up.link|vodafone|windows ce|xda |xda_)/i', $_SERVER['HTTP_USER_AGENT']))
        return true;
    else
        return false;
}

/**
 * 下载远程文件
 * @param string $url 网址
 * @param string $filename 保存文件名
 * @param integer $timeout 过期时间
 * return boolean|string
 */
function http_down($url, $filename, $timeout = 60)
{
    $path = dirname($filename);
    if (!is_dir($path) && !mkdir($path, 0755, true)) {
        return false;
    }
    $fp = fopen($filename, 'w');
    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_FILE, $fp);
    curl_setopt($ch, CURLOPT_TIMEOUT, $timeout);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);
    curl_exec($ch);
    curl_close($ch);
    fclose($fp);
    return $filename;
}

// 微信数据转化
function arrayToXml($arr)
{
    $xml = "<xml>";
    foreach ($arr as $key => $val) {
        if (is_numeric($val)) {
            $xml .= "<" . $key . ">" . $val . "</" . $key . ">";
        } else
            $xml .= "<" . $key . "><![CDATA[" . $val . "]]></" . $key . ">";
    }
    $xml .= "</xml>";
    return $xml;
}

function xmlToArray($xml)
{
    $array_data = json_decode(json_encode(simplexml_load_string($xml, 'SimpleXMLElement', LIBXML_NOCDATA)), true);
    return $array_data;
}

// 获取坐标的hash值  https://www.jianshu.com/p/02d12efc77ea  保留位数误差分别为： 4: 20km, 5: 2.4km  6: 0.61km 7: 0.076km
function getGeoHash($lat, $lng)
{
    $geohash = new \GeoHash\GeoHash();
    $geohash->setLatitude($lat);
    $geohash->setLongitude($lng);
    return $geohash->getHash();
}

/**
 * 数组转xls格式的excel文件
 * @param array $data 需要生成excel文件的数组
 * @param string $filename 生成的excel文件名
 *      示例数据：
 * $data = array(
 * array(NULL, 2010, 2011, 2012),
 * array('Q1',   12,   15,   21),
 * array('Q2',   56,   73,   86),
 * array('Q3',   52,   61,   69),
 * array('Q4',   30,   32,    0),
 * );
 * @param array $width 每列宽度数组
 */

function export_xls($data, $filename = 'simple', $width = [])
{
    ini_set('max_execution_time', '0');
    $filename .= date('Ymdhis') . '.xls';
    require \think\facade\Env::get('root_path') . 'extend/PHPExcel/PHPExcel.php';
    $phpexcel = new \PHPExcel();
    $phpexcel->getProperties()
        ->setCreator("Maarten Balliauw")
        ->setLastModifiedBy("Maarten Balliauw")
        ->setTitle("Office 2007 XLSX Test Document")
        ->setSubject("Office 2007 XLSX Test Document")
        ->setDescription("Test document for Office 2007 XLSX, generated using PHP classes.")
        ->setKeywords("office 2007 openxml php")
        ->setCategory("Test result file");
    $phpexcel->getActiveSheet()->fromArray($data);
    $phpexcel->getActiveSheet()->setTitle('Sheet1');
    $phpexcel->setActiveSheetIndex(0);
    if (!empty($width)) {
        $a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for ($i = 0; $i < count($width); $i++) {
            $phpexcel->getActiveSheet()->getColumnDimension("{$a[$i]}")->setWidth(intval($width[$i]));
        }
    }
    ob_end_clean();
    header('Content-Type: application/vnd.ms-excel');
    header("Content-Disposition: attachment;filename=$filename");
    header('Cache-Control: max-age=0');
    header('Cache-Control: max-age=1');
    header('Expires: Mon, 26 Jul 1997 05:00:00 GMT'); // Date in the past
    header('Last-Modified: ' . gmdate('D, d M Y H:i:s') . ' GMT'); // always modified
    header('Cache-Control: cache, must-revalidate'); // HTTP/1.1
    header('Pragma: public'); // HTTP/1.0
    $objwriter = PHPExcel_IOFactory::createWriter($phpexcel, 'Excel5');
    $objwriter->save('php://output');
    exit;
}

/**
 * 导入excel文件
 * @param string $file excel文件路径
 * @return array        excel文件内容数组
 */
function import_xls($file)
{
    // 判断文件是什么格式
    $type = pathinfo($file);
    $type = strtolower($type["extension"]);
    if ($type === 'xls') $type = 'Excel5';
    elseif ($type === 'xlsx') $type = 'Excel2007';
    elseif ($type === 'csv') $type = 'csv';
    else return false;
//    $type=$type==='csv' ? $type : 'Excel5';
    ini_set('max_execution_time', '0');
    // Vendor('PHPExcel.PHPExcel');
    \think\Loader::import('PHPExcel.PHPExcel', EXTEND_PATH);
    // 判断使用哪种格式
    $objReader = PHPExcel_IOFactory::createReader($type);
    $objPHPExcel = $objReader->load($file);
    $sheet = $objPHPExcel->getSheet(0);
    // 取得总行数
    $highestRow = $sheet->getHighestRow();
    // 取得总列数
    $highestColumn = $sheet->getHighestColumn();
    //循环读取excel文件,读取一条,插入一条
    $data = array();
    //从第一行开始读取数据
    for ($j = 1; $j <= $highestRow; $j++) {
        //从A列读取数据
        for ($k = 'A'; $k <= $highestColumn; $k++) {
            // 读取单元格
            $data[$j][] = $objPHPExcel->getActiveSheet()->getCell("$k$j")->getValue();
        }
    }
    return $data;
}

/*
 * 导出excel（结果集，字段和表头的对应关系）
 * $field_map 格式为 [
 *         'name' => '名称',
 *         'add_time|formatDate=Ymd' => '添加时间',
 *          ....
 * ]
 * */
function C_exportExcel($arr, $field_map, $width_arr = [])
{
    $fields = array_keys($field_map);
    // 拼接键
    $func = $arg = $tmp_data = [];
    foreach ($fields as $k => $v) {
        $cb1 = explode('|', $v);
        if (count($cb1) > 1) {
            $result_keys[] = $cb1[0];
            $cb2 = explode('=', $cb1[1]);
            if (count($cb2) > 1) {
                $func[$cb1[0]] = $cb2[0];
                $arg[$cb1[0]] = $cb2[1];
            } else {
                $func[$cb1[0]] = $cb2[0];
                $arg[$cb1[0]] = null;
            }
        } else {
            $result_keys[] = $v;
        }
    }

    // 拼接表头名称
    $field_title = array_values($field_map);
    $temp_key = [];
    foreach ($arr as &$d) {
//        $d = array_values($d);
        foreach ($d as $k => &$v) {

            if (in_array($k, $result_keys)) {
                array_push($temp_key, array_search($k, $result_keys));
                if (isset($func[$k]) && isset($arg[$k])) {
                    $v = $func[$k]($v, $arg[$k]);
                } elseif (isset($func[$k]) && isset($arg[$k]) == false) {
                    $v = $func[$k]($v);
                }
            } else {
                unset($d[$k]);
            }
        }
        array_multisort($temp_key, SORT_ASC, $d);
        $temp_key = [];
    }
    array_unshift($arr, $field_title);


    return export_xls($arr, '', $width_arr);
}


/*
 * 精度计算 需bcmath扩展
 * $m和$n代表传入的两个数值，主要就是这两个数值之间的比较
 * $x代表传入的方法，比如+ - * / 等
 * $scale 代表传入的小数点位数。这个根据需求更改即可
 */
function calculate($m, $n, $x, $scale = 2)
{
    $errors = ['被除数不能为零', '负数没有平方根'];
    switch ($x) {
        case '+':
            $t = bcadd($m, $n, $scale);
            break;
        case '-':
            $t = bcsub($m, $n, $scale);
            break;
        case '*':
            $t = bcmul($m, $n);
            break;
        case '/':
            if ($n != 0) {
                $t = bcdiv($m, $n);
            } else {
                return $errors[0];
            }
            break;
        case 'pow':
            $t = bcpow($m, $n);
            break;
        case 'mod':
            if ($n != 0) {
                $t = bcmod($m, $n);
            } else {
                return $errors[0];
            }
            break;
        case 'sqrt':
            if ($m >= 0) {
                $t = bcsqrt($m);
            } else {
                return $errors[1];
            }
            break;
    }
    return $t;
}

function whiteLog($content, $file_prefix = 'log_')
{
    $path = env('root_path') . 'paylog/';
    if (!file_exists($path)) mkdir($path);
    $file_name = $file_prefix . date('Y-m-d') . ".txt";
    file_put_contents(
        $path . $file_name,
        '[' . date("Y/m/d H:i:s") . '] ' . request()->ip() . ' -> ' . request()->method() . ' ' . request()->path() . PHP_EOL . $content . PHP_EOL,
        FILE_APPEND);
}

function emailAdmin($title, $content = "", $to_name = "", $remark = "提醒")
{
    sendMail("541720500@qq.com", $title, $content, $to_name, $remark);
}

function brReplace($str)
{
    echo html_entity_decode((str_replace(["\r\n", "\n"], "<br />", $str)));
}

// 判断是否有权限
function hasPermission($node_id)
{
    $nodes = session('permission_ids');
    return in_array($node_id, $nodes);
}

/**
 * 获取$time当周所有日期 返回的key为1-7（周一到周日）
 */
function getWeekDays($time = '', $format = 'Y-m-d')
{
    $time = $time != '' ? $time : time();
    $date = [];
    $W = date('W', $time);
    $Y = date('Y', $time);
    $start = strtotime($Y . 'W' . $W);
    $end = strtotime('+ 6days', $start);
    $next_date = $start;
    $i = 1;
    while ($next_date <= $end) {
        $date[$i] = date($format, $next_date);
        $next_date = strtotime('+1 day', $next_date);
        $i++;
    }
    return $date;
}


/**
 * 获取一个月内的日期数组
 */
function getMonthDays($format = 'Y-m-d')
{
    $start = strtotime('-1month');;
    $end = time();
    $date = [];
    while ($start <= $end) {
        $date[] = date($format, $start);
        $start = strtotime('+1 day', $start);
    }
    array_pop($date); // 删除今天的
    return $date;
}

function weekdayText(int $w)
{
    switch ($w) {
        case 1:
            $text = '一';
            break;
        case 2:
            $text = '二';
            break;
        case 3:
            $text = '三';
            break;
        case 4:
            $text = '四';
            break;
        case 5:
            $text = '五';
            break;
        case 6:
            $text = '六';
            break;
        default:
            $text = '日';
    }
    return '周' . $text;
}

// 获取配置
function db_config($key)
{
    return \app\common\model\Config::getValue($key);
}

// 模板函数

function get_nav_list($position)
{
    return \app\common\model\Nav::getList($position);
}

function get_nav_list_by_pid($pid)
{
    return \app\common\model\Nav::getListByPid($pid);
}

function get_article_by_nav($pid)
{
    return \app\common\model\Article::getOneByNav($pid);
}

function get_article($id)
{
    return \app\common\model\Article::find($id);
}

function get_news_list($limit)
{
    return \app\common\model\Article::getByNav(0, $limit);
}

function get_category_list()
{
    return [];
}

function get_course_list($limit = 6, $cate_id = null)
{
    return [];
}

//function getCover($file_id, $width = 100, $height = 100, $cut = true, $replace = false)
//{
//    $file_url = cut_auto($file_id, $width, $height);
//    return cut_fix($file_url, $width, $height, $type = 1, $pos = 5, $start_x = 0, $start_y = 0);
//}
function get_ad_list($position)
{
    return \app\common\model\Advertisement::getByPosition($position);
}

function get_teacher_list($limit = 100)
{
    return [];
//    return \app\common\model\Teacher::getList($limit);
}

function get_student_list($per_page)
{
    return \app\common\model\OldStudent::getList($per_page);
}

function get_audio_category($pid = null)
{
    if ($pid === null)
        $res = \app\common\model\AudioCategory::where('parent_id<>0')->order('sort_num desc')->select();
    else
        $res = \app\common\model\AudioCategory::where('parent_id', $pid)->order('sort_num desc')->select();
    return $res;
}

function getMediaInfo($file)
{
    require_once(env('extend_path') . "getid3/getid3.php");
    $getID3 = new \getID3;
    return $getID3->analyze($file);
}

// 通过远程地址获取mp3信息
function getMp3Info($remotefilename)
{
    // Copy remote file locally to scan with getID3()  https://github.com/JamesHeinrich/getID3/
//    $remotefilename = 'http://www.example.com/filename.mp3';
    $ThisFileInfo = null;
    if ($fp_remote = fopen($remotefilename, 'rb')) {
        $localtempfilename = tempnam('/tmp', 'getID3');
        if ($fp_local = fopen($localtempfilename, 'wb')) {
            while ($buffer = fread($fp_remote, 8192)) {
                fwrite($fp_local, $buffer);
            }
            fclose($fp_local);
            // Initialize getID3 engine
            require_once(env('extend_path') . "getid3/getid3.php");
            $getID3 = new \getID3;
            $ThisFileInfo = $getID3->analyze($localtempfilename);
            // Delete temporary file
            unlink($localtempfilename);
        }
        fclose($fp_remote);
    }
    return $ThisFileInfo;
}

// amr 转 mp3
function amrToMp3($amr_path, $mp3_path)
{
    if (file_exists($mp3_path) == true) {
        // exit('无需转换');
        return false;
    } else {
        $command = "ffmpeg -i $amr_path $mp3_path";
        exec($command);
    }
}