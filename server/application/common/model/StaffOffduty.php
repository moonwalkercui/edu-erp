<?php

namespace app\common\model;

use think\Collection;
use think\model\concern\SoftDelete;

class StaffOffduty extends BaseModel
{
    use BelongsToStaff, SoftDelete;
    protected $table = 'staff_offduty';
    protected $deleteTime = 'delete_time';

    function type()
    {
        return $this->belongsTo(OffdutyType::class, 'type_id');
    }

    function getImagesAttr($value, $data)
    {
        $imgs = $value ? explode(',', $value) : [];
        foreach ($imgs as &$v) {
            $v = add_image_prefix($v);
        }
        return $imgs;
    }

    function getStatusTextAttr($value, $data)
    {
        $status = config('status.verify');
        return isset($status[$data['status']]) ? $status[$data['status']] : $data['status'];
    }

    // 过滤掉请假的
    static function filterOffduty($date, &$staff_ids)
    {
        $list = self::where(compact('date'))->column('staff_id'); // cache
        if (empty($list)) return null;
        foreach ($staff_ids as $k => $id) {
            if (in_array($id, $list))
                unset($staff_ids[$k]);
        }
    }

    static function getOffdutyStaffsByDates(array $dates, array $staff_ids): Collection
    {
        return self::where('date', 'in', $dates)->where('staff_id', 'in', $staff_ids)->field('date, staff_id')->select();
    }

    // 从休假记录中查询，员工是否在休假
    static function checkOffduty($offdutys, string $date, int $staff_id)
    {
        foreach ($offdutys as $od) {
            if ($staff_id == $od['staff_id'] && $od['date'] == $date) {
                return true;
            }
        }
        return false;
    }

    static function addOne($staff_id, $start_date, $end_date, $type, $reason, array $images = [])
    {
        $var = 3600 * 24;

        $start_ts = strtotime($start_date);
        $end_ts = strtotime($end_date);
        if ($start_ts > $end_ts) exception('时间设置错误');
        if ($start_ts < time() - $var) exception('开始时间设置错误');

        $days = [];
        for ($i = 0; $i <= ($end_ts - $start_ts); $i += $var) {
            $days[] = date('Y-m-d', $start_ts + $i);
        }

        // 已经请过假的日期
        if (self::where(compact('staff_id'))->where(function ($query) use ($start_date, $end_date) {
            $query->whereOr([[
                ['start_date', '>=', $start_date],
                ['start_date', '<=', $end_date],
            ],[
                ['end_date', '<=', $start_date],
                ['end_date', '>=', $end_date],
            ]]);
        })->count()) exception('不能重复请假');
        $insert = [
            'type_id' => $type,
            'staff_id' => $staff_id,
            'start_date' => $start_date,
            'start_date_time' => '00:00',
            'end_date' => $end_date,
            'end_date_time' => '23:59',
            'add_time' => now(),
            'edit_time' => now(),
            'reason' => $reason,
            'status' => 0, // 默认先自动审核 -1驳回 1 成功 0 申请中
            'images' => empty($images) ? '' : implode(',', $images)
        ];

        return self::insert($insert);
    }
    static function checkIsOffduty($staff_id, $date)
    {
        return self::where(compact('staff_id'))
                ->where('start_date', '<=', $date)
                ->where('end_date','>=', $date)
                ->where('status', 1)
                ->count() > 0;

    }
}