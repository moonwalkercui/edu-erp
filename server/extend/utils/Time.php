<?php
namespace utils;

class Time
{
    /**
     * 今天开始的Y-m-d H:i:s
     *
     * @return string
     */
    public static function beginToday()
    {
        return date('Y-m-d').' 00:00:00';
    }

    /**
     * 今天结束的Y-m-d H:i:s
     *
     * @return string
     */
    public static function endToday()
    {
        return date('Y-m-d').' 23:59:59';
    }

    /**
     * 本周的开始日期
     *
     * @param bool $His     是否展示时分秒 默认true
     * @param bool $time    时间戳
     *
     * @return false|string
     */
    public static function beginWeek($His = true, $time=null)
    {
        if($time) {
            $timestamp = mktime(0, 0, 0, date('m', $time), date('d', $time) - date('w', $time) + 1, date('Y', $time));
        } else {
            $timestamp = mktime(0, 0, 0, date('m'), date('d') - date('w') + 1, date('Y'));
        }
        return $His ? date('Y-m-d H:i:s', $timestamp) : date('Y-m-d', $timestamp);
    }

    /**
     * 本周的结束日期
     *
     * @param bool $His     是否展示时分秒 默认true
     *
     * @return false|string
     */
    public static function endWeek($His = true, $time=null)
    {
        if($time) {
            $timestamp = mktime(23, 59, 59, date('m', $time), date('d', $time) - date('w', $time) + 7, date('Y', $time));
        } else {
            $timestamp = mktime(23, 59, 59, date('m'), date('d') - date('w') + 7, date('Y'));
        }
        return $His ? date('Y-m-d H:i:s', $timestamp) : date('Y-m-d', $timestamp);
    }

    /**
     * 本月的开始日期
     *
     * @param bool $His     是否展示时分秒 默认true
     *
     * @return false|string
     */
    public static function beginMonth($His = true)
    {
        $timestamp = mktime(0, 0, 0, date('m'), 1, date('Y'));
        return $His ? date('Y-m-d H:i:s', $timestamp) : date('Y-m-d', $timestamp);
    }

    /**
     * 本月的结束日期
     *
     * @param bool $His     是否展示时分秒 默认true
     *
     * @return false|string
     */
    public static function endMonth($His = true)
    {
        $timestamp = mktime(23, 59, 59, date('m'), date('t'), date('Y'));
        return $His ? date('Y-m-d H:i:s', $timestamp) : date('Y-m-d', $timestamp);
    }

    /**
     * 几年的开始日期
     *
     * @param bool $His     是否展示时分秒 默认true
     *
     * @return false|string
     */
    public static function beginYear($His = true)
    {
        $timestamp = mktime(0, 0, 0, 1, 1, date('Y'));
        return $His ? date('Y-m-d H:i:s', $timestamp) : date('Y-m-d', $timestamp);
    }

    /**
     * 本月的结束日期
     *
     * @param bool $His     是否展示时分秒 默认true
     *
     * @return false|string
     */
    public static function endYear($His = true)
    {
        $timestamp = mktime(23, 59, 59, 12, 31, date('Y'));
        return $His ? date('Y-m-d H:i:s', $timestamp) : date('Y-m-d', $timestamp);
    }
}