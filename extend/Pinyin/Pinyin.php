<?php
/**
 * PHP 汉字转拼音 [包含20902个基本汉字+5059生僻字]
 * @note 请开启 mb_string 扩展
 * @author TechLee
 */
/* 测试用例
$start_time = microtime(1);

var_dump(pinyin('对多音字无能为力'));
var_dump(pinyin('最全的PHP汉字转拼音库，比百度词典还全（dict.baidu.com）'));
var_dump(pinyin('试试：㐀㐁㐄㐅㐆㐌㐖㐜'));
var_dump(pinyin('一起开始数：12345'));
var_dump(pinyin('海南'));
var_dump(pinyin('乌鲁木齐'));
var_dump(pinyin('前总理朱镕基'));
var_dump(pinyin('仅首字母', 'first'));
var_dump(pinyin('占-位-符-为-空', 'all', ''));
var_dump(pinyin('不允许中文以外的字符', 'first', '', ''));

for ($i=0; $i<1e4; $i++) { // 性能次数，转换1万次
pinyin('对多音字无能为力');
pinyin('最全的PHP汉字转拼音库，比百度词典还全（dict.baidu.com）');
pinyin('试试：㐀㐁㐄㐅㐆㐌㐖㐜');
pinyin('一起开始数：12345');
pinyin('海南');
pinyin('乌鲁木齐');
pinyin('前总理朱镕基');
pinyin('仅首字母', 'first');
pinyin('占-位-符-为-空', 'all', '');
pinyin('不允许中文以外的字符', 'first', '', '');
}

echo number_format(microtime(1) - $start_time, 6);
 */
namespace word;

/**
 * 中文转拼音 (utf8版,gbk转utf8也可用)
 */
class Pinyin
{
	/**
	 * 获取首字母
	 */
    public static function first($str)
    {
        return self::Pinyin($str, 'frist');
    }
    /**
	 * 获取连续拼音字符串
	 */
    public static function all($str)
    {
        $pinyin = self::Pinyin($str, 'all');
        return str_replace(' ', '', $pinyin);
    }
    /**
     * @param string $str         utf8字符串
     * @param string $ret_format  返回格式 [all:全拼音|first:首字母|one:仅第一字符首字母]
     * @param string $placeholder 无法识别的字符占位符
     * @param string $allow_chars 允许的非中文字符
     * @return string             拼音字符串
     */
    public static function Pinyin($str, $ret_format = 'all', $placeholder = '_', $allow_chars = '/[a-zA-Z\d ]/')
    {
        static $pinyins = null;
        if (null === $pinyins) {
            $data = require dirname(__FILE__) . '/WordTable.php';
            $rows = explode('|', $data);

            $pinyins = array();
            foreach ($rows as $v) {
                list($py, $vals) = explode(':', $v);
                $chars           = explode(',', $vals);

                foreach ($chars as $char) {
                    $pinyins[$char] = $py;
                }
            }
        }

        $str = trim($str);
        $len = mb_strlen($str, 'UTF-8');
        $rs  = '';
        for ($i = 0; $i < $len; $i++) {
            $chr = mb_substr($str, $i, 1, 'UTF-8');
            $asc = ord($chr);
            if ($asc < 0x80) {
                // 0-127
                if (preg_match($allow_chars, $chr)) { // 用参数控制正则
                    $rs .= $chr; // 0-9 a-z A-Z 空格
                } else { // 其他字符用填充符代替
                    $rs .= $placeholder;
                }
            } else {
                // 128-255
                if (isset($pinyins[$chr])) {
                    $rs .= 'first' === $ret_format ? $pinyins[$chr][0] : ($pinyins[$chr] . ' ');
                } else {
                    $rs .= $placeholder;
                }
            }

            if ('one' === $ret_format && '' !== $rs) {
                return $rs[0];
            }
        }
        return rtrim($rs, ' ');
    }
}
