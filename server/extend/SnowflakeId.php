<?php
/**
 * 全局生成UUID
 *  // 调用 方法
    $work1 = new SnowflakeId(1);
    for($i=0; $i<10;$i++) {
        echo $i."--".$work1->nextId()."<br/>";
    }
 */
class SnowflakeId
{
    /** 开始时间截 (2018-01-01) */
    const twepoch = 1514736000000;

    /** 机器id所占的位数 */
    const workerIdBits = 10;

    //支持的最大机器id，结果是1023 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
    const maxWorkerId = (-1 ^ (-1 << self::workerIdBits));

    //序列在id中占的位数
    const sequenceBits = 12;

    //机器ID向左移12位
    const workerIdShift = self::sequenceBits;

    //时间截向左移22位(10+12)
    const timestampLeftShift = self::workerIdBits + self::sequenceBits;

    //序列号值的最大值，这里为4095 (0b111111111111=0xfff=4095)
    const sequenceMask = (-1 ^ (-1 << self::sequenceBits));

    //工作机器ID(0~1023)：默认0
    private $workerId = 0;

    //毫秒内序列(0~4095)：标识符，常驻内存
    static $sequence = 0;

    //上次生成ID的时间截
    static $lastTimestamp = -1;

    /***
     * 构造函数：设置当前机器ID 或者其他逻辑
     * SnowflakeIdWorker constructor.
     * @param $workerId
     */
    public function __construct($workerId = 1)
    {
        //转换类型
        $workerId = (int)$workerId;

        //判断参数合法性
        if ($workerId < 0 || $workerId > self::maxWorkerId) {
            die('error...');
        }

        //设置当前机器id
        $this->workerId = $workerId;
    }


    public function nextId()
    {
        //获取当前毫秒时间戳
        $timestamp = $this->timeGen();
        //获取上一次生成id时的毫秒时间戳
        $lastTimestamp = self::$lastTimestamp;

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if ($timestamp < $lastTimestamp) {
            die('error...');
        }

        //如果是同一毫秒内生成的，则进行毫秒序列化
        if ($timestamp == $lastTimestamp) {
            //获取当前序列号值
            self::$sequence = (self::$sequence + 1) & self::sequenceMask;
            //毫秒序列化值溢出（就是超过了4095）
            if (self::$sequence == 0) {
                //阻塞到下一秒，获得新的时间戳
                $timestamp = $this->tilNextMillis($lastTimestamp);
            }
        } //如果不是同一毫秒，那么重置毫秒序列化值
        else {
            self::$sequence = 0;
        }

        //重置上一次生成的时间戳
        self::$lastTimestamp = $timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return
            //时间戳左移 22 位
            (($timestamp - self::twepoch) << self::timestampLeftShift) |
            //机器id左移 12 位
            ($this->workerId << self::workerIdShift) |
            //或运算序列号值
            self::$sequence;
    }

    /****
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param int $lastTimestamp 上次生成ID的时间截
     * @return float 当前毫秒时间戳
     */
    private function tilNextMillis($lastTimestamp)
    {
        //重新获取当前时间戳
        $timestamp = $this->timeGen();
        //如果等于上一次获取的时间戳，仍然重新获取
        while ($timestamp <= $lastTimestamp) {
            $timestamp = $this->timeGen();
        }
        //返回新的时间戳
        return $timestamp;
    }


    /***
     * 返回当前毫秒时间戳
     * @return float
     */
    private function timeGen()
    {
        return (float)sprintf("%.0f", microtime(true) * 1000);
    }

}