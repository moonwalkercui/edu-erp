<?php
/**
 * 备份、还原
 * 1.去除sql导入的时候排除sql文件里面的注释'-- ' 从而解决sql中单双引号不能导入
 * 2.单行读取后的sql直接执行，避免重新将sql语句组合到数组中再从数组中读取导入sql，提高效率
 * 说明：分卷文件是以_v1.sql为结尾(20120522021241_all_v1.sql)
 * 功能：实现mysql数据库分卷备份,选择表进行备份,实现单个sql文件及分卷sql导入
 */
namespace backup;

use think\Config;
use think\Db;

class Backup
{
    public $database; // 所用数据库
    public $sqldir; // 数据库备份文件夹
    private $db; // 数据库连接
    // 换行符
    private $ds        = PHP_EOL;
    private $suffix    = 'php';
    private $backupDir = '';

    public $msg = "";

    // 存储SQL的变量
    public $sqlContent = "";
    // 每条sql语句的结尾符
    public $sqlEnd = ";";

    /**
     * 初始化
     *
     */
    public function __construct()
    {
        $database        = Config::get('database');
        $this->host      = $database['hostname'];
        $this->database  = $database['database'];
        $this->charset   = $database['charset'];
        $this->backupDir = Config::get('base.backup_path');
        set_time_limit(0);
        @ob_end_flush();
    }

    /**
     * 数据库备份
     * 参数：备份哪个表(可选),备份目录(可选，默认为backup),分卷大小(可选,默认2000，即2M)
     *
     * @param $string $dir
     * @param int $size
     * @param $string $tablename
     */
    public function backup($tablename = '', $dir = '', $size = '2000')
    {
        $dir = $dir ? $dir : $this->backupDir;
        //当次备份文件名
        $dir .= date('Y_m_d_H_i_s') . DS;
        // 创建目录
        if (!is_dir($dir)) {
            mkdir($dir, 0777, true) or die('创建文件夹失败');
        }
        $size = $size ? $size : 2048;
        $sql  = '';
        // 只备份某个表
        if (!empty($tablename)) {
            $table = Db::query("SHOW TABLES LIKE '" . $tablename . "'");
            if (!current($table)) {
                $this->_showMsg('表-<b>' . $tablename . '</b>-不存在，请检查！', true);
                die();
            }
            $this->_showMsg('正在备份表 <span class="imp">' . $tablename . '</span>');
            // 插入dump信息
            $sql = $this->_retrieve();
            // 插入表结构信息
            $sql .= $this->_insert_table_structure($tablename);
            // 插入数据
            $data = Db::query("select * from " . $tablename);
            // 文件名前面部分
            $filename = date('YmdHis') . "_" . $tablename;
            // 第几分卷
            $p = 1;
            // 循环每条记录
            foreach ($data as $key => $record) {
                // 单条记录
                $sql .= $this->_insert_record($tablename, $record);
                // 如果大于分卷大小，则写入文件
                if (strlen($sql) >= $size * 1024) {
                    $file = $filename . "_v" . $p . "." . $this->suffix;
                    if ($this->_write_file($sql, $file, $dir)) {
                        $this->_showMsg("表-<b>" . $tablename . "</b>-卷-<b>" . $p . "</b>-数据备份完成,备份文件 [ <span class='imp'>" . $file . "</span> ]");
                    } else {
                        $this->_showMsg("备份表 -<b>" . $tablename . "</b>- 失败", true);
                        return false;
                    }
                    // 下一个分卷
                    $p++;
                    // 重置$sql变量为空，重新计算该变量大小
                    $sql = "";
                }
            }
            // 及时清除数据
            unset($data, $record);
            // sql大小不够分卷大小
            if ($sql != "") {
                $filename .= "_v" . $p . "." . $this->suffix;
                if ($this->_write_file($sql, $filename, $dir)) {
                    $this->_showMsg("表-<b>" . $tablename . "</b>-卷-<b>" . $p . "</b>-数据备份完成,备份文件 [ <span class='imp'>" . $filename . "</span> ]");
                } else {
                    $this->_showMsg("备份卷-<b>" . $p . "</b>-失败<br />");
                    return false;
                }
            }
            $this->_showMsg("恭喜您! <span class='imp'>备份成功</span>");
        } else {
            $this->_showMsg('正在备份');
            // 备份全部表
            if ($tables = Db::query("show table STATUS ")) {
                $this->_showMsg("读取数据库结构成功！");
            } else {
                $this->_showMsg("读取数据库结构失败！");
                exit(0);
            }
            // 插入dump信息
            $sql .= $this->_retrieve();
            // 文件名前面部分
            $filename = date('YmdHis') . "_all";
            // 查出所有表
            $tables = Db::getTables();
            // 第几分卷
            $p = 1;
            // 循环所有表
            foreach ($tables as $tablename) {
                // 获取表结构
                $sql .= $this->_insert_table_structure($tablename);
                $data = Db::query("select * from " . $tablename);
                // $num_fields = mysql_num_fields ( $data );

                // 循环每条记录
                foreach ($data as $key => $record) {
                    // 单条记录
                    $sql .= $this->_insert_record($tablename, $record);
                    // 如果大于分卷大小，则写入文件
                    if (strlen($sql) >= $size * 1000) {

                        $file = $filename . "_v" . $p . "." . $this->suffix;
                        // 写入文件
                        if ($this->_write_file($sql, $file, $dir)) {
                            $this->_showMsg("-卷-<b>" . $p . "</b>-数据备份完成,备份文件 [ <span class='imp'>" . $file . "</span> ]");
                        } else {
                            $this->_showMsg("卷-<b>" . $p . "</b>-备份失败!", true);
                            return false;
                        }
                        // 下一个分卷
                        $p++;
                        // 重置$sql变量为空，重新计算该变量大小
                        $sql = "";
                    }
                }
            }
            // sql大小不够分卷大小
            if ($sql != "") {
                $filename .= "_v" . $p . "." . $this->suffix;
                if ($this->_write_file($sql, $filename, $dir)) {
                    $this->_showMsg("-卷-<b>" . $p . "</b>-数据备份完成,备份文件 [ <span class='imp'>" . $filename . "</span> ]");
                } else {
                    $this->_showMsg("卷-<b>" . $p . "</b>-备份失败", true);
                    return false;
                }
            }
            $this->_showMsg("恭喜您! <span class='imp'>备份成功</span>");
        }
    }

    //  及时输出信息
    private function _showMsg($msg, $err = false)
    {
        $err = $err ? "<span class='err'>ERROR:</span>" : '';
        echo "<p class='dbDebug'>" . $err . $msg . "</p>";
        flush();

    }

    /**
     * 插入数据库备份基础信息
     *
     * @return string
     */
    private function _retrieve()
    {
        $value = '';
        $value .= '--' . $this->ds;
        $value .= '-- MySQL database dump' . $this->ds;
        $value .= '-- Created by CMF, Power By TechLee. ' . $this->ds;
        $value .= '--' . $this->ds;
        $value .= '-- 主机: ' . $this->host . $this->ds;
        $value .= '-- 生成日期: ' . date('Y') . ' 年  ' . date('m') . ' 月 ' . date('d') . ' 日 ' . date('H:i:s') . $this->ds;
        $db_version = Db::query('select version() as version');
        $value .= '-- MySQL版本: ' . @$db_version[0]['version'] . $this->ds;
        $value .= '-- PHP 版本: ' . phpversion() . $this->ds;
        $value .= $this->ds;
        $value .= '--' . $this->ds;
        $value .= '-- 数据库: `' . $this->database . '`' . $this->ds;
        $value .= '--' . $this->ds . $this->ds;
        $value .= '-- -------------------------------------------------------';
        $value .= $this->ds . $this->ds;
        return $value;
    }

    /**
     * 插入表结构
     *
     * @param unknown_type $table
     * @return string
     */
    private function _insert_table_structure($table)
    {
        $sql = '';
        $sql .= "--" . $this->ds;
        $sql .= "-- 表的结构" . $table . $this->ds;
        $sql .= "--" . $this->ds . $this->ds;

        // 如果存在则删除表
        $sql .= "DROP TABLE IF EXISTS `" . $table . '`' . $this->sqlEnd . $this->ds;
        // 获取详细表信息
        $row = Db::query('SHOW CREATE TABLE `' . $table . '`');
        $row = current($row);
        $sql .= $row['Create Table'];
        $sql .= $this->sqlEnd . $this->ds;
        // 加上
        $sql .= $this->ds;
        $sql .= "--" . $this->ds;
        $sql .= "-- 转存表中的数据 " . $table . $this->ds;
        $sql .= "--" . $this->ds;
        $sql .= $this->ds;
        return $sql;
    }

    /**
     * 插入单条记录
     *
     * @param string $table
     * @param int $num_fields
     * @param array $record
     * @return string
     */
    private function _insert_record($table, $record)
    {
        // sql字段逗号分割
        $insert = '';
        $comma  = "";
        $insert .= "INSERT INTO `" . $table . "` VALUES(";
        // 循环每个子段下面的内容
        foreach ($record as $key => $value) {
            if(is_null($value)){
                $value = 'null';            
            }else{
                $value = "'" . addslashes($value) . "'";
            }            
            $value = str_replace(["\r","\n"], ["\\r" , "\\n"], $value);
            $insert .= ($comma . $value);
            $comma = ",";
        }
        $insert .= ");" . $this->ds;
        return $insert;
    }

    /**
     * 写入文件
     *
     * @param string $sql
     * @param string $filename
     * @param string $dir
     * @return boolean
     */
    private function _write_file($sql, $filename, $dir)
    {
        // 创建目录
        if (!is_dir($dir)) {
            mkdir($dir, 0777, true);
        }
        $re = true;
        if (!@$fp = fopen($dir . $filename, "w+")) {
            $re = false;
            $this->_showMsg("打开sql文件失败！", true);
        }
        if (!@fwrite($fp, $sql)) {
            $re = false;
            $this->_showMsg("写入sql文件失败，请文件是否可写", true);
        }
        if (!@fclose($fp)) {
            $re = false;
            $this->_showMsg("关闭sql文件失败！", true);
        }
        return $re;
    }

    /*
     *
     * -------------------------------上：数据库导出-----------分割线----------下：数据库导入--------------------------------
     */

    /**
     * 导入备份数据
     * 说明：分卷文件格式20120516211738_all_v1.sql
     * 参数：文件路径(必填)
     *
     * @param string $sqlfile
     */
    public function restore($backupDir)
    {
        $backupDir = $this->backupDir . $backupDir . DS;
        // echo $backupDir;die;
        $handle  = opendir($backupDir);
        $sqlfile = '';
        while (false !== ($file = readdir($handle))) {
            if ($file != '.' && $file != '..') {
                $sqlfile = $backupDir . $file;
                break;
            }
        }
        // 检测文件是否存在
        if (empty($sqlfile) || !file_exists($sqlfile)) {
            $this->_showMsg("sql文件不存在！请检查", true);
            exit();
        }
        // 获取数据库存储位置
        $sqlpath      = pathinfo($sqlfile);
        $this->sqldir = $sqlpath['dirname'];
        // 检测是否包含分卷，将类似20120516211738_all_v1.sql从_v分开,有则说明有分卷
        $volume      = explode("_v", $sqlfile);
        $volume_path = $volume[0];
        $this->_showMsg("请勿刷新及关闭浏览器以防止程序被中止，如有不慎！将导致数据库结构受损！");
        $this->_showMsg("正在导入备份数据，请稍等……");
        if (empty($volume[1])) {
            $this->_showMsg("正在导入sql：<span class='imp'>" . $sqlfile . '</span>');
            // 没有分卷
            if ($this->_import($sqlfile)) {
                $this->_showMsg("数据库导入成功！");
            } else {
                $this->_showMsg('数据库导入失败！', true);
                exit();
            }
        } else {
            // 存在分卷，则获取当前是第几分卷，循环执行余下分卷
            $volume_id = explode(".", $volume[1]);
            // 当前分卷为$volume_id
            $volume_id = intval($volume_id[0]);
            $volume_id = $volume_id ? 1 : $volume_id;
            while ($volume_id) {
                $tmpfile = $volume_path . "_v" . $volume_id . "." . $this->suffix;
                // 存在其他分卷，继续执行
                if (file_exists($tmpfile)) {
                    // 执行导入方法
                    $this->_showMsg("正在导入分卷 $volume_id ：<span style='color:#f00;'>" . basename($tmpfile) . '</span><br />');
                    if ($this->_import($tmpfile)) {

                    } else {
                        $volume_id = $volume_id ? $volume_id : 1;
                        exit("导入分卷：<span style='color:#f00;'>" . basename($tmpfile) . '</span>失败！可能是数据库结构已损坏！请尝试从分卷1开始导入');
                    }
                } else {
                    $this->_showMsg("此分卷备份全部导入成功！<br />");
                    return;
                }
                $volume_id++;
            }
        }
    }

    /**
     * 将sql导入到数据库（普通导入）
     *
     * @param string $sqlfile
     * @return boolean
     */
    private function _import($sqlfile)
    {
        // sql文件包含的sql语句数组
        $sqls = array();
        $f    = fopen($sqlfile, "rb");
        // 创建表缓冲变量
        $create_table = '';
        while (!feof($f)) {
            // 读取每一行sql
            $line = fgets($f);
            // 这一步为了将创建表合成完整的sql语句
            // 如果结尾没有包含';'(即为一个完整的sql语句，这里是插入语句)，并且不包含'ENGINE='(即创建表的最后一句)
            if (!preg_match('/;/', $line) || preg_match('/ENGINE=/', $line)) {
                // 将本次sql语句与创建表sql连接存起来
                $create_table .= $line;
                // 如果包含了创建表的最后一句
                if (preg_match('/ENGINE=/', $create_table)) {
                    //执行sql语句创建表
                    $this->_insert_into($create_table);
                    // 清空当前，准备下一个表的创建
                    $create_table = '';
                }
                // 跳过本次
                continue;
            }
            //执行sql语句
            $this->_insert_into($line);
        }
        fclose($f);
        return true;
    }

    //插入单条sql语句
    private function _insert_into($sql)
    {
        $res = Db::execute(trim($sql));
        if (!$res) {
            $this->msg .= Db::getError();
            return false;
        }
    }
    static public function deleteDir($deletePath, $delDir = true){
        $deletePath = strstr($deletePath, ',') ? explode(',', $deletePath) : $deletePath;
        if(is_array($deletePath)){
            $res = false;
            foreach ($deletePath as $key => $value) {
                $res = self::deleteDir($value);
            }
            return $res;
        }            
        $path = Config::get('base.backup_path') . $deletePath;
        if (empty($deletePath) || !is_dir($path)) {
            return false;
        }
        if (substr($path, strlen($path) - 1) != DS) {
            $path .= DS;
        }
        $handle = @opendir($path);        
        while (false !== ($file = readdir($handle))) {
            if ($file != '.' && $file != '..') {
                $path2 = $path . $file;
                if (is_dir($path2)) {
                    self::deleteDir($deletePath. DS . $file, $delDir);
                }else{
                    unlink($path2);
                }                
            }
        }        
        closedir($handle);
        if ($delDir){
            return rmdir($path);        
        }else {
            if (file_exists($path)) {
                return unlink($path);
            } else {
                return false;
            }
        }        
    }

    static public function getBackupList()
    {
        $dirs = [];
        $path = Config::get('base.backup_path');
        if (!is_dir($path)) {
            return [];
        }
        if (substr($path, strlen($path) - 1) != '/') {
            $path .= '/';
        }
        $handle = opendir($path);
        while (false !== ($file = readdir($handle))) {
            if ($file != '.' && $file != '..') {
                $path2 = $path . $file;
                if (is_dir($path2)) {
                    $dirs[] = array(
                        'url'   => $file,
                        'mtime' => filemtime($path2),
                    );
                }
            }
        }
        return $dirs;
    }
}
