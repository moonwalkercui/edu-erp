<?php

namespace app\common\model;

// 编排计划表
class SchedulePlan extends BaseModel
{
    protected $table = 'duty_schedule_plan';

    function setWeekdaysAttr($value)
    {
        return implode(",", $value);
    }

    function setTimesAttr($value)
    {
        $value = str_replace('，', ',', $value);
        $value = str_replace(['：', '.'], ':', $value);
        return $value;
    }

    function getStartAmAttr($value)
    {
        return $value ? substr($value, 0, -3) : '00:00';
    }

    function getEndAmAttr($value)
    {
        return $value ? substr($value, 0, -3) : '00:00';
    }

    function getStartPmAttr($value)
    {
        return $value ? substr($value, 0, -3) : '00:00';
    }

    function getEndPmAttr($value)
    {
        return $value ? substr($value, 0, -3) : '00:00';
    }

    // 获取时间间隔块
    static function getTimeBlock(self $plan)
    {
//        $plan = static::find($plan_id);

        if ($plan['times']) {
            return explode(',', $plan['times']);
        } else {
            $func = function ($t) {
                return $t[0] * 60 + $t[1];
            };
            $fmt = function ($t) {
                return [intval($t / 60), $t % 60];
            };
            $t1 = $func(explode(':', $plan['start_am']));
            $t2 = $func(explode(':', $plan['end_am']));
            $t3 = $func(explode(':', $plan['start_pm']));
            $t4 = $func(explode(':', $plan['end_pm']));

            $block = [];
            if ($plan['start_am'])
                for ($i = $t1; $i < $t2; $i += $plan['duration']) {
                    list($h, $m) = $fmt($i);
                    $block[] = ($h < 10 ? '0' . $h : $h) . ':' . ($m < 10 ? '0' . $m : $m);
                }
            if ($plan['start_pm'])
                for ($i = $t3; $i < $t4; $i += $plan['duration']) {
                    list($h, $m) = $fmt($i);
                    $block[] = ($h < 10 ? '0' . $h : $h) . ':' . ($m < 10 ? '0' . $m : $m);
                }
            return $block;
        }
    }
    // 通过日期获取员工的计划id
//    static function getPlanIdByDate($date)
//    {
//        $w = date('w', strtotime($date));
//        return self::getIdsByWeek($w);
//    }
//    static function getTimeBlockBy
//    static function getAllTimeBlock()
//    {
//        $res = [];
//        foreach (self::getAll() as $p) {
//            $res[$p['id']] = self::getTimeBlock($p);
//        }
//        return $res;
//    }
    /*
     * 获取员工能够在天内的值班数据，包括可接单数量
     * @staff_id int 员工id
     * @date string 日期
     * @offdutys array 一定时间内的请假记录
     * @apptlog_inweek 一定时间内的预约记录
     * */
    static function getStaffWorkDayData(int $staff_id, string $date, $offduty_inweek, $apptlog_inweek)
    {
        $sch_plan_ids = Schedule::getStaffPlanIds($staff_id);
        $w = date('w', strtotime($date));
        $plans = self::where("FIND_IN_SET($w, weekdays)")->where('id', 'in', $sch_plan_ids)->select();

        $applog = [];
        foreach ($apptlog_inweek as $a) {
            if ($a['staff_id'] == $staff_id) {
                $applog[] = $a;
            }
        }

        $res = [];
        foreach ($plans as $p) {
            if (StaffOffduty::checkOffduty($offduty_inweek, $date, $staff_id) == false) { // 检查请假
                $blocks = self::wrapTimeBlock(self::getTimeBlock($p), $date, $applog);
                foreach ($blocks as $b) {
                    $res[$b['time']] = $b;
                }
            }
        }
        ksort($res);
        $res = array_values($res);
        $wd = ["日", "一", "二", "三", "四", "五", "六"];
        return [
            'date' => $date,
            'week' => '周' . $wd[$w],
            'count' => self::countValidateBlock($res),
//            'blocks' => $res // 暂不返回结果
        ];
    }

    // 重新包装时间块
    static function wrapTimeBlock($blocks, $date, $applog)
    {
        $res = [];
        foreach ($blocks as $b) {
            $temp['time'] = $b;
            $temp['out'] = false; // 未约
            foreach ($applog as $a) {
                // 时间点和预约时间只差，在60秒内就算约走了这个时间点
                if (abs(strtotime($date . ' ' . $b) - strtotime($a['visit_time'])) <= 60 || strtotime($date . ' ' . $b) < time()) {
                    $temp['out'] = true;  // 不能约了
                    break;
                }
            }
            $res[] = $temp;
        }
        return $res;
    }

    // 获取可以预约的数量， 使用wrapTimeBlock返回的结果
    static function countValidateBlock($blocks)
    {
        $i = 0;
        foreach ($blocks as $v) {
            $i += ($v['out'] == true ? 0 : 1);
        }
        return $i;
    }

    // 获取当天的值班计划
    static function getIdsByWeek($w)
    {
        return self::where("FIND_IN_SET($w, weekdays)")->column('id');
    }

    // 全部的
    static function getAll()
    {
        return self::all();
    }

    // 一周全部的计划安排id
    static function getAllIds()
    {
        return array_column(self::getAll()->toArray(), 'id');
    }

    static function getWeekDays($plan_ids, $friendly = true)
    {
        $w = self::where('id', 'in', $plan_ids)->column('weekdays');
        $res = implode(',', $w);
        $res = array_unique(explode(',', $res));
        if ($friendly) {
            $arr = ['', "一", "二", "三", "四", "五", "六", "日"];
            $word = [];
            foreach ($res as $v) {
                $word[] = '周' . $arr[$v];
            }
            return $word;
        } else {
            return $res;
        }
    }
}