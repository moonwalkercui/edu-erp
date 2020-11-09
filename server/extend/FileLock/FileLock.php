<?php
/**
 *
 * 用户高并发的文件锁处理并发请求
 *
 * 作者：Ryan 541720500@qq.com
 *
 * 一、多文件执行锁的用法
 *
 * 文件1：
 * FileLock::lock();
 * // 独占进程的业务逻辑
 * FileLock::unlock();
 *
 *  文件2：（阻塞模式）
 * if(FileLock::onUnlocked()) {
 * echo '文件1解锁后这里才会执行';
 * } else {
 * echo '这里永不会执行到';
 * }
 *
 *  文件2：（非阻塞模式）
 * if(FileLock::isLockingAndContinue()) {
 * echo '异常：文件1中的代码正在执行，稍后再来吧';
 * } else {
 * echo '这里永不会执行到';
 * }
 *
 *
 * 二、单文件多进程阻塞模式组合使用用法
 * if( FileLock::lock() ) {
 * echo '业务逻辑代码';
 * FileLock::unlock();
 * }
 *
 *  三、 ab测试代码，如果执行后数据库里的time按降序排列，那么id也一定会按降序排列
 * .\ab.exe -n 100 -c 100 xxx.html/test
 *
 * if (FileLock::lock()) {
 * list($msec, $sec) = explode(' ', microtime());
 * $msectime = (float)sprintf('%.0f', (floatval($msec) + floatval($sec)) * 1000);
 * db('temp')->insert([  'time' => $msectime ]);
 * FileLock::unlock();
 * }
 *
 */

namespace FileLock;

class FileLock
{
    static private $lock_path;
    static private $lock_file;

    /*
     * @flag 锁标记 可标记不同的锁
     * */
    static function lock($flag = '')
    {
        self::lockFlag($flag);
        self::$lock_file = fopen(self::$lock_path, "w+");
        return flock(self::$lock_file, LOCK_EX);
    }

    static function unlock()
    {
        flock(self::$lock_file, LOCK_UN);
        fclose(self::$lock_file);
        unlink(self::$lock_path);
    }

    // 阻塞模式
    static function onUnlocked($flag = '')
    {
        self::lockFlag($flag);
        $file = fopen(self::$lock_path, 'r');
        var_dump(flock($file, LOCK_EX));
        return flock($file, LOCK_EX);
    }

    // 不阻塞 继续执行
    static function isLockingAndContinue($flag = '')
    {
        self::lockFlag($flag);
        $file = fopen(self::$lock_path, 'r');
        if (flock($file, LOCK_SH | LOCK_NB)) return false; // 未占用
        return true; // 当前锁被占用 一般会返回异常
    }

    static private function lockFlag($flag)
    {
        $dir = env('runtime_path') . 'lock' . DIRECTORY_SEPARATOR; // 注意： tp 版本 不一样 runtime 路径不一样
        if(!is_dir($dir)) {
            mkdir($dir, 0777);
        }
        self::$lock_path = $dir . ($flag ? md5($flag) : 'lock') . '.lk';
    }

//    // 用回调函数阻塞执行  暂不用
//    static function lockRun(\Closure $callback)
//    {
//        $file = fopen("text.txt", "w+");
//        if (flock($file, LOCK_EX))   // 有阻塞 其他进程进来会等待
//        {
//            $res = $callback();
//            flock($file, LOCK_UN);
//        } else {
//            echo "Error locking file!"; // 可不写 因为不会执行到
//        }
//        fclose($file);
//        return $res;
//    }
//
//    // 用回调函数不阻塞执行  暂不用
//    static function lockRunOrNull(\Closure $callback)
//    {
//        $file = fopen("text.txt", "a+");
//        if (flock($file, LOCK_EX | LOCK_NB)) // LOCK_NB 表示非阻塞
//        {
//            $res = $callback();
//            flock($file, LOCK_UN);
//        } else {
//            // 如果上面锁着呢，其他进程执行会报错 可用用恒等null做判断，也可用抛出异常
//            // throw new Exception("Error locking file!");
//            return null;
//        }
//        fclose($file);
//        return $res;
//    }

}