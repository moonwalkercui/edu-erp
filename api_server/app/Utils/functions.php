<?php
/**
 * Created by Ray: 541720500@qq.com
 */

//if(isset($_SERVER['HTTP_HOST'])) {
//    $api_domain_prefix = substr($_SERVER['HTTP_HOST'], 0, strpos($_SERVER['HTTP_HOST'], '.'));
//    $sub_domain = $api_domain_prefix == 'vhuojia' ? '' : $api_domain_prefix;
//} else {
//    $sub_domain = 'api';
//}


function getResourceUrl(){
     // 使用了连接符号，所以去掉原来的获取资源方法。
//    return API_DOMAIN_FULL. '/resource?p=';
    return API_DOMAIN_FULL. '/storage';
}
/**
 * 无限分类函数，在子分类签插入符号,注意id字段必须用id或别名，父级id默认parent_id
 * @param array $list 要转换的数据集
 * @param array $html 分类名前面的横杠
 * @param number $pid 从父级为0的开始
 * @param number $level 层级
 * @param string $pid_name 父级id的字段名
 * @return 树状多维数组
 * @author Ray
 */
function unlimitedLevel($list,$html="- - ",$pid=0,$level=0,$pid_name = "parent_id"){
    $arr = array();
    foreach ($list as $v) {
        if($v[$pid_name] == $pid){
            $v['_level'] = $level+1;
            $v['_html'] = str_repeat($html, $level);
            $arr[] = $v;
            $arr = array_merge($arr,unlimitedLevel($list,$html,$v['id'],$level+1,$pid_name));
        }
    }
    return $arr;
}
//整合数组，把子级整合到父级的_child里，形成树结构多维数组
function unlimitedChild($list , $pid = 0 , $pid_name = "parent_id" , $child_name='_child'){
    $arr = [];
    foreach ($list as $v) {
        if($v[$pid_name] == $pid){
            $child = unlimitedChild($list , $v['id'] ,$pid_name, $child_name);
            if(empty($child) == false) $v[$child_name] = $child;
            $arr[] = $v;
        }
    }
    return $arr;
}

// 从数组里，递归查找上级分类，直到根分类
function unlimitedParents($list,$cid,$pid_name = "parent_id"){
    $arr = [];
    foreach ($list as $v) {
        if($v['id'] == $cid){
            $arr[] = $v;
            $arr = array_merge(unlimitedParents($list,$v[$pid_name]) , $arr);
        }
    }
    return $arr;
}
// 向上找父级的id，并用,隔开
function getParentsIds($list,$id)
{
    if(empty($list)) return '';
    $arr = unlimitedParents($list,$id);
    if(empty($arr)) return '';
    $res = '';
    foreach ($arr as $v){
        $res .= $v['id'] . ',';
    }
    return rtrim($res,",");
}
//按一个作为父级分类的id查找其所有子类数据，不包含自己
function getChildCate($data,$cid){
    static $arr;
    foreach ($data as $v) {
        if($v['pid'] == $cid){
            $arr[] = $v;
            getChildCate($data,$v['id']);
        }
    }
    return $arr;
}
function passport_encrypt($txt, $key) {
    srand((double)microtime() * 1000000);
    $encrypt_key = md5(rand(0, 32000));
    $ctr = 0;
    $tmp = '';
    for($i = 0;$i < strlen($txt); $i++) {
        $ctr = $ctr == strlen($encrypt_key) ? 0 : $ctr;
        $tmp .= $encrypt_key[$ctr].($txt[$i] ^ $encrypt_key[$ctr++]);
    }
    return base64_encode(passport_key($tmp, $key));
}
function passport_decrypt($txt, $key) {
    $txt = passport_key(base64_decode($txt), $key);
    $tmp = '';
    for($i = 0;$i < strlen($txt); $i++) {
        $md5 = $txt[$i];
        $tmp .= $txt[++$i] ^ $md5;
    }
    return $tmp;
}
function passport_key($txt, $encrypt_key) {
    $encrypt_key = md5($encrypt_key);
    $ctr = 0;
    $tmp = '';
    for($i = 0; $i < strlen($txt); $i++) {
        $ctr = $ctr == strlen($encrypt_key) ? 0 : $ctr;
        $tmp .= $txt[$i] ^ $encrypt_key[$ctr++];
    }
    return $tmp;
}
function handleEncrypt($value) {
    return passport_encrypt((string)$value,19810618);
}
function handleDecrypt($value) {
    return passport_decrypt((string)$value,19810618);
}
function encryptId($value)
{
    return str_replace("=","",handleEncrypt($value));
}
function decryptId($value)
{
    $code = handleDecrypt($value);
    if(!is_numeric($code)) throw new \Exception('参数错误');
    else return $code;
}
// system里的配置状态相关
// 通过状态值获得状态名
function getStatusText($status_val,$status_name = '') {
    if($status_name){
        $conf = config('system.status.'.$status_name);
        if(empty($conf)) return '未定义状态';
        if(isset($conf[$status_val])) $text = $conf[$status_val];
        else $text = $status_val;
        return $status_val===null ? "" : $text;
    }else{
        return  $status_val==1 ? '正常' : "已禁用";
    }
}
// 通过状态名获得状态键
function getStatusValue($status_text,$status_name) {
    $conf = config('system.status.'.$status_name);
    foreach ($conf as $k => $v){
        if($v === $status_text) {
            return $k;
        }
    }
    return null;
}
// 通过状态名获得状态值 并用,隔开 用于checkbox多选
function getStatusValueFromArray($array,$status_name) {
    return implode(',', array_map( function($v) use ($status_name){
        return getStatusValue($v,$status_name);
    } ,$array ) );
}
// 获取状态数组
function getStatusArray($status_name){
    $conf = config('system.status.'.$status_name);
    if(empty($conf)) return [];
    else return $conf;
}
// 生成字母数字编号 24 位
//function makeSn(){
//    return date('ymdH').substr(implode(NULL, array_map('ord', str_split(substr(uniqid(), 7, 6), 1))), 0, 12).substr(uniqid('', true), 15, 4);
////    return date('ymd') .substr(time(),-5) .substr(microtime(),2,5);
////    return alphaID(date('ymd').implode(NULL,array_map('ord',str_split(substr($unid, 7, 7),1))).substr($unid, 15, 6));
//}
function makeSn()
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
    );
    return $d;
}

// 生成22位订单号
//function makeOrdSn() {
//    return alphaID(makeSn());
////    $ymCode = [
////        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
////        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
////        'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5'
////    ];
////    $unid = uniqid('', true);
////    return $ymCode[(intval(date('Y'))-2018)%10].$ymCode[intval(date('m') + 9)].$ymCode[intval(date('d'))].$ymCode[intval(date('H'))].$ymCode[intval(date('i') / 2)].strtoupper(substr($unid, 7, 7)).mt_rand(10,99).substr($unid, 15, 8);
//}
/*
    * 数字转字母 字母转数字
    * @param mixed   $in    要转换的字符或数字
    * @param boolean $to_num  是否转为数字
    * @param mixed   $pad_up  Number or boolean padds the result up to a specified length 长度
    * @param string  $passKey Supplying a password makes it harder to calculate the original ID
    * @return mixed string or long
*/
function alphaID($in, $to_num = false, $pad_up = false, $passKey = null)
{
    $index = "abcdefghijkl0123456789mnopqrstuvwxyz";
    if ($passKey !== null) {
        // Although this function's purpose is to just make the
        // ID short - and not so much secure,
        // with this patch by Simon Franz (http://blog.snaky.org/)
        // you can optionally supply a password to make it harder
        // to calculate the corresponding numeric ID
        for ($n = 0; $n<strlen($index); $n++) {
            $i[] = substr( $index,$n ,1);
        }
        $passhash = hash('sha256',$passKey);
        $passhash = (strlen($passhash) < strlen($index))
            ? hash('sha512',$passKey)
            : $passhash;

        for ($n=0; $n < strlen($index); $n++) {
            $p[] =  substr($passhash, $n ,1);
        }
        array_multisort($p,  SORT_DESC, $i);
        $index = implode($i);
    }
    $base  = strlen($index);
    if ($to_num) {
        // Digital number  <<--  alphabet letter code
        $in  = strrev($in);
        $out = 0;
        $len = strlen($in) - 1;
        for ($t = 0; $t <= $len; $t++) {
            $bcpow = bcpow($base, $len - $t);
            $out   = $out + strpos($index, substr($in, $t, 1)) * $bcpow;
        }

        if (is_numeric($pad_up)) {
            $pad_up--;
            if ($pad_up > 0) {
                $out -= pow($base, $pad_up);
            }
        }
        $out = sprintf('%F', $out);
        $out = substr($out, 0, strpos($out, '.'));
    } else {
        if (is_numeric($pad_up)) {
            $pad_up--;
            if ($pad_up > 0) {
                $in += pow($base, $pad_up);
            }
        }
        $out = "";
        for ($t = floor(log($in, $base)); $t >= 0; $t--) {
            $bcp = bcpow($base, $t);
            $a   = floor($in / $bcp) % $base;
            $out = $out . substr($index, $a, 1);
            $in  = $in - ($a * $bcp);
        }
        $out = strrev($out); // reverse
    }

    return $out;
}
// 防御xss清空插入数据库前的数据
function cleanXss($string, $slow = true)
{
    if (!is_array($string)) {
        $string = trim($string);
        $string = strip_tags($string);
        $string = htmlspecialchars($string,ENT_QUOTES);
        if ($slow == false) {
            $string = str_replace(array('"', "\\", "'", "/", "..", "../", "./", "//"), '', $string);
            $no = '/%0[0-8bcef]/';
            $string = preg_replace($no, '', $string);
            $no = '/%1[0-9a-f]/';
            $string = preg_replace($no, '', $string);
            $no = '/[\x00-\x08\x0B\x0C\x0E-\x1F\x7F]+/S';
            $string = preg_replace($no, '', $string);
        }
    }else{
        foreach ($string as $k => $item) {
            $string[$k] = $item === null ? null : cleanXss($item);
        }
    }
    return $string;
}
// 去掉图片路径前缀
function cleanImagePrefix($url){
    return str_replace(getResourceUrl() ,"", $url);
}
// 添加图片路径前缀
function addImagePrefix($url){
    if (strpos($url, 'http') !== false) return $url;
    else return $url ? getResourceUrl() . $url : '';
}
//    $date_type    '本月', '近三月', '近半月', '近一年' ， '今天', '本周', '全部'
function makeTimeBetweenArray($date_type) {
    $dt = \Carbon\Carbon::now();

    switch ($date_type) {
        case '今天':
            $between = [
                date('Y-m-d 00:00:00', time()),
                date('Y-m-d 23:59:59', time())
            ];
            break;
        case '本月':
            $between = [
                $dt->setDate($dt->year, $dt->month, 1)->toDateTimeString(),
                now()
            ];
            break;
        case '本周':
            $between = [
                $dt->startOfWeek()->toDateTimeString(),
                $dt->endOfWeek()->toDateTimeString()
            ];
            break;
        case '近三月':
            $between = [
                $dt->setDate($dt->year, $dt->month - 3, $dt->day)->toDateTimeString(),
                now()
            ];
            break;
        case '近半年':
            $between = [
                $dt->setDate($dt->year, $dt->month - 6, $dt->day)->toDateTimeString(),
                now()
            ];
            break;
        case '近一年':
            $between = [
                $dt->setDate($dt->year - 1, $dt->month, $dt->day)->toDateTimeString(),
                now()
            ];
            break;
        default:
            $between = [];
            break;
    }
    return $between;
}

/**
 * 生成宣传海报
 * @param array  参数,包括图片和文字
 * @param string  $filename 生成海报文件名,不传此参数则不生成文件,直接输出图片
 * @return [type] [description]
 */
function createPoster($config=array(), $filename=""){
    //如果要看报什么错，可以先注释调这个header
    if(empty($filename)) header("content-type: image/png");
    $imageDefault = array(
        'left'=>0,
        'top'=>0,
        'right'=>0,
        'bottom'=>0,
        'width'=>100,
        'height'=>100,
        'opacity'=>100
    );
    $textDefault = array(
        'text'=>'',
        'left'=>0,
        'top'=>0,
        'fontSize'=>32,       //字号
        'fontColor'=>'255,255,255', //字体颜色
        'angle'=>0,
        'fontPath'=>"poster/ali.ttf",
    );
    $background = $config['background'];//海报最底层得背景
    //背景方法
    $backgroundInfo = getimagesize($background);
    $backgroundFun = 'imagecreatefrom'.image_type_to_extension($backgroundInfo[2], false);
    $background = $backgroundFun($background);
    $backgroundWidth = imagesx($background);  //背景宽度
    $backgroundHeight = imagesy($background);  //背景高度
    $imageRes = imageCreatetruecolor($backgroundWidth,$backgroundHeight);
    $color = imagecolorallocate($imageRes, 40, 180, 130);
    imagefill($imageRes, 0, 0, $color);
    // imageColorTransparent($imageRes, $color);  //颜色透明
    imagecopyresampled($imageRes,$background,0,0,0,0,imagesx($background),imagesy($background),imagesx($background),imagesy($background));
    //bool imagecopyresampled ( resource $dst_image , resource $src_image , int $dst_x , int $dst_y , int $src_x , int $src_y , int $dst_w , int $dst_h ,int $src_w , int $src_h )
    //$dst_image：新建的图片
    //$src_image：需要载入的图片
    //$dst_x：设定需要载入的图片在新图中的x坐标
    //$dst_y：设定需要载入的图片在新图中的y坐标
    //$src_x：设定载入图片要载入的区域x坐标
    //$src_y：设定载入图片要载入的区域y坐标
    //$dst_w：设定载入的原图的宽度（在此设置缩放）
    //$dst_h：设定载入的原图的高度（在此设置缩放）
    //$src_w：原图要载入的宽度
    //$src_h：原图要载入的高度
    //处理了图片
    if(!empty($config['image'])){
        foreach ($config['image'] as $key => $val) {
            $val = array_merge($imageDefault,$val);
            $info = getimagesize($val['url']);
            $function = 'imagecreatefrom'.image_type_to_extension($info[2], false);
            if($val['stream']){   //如果传的是字符串图像流
                $info = getimagesizefromstring($val['url']);
                $function = 'imagecreatefromstring';
            }
            $res = $function($val['url']);
            $resWidth = $info[0];
            $resHeight = $info[1];
            //建立画板 ，缩放图片至指定尺寸
            $canvas=imagecreatetruecolor($val['width'], $val['height']);
            imagefill($canvas, 0, 0, $color);
            //关键函数，参数（目标资源，源，目标资源的开始坐标x,y, 源资源的开始坐标x,y,目标资源的宽高w,h,源资源的宽高w,h）
            imagecopyresampled($canvas, $res, 0, 0, 0, 0, $val['width'], $val['height'],$resWidth,$resHeight);
            $val['left'] = $val['left']<0?$backgroundWidth- abs($val['left']) - $val['width']:$val['left'];
            $val['top'] = $val['top']<0?$backgroundHeight- abs($val['top']) - $val['height']:$val['top'];
            //放置图像
            imagecopymerge($imageRes,$canvas, $val['left'],$val['top'],$val['right'],$val['bottom'],$val['width'],$val['height'],$val['opacity']);//左，上，右，下，宽度，高度，透明度
        }
    }
    //处理文字
    if(!empty($config['text'])){
        foreach ($config['text'] as $key => $val) {
            $val = array_merge($textDefault,$val);
            list($R,$G,$B) = explode(',', $val['fontColor']);
            $fontColor = imagecolorallocate($imageRes, $R, $G, $B);
            $val['left'] = $val['left']<0?$backgroundWidth- abs($val['left']):$val['left'];
            $val['top'] = $val['top']<0?$backgroundHeight- abs($val['top']):$val['top'];
            imagettftext($imageRes,$val['fontSize'],$val['angle'],$val['left'],$val['top'],$fontColor,$val['fontPath'],$val['text']);
        }
    }
    //生成图片
    if(!empty($filename)){
        $res = imagejpeg ($imageRes,$filename,90); // 保存到本地
        imagedestroy($imageRes);
        if(!$res) return false;
        return $filename;
    }else{
        imagejpeg ($imageRes);     // 在浏览器上显示
        imagedestroy($imageRes);
    }
}


// 获取红包随机值 面额越大 几率越小
function makeRedPacketAmount($min, $max) {
    $temp = 0;
    for ($i = 0; $i <= ($max - $min) ; $i++) {
        $temp += $max - $min - $i + 1;
    }
    $rand = mt_rand(0,$temp);
    $temp = 0;
    $flag = 0;
    for ($i = 0; $i <= ($max - $min) ; $i++) {
        $temp += $max - $min - $i + 1;
        if($temp >= $rand) {
            $flag = $i;
            break;
        }
    }
    return $min + $flag;
}