<?php
/**
 * 静态页面生成控制器类
 * @since  2019-3
 * @author 冷风崔 <541720500@qq.com>
 *
 * 1，config.php中加入以下参数：
 * 'dist_path' => ROOT_PATH. 'public/', // 静态站放置路径
 * 'dist_dir_name' => 'dist', // 静态页的文件夹名 须放置在public下
 * 'dist_module_name' => 'dist', // 静态页的模块名
 * 'dist_file_dot' => '_', // 静态页文件名字中的参数分隔符
 *
 * 2，拷贝dist_rules.php到application目录
 * 3, 有需要在原生tp预览模板并生成静态需求的，可以封装控制器的 fetch 方法
 *
 */
namespace app\common\controller;
use think\Controller;
use think\facade\Config;

class JKBuildHtml extends Controller
{
    protected $module_name;
    protected $dist_path;
    protected $dir_name;
    protected $file_dot;
    protected $domain;
    protected $src_match;
    protected $sub_dir;
    protected $config;

    protected function initialize()
    {
        $this->module_name = config('dist_module_name');
        $this->dist_path = config('dist_path');
        $this->dir_name = config('dist_dir_name');
        $this->sub_dir = config('dist_sub_dir');
        $this->file_dot = config('dist_file_dot');
        $this->src_match = config('dist_src_match');
        if(
            $this->module_name === null ||
            $this->dist_path === null ||
            $this->dir_name === null ||
            $this->file_dot === null ||
            $this->sub_dir === null ||
            $this->src_match === null
        ) $this->handleError('配置参数设置有误');
        $this->domain = request()->domain() . '/';

        // thinkphp 5.1的写法
        $this->config = Config::get('dist_rules.');
        // thinkphp 5 的写法
//        if (!is_file(CONF_PATH . 'dist_rules.php')) {
//            die('未定义生成静态页的配置文件 dist_rules.php');
//        }
//        $rules = include CONF_PATH . 'dist_rules.php';
//        if (!is_array($rules)) {
//            die('配置文件 dist_rules.php 格式错误');
//        }
//        $this->config = $rules;
    }
    /*
     *  生成单个html文件 参数实例:
     *  $path : news/index
     *  $param : ['id' => 5]
     * */
    public function buildOne($path, $params = [])
    {
        $match_filename =  $this->matchFilename($path, $params);
        $file_name = $match_filename[0];
        $param_str = $match_filename[1];
        $keys = $match_filename[2];
        $file_name = $this->fetchDir($file_name);
        $this->buildFile(
            $this->module_name. '/' .$path . ( $param_str ? '?' . $param_str : ''),
            strtr($file_name, $keys)
        );
    }
    // 用于控制器封装fetch方法中 写法
    //  $html = $this->fetch();
    //  controller('common/JKBuildHtml')->buildFromFetch( $html, input('get.')); // 生成静态html
    //  echo $html;
    public function buildFromFetch($html, $params)
    {
        $path = strtolower(request()->controller() . '/' . request()->action());
        $match_filename =  $this->matchFilename($path, $params);
        $file_name = $match_filename[0];
        $param_str = $match_filename[1];
        $keys = $match_filename[2];
        $file_name = strtr($this->fetchDir($file_name), $keys);
        $file_path = $this->makeFileDir($file_name);
        $res = $this->filePutHtml($file_path, $html);
        $url = $this->module_name. '/' .$path . ( $param_str ? '?' . $param_str : '');
//         echo '[ 文件已生成 ] '. $url .' [ 路径 ] ' . $file_path . ' [ 长度 ] ' . $res . '<br>';
    }

    /*
     *  批量生成html
     *  todo 改成ajax的不阻塞
     * */
    public function buildAll()
    {
        foreach ($this->getDistRules() as $file_name => $path) {
            // 不带参数的 直接请求
            $file_name = $this->fetchDir($file_name);
            // 找到参数 news_:id
            $expl = explode(':', $file_name);
            if (count($expl) > 1) {
                // 带参数的 拼接参数后请求
                // 循环获取 id 并生成 静态页文件
                $path_params = $this->getParams($path, $expl);
                $form_datas = $this->setFromData($path_params[1]);
                foreach ($form_datas as $form) {
//                    var_dump($file_name);
//                    var_dump($form['map']);
//                    echo '+++++++++++++<br>';
                    $this->buildFile(
                        $this->module_name. '/' .$path_params[0] . '?' . $form['str'],
                        strtr($file_name, $form['map'])
                    );
                }
            } else {
                $this->buildFile(
                    $this->module_name. '/' .$path,
                    $file_name
                );
            }
        }
        echo "[ 全部生成完毕 ] 大功告成!!! ";
    }

    // 匹配静态规则中的文件名和路径，返回[生成文件名，请求地址参数后缀，替换地址中参数的数组]
    protected function matchFilename($path, $params)
    {
        $rules = $this->getDistRules();
        $param_str = '';
        $file_name = '';
        $keys = [];
        if(!empty($params)) {
            foreach ($params as $k => $p) {
                $param_str .= $k . '='. $p . '&';
                $keys[':'. $k] = $p;
            }
            $param_str = rtrim($param_str, '&');
        }
        foreach ($rules as $k=>$v) {
            if($path == $v)
                $file_name = $k;
        }
        if($file_name == '') {
            $this->handleError('该路径没有设置生成静态规则');
        }
        return [
            $file_name,
            $param_str,
            $keys
        ];
    }
    protected function getDistRules()
    {
        return $this->config;
    }
    public function buildFile($url, $file_name)
    {
//        $url = substr($url, 0, strrpos($url, '/'));
        $file_path = $this->makeFileDir($file_name);
        $html = $this->curlRequest($url);
        $res = $this->filePutHtml($file_path, $html);
        echo '[ 文件已生成 ] '. $url .' [ 路径 ] ' . $file_path . ' [ 长度 ] ' . $res . '<br>';
        ob_flush();
        flush();
    }
    protected function filePutHtml($file_path, $html)
    {
//        return file_put_contents($file_path, $html);
        // 匹配静态资源路径 把../ 改成 ./
        if(strpos($file_path, $this->dir_name . '/' . $this->sub_dir .'/') !== false){
            $dot_line = '../';
        }else{
            $dot_line = './';
        }
        return file_put_contents($file_path, str_replace($this->src_match , $dot_line, $html));
    }
    // 全是get请求
    private function curlRequest($url)
    {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HEADER, false); //设定是否输出页面内容
        curl_setopt($ch, CURLOPT_URL, $this->domain . $url);
        $result = curl_exec($ch);
        curl_close($ch);
        return $result;
    }
    protected function makeFileDir($file_name)
    {
        $file_path = $this->dist_path . $this->dir_name . '/' . $file_name . '.html';
        if (!is_dir(dirname($file_path))) {
            $this->createDir(dirname($file_path));
        }
        return $file_path;
    }
    // 创建文件夹
    private function createDir($path)
    {
        if (!file_exists($path)) {
            $this->createDir(dirname($path));
            if(mkdir($path, 0777)) echo '[ 文件夹已生成 ] '.$path . '<br>';
            else die( '[ 文件夹已生成失败 ] '.$path );
        }
    }

    /*
     * 设置路径中参数的值的范围
     * 比如路径中有'news/find/:id/:sn' 则$params为 ['id' => [1,2,3,4,5,6], 'sn' => ['a1','b2']] ...
     * 这个方法会将以上参数进行排列组合
     * 返回数据：
        array(24) {
          [0]=> string(15) "id=1&sn=a1"
          [1]=> string(15) "id=1&sn=b1"
          ...
     * */
    protected function setFromData($params)
    {
        $arr = $this->arrayRank($params);
        $res = [];
        foreach ($arr as $k1 => $v1) {
            $str = '';
            $map = [];
            foreach ($v1 as $k2 => $v2) {
                $str .= $k2 . '=' . rawurlencode($v2) . '&';
                $map[':' . $k2] = $v2;
            }
            $res[$k1] = [
                'str' => rtrim($str, '&'),
                'map' => $map,
            ];
        }
        return $res;
    }
    /*
    arrayRank 数组排列组合：把多个数组里的元素进行组合排列。适用于商品规格和每个商品规格值的排列组合。
    */
    protected function arrayRank($d)
    {
        $keys = array_keys($d);
        if (func_num_args() > 1)
            $d = func_get_args();
        $r = array_pop($d);
        while ($d) {
            $t = [];
            $s = array_pop($d);
            if (!is_array($s))
                $s = [$s];
            foreach ($s as $x) {
                foreach ($r as $y) {
                    $t[] = array_merge([$x], is_array($y) ? $y : [$y]);
                }
            }
            $r = $t;
        }
        foreach ($r as $k => &$v) {
            $v = array_combine($keys, count($v) == 1 ? [$v] : $v);
        }
        return $r;
    }
    // 获取文件夹位置
    protected function fetchDir($key)
    {
        if (0 === strrpos($key, '@')) {
            $key = substr($key, 1);
        } else {
            $key = 'pages/' . $key;
        }
        return $key;
    }
    protected function handleError($msg)
    {
        echo json_encode([
            'code' => 0,
            'msg' => $msg,
        ], JSON_UNESCAPED_UNICODE);
        die;
    }
    // 返回值 第一个是请求路径，第二个是参数列表 如:['id' => [1, 2, 3], 'attr' => [ 2 , 3 ]]
    protected function getParams($path, $p)
    {
        unset($p[0]);
        foreach ($p as &$v) {
            $v = str_replace($this->file_dot, '', $v);
        }
        if(is_array($path)) {
            $temp = $path;
            if (0 === strrpos($temp[1], 'func:')) {
                $func_name = substr($temp[1], 5);
                if(false === strpos($func_name, '/')){
                    $params = $func_name(isset($temp[2]) ? $temp[2] : null);
                } else {
                    $params = action($func_name, isset($temp[2]) ? $temp[2] : null);
                }
            } else {
                $db_name = $temp[1];
                $where = isset($temp[2]) ? $temp[2] : '';
                $db = \think\Db::name($db_name)->where($where)->field(array_values($p))->select();
                $db_res = [];
                foreach ($db as $k1 => $v1) {
                    foreach ($v1 as $k2 => $v2) {
                        $db_res[$k2][$k1] = $v2;
                    }
                }
                $params = $db_res;
            }
            $res = [ $temp[0], $params ];
        } else {
            $res = [ $path, null ];
        }
        return $res;
    }
}
