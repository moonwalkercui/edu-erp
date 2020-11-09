<?php

namespace app\common\model;

// 编排记录表
class Schedule extends BaseModel
{
    use BelongsToStaff;
    protected $table = 'duty_schedule';

    // 通过值班规则查找值班员工
//    static function getStaffIdsByPlanIds($plan_ids, $store_id = null)
//    {
//        if(count($plan_ids) == 0) return [];
//        $where = [];
//        $res = [];
//        if($store_id) {
//            $where = compact('store_id');
//        }
//        // 获取店铺的员工的值班计划列表
//        $staffs = self::field('staff_id,plan_ids')->where($where)->select();
//        if(count($staffs) == 0) return $res;
//
//        foreach ($staffs as $s) {
//            $p1 = explode(",", $s['plan_ids']);
//            foreach ($plan_ids as $p) {
//                if(in_array($p, $p1))
//                    $res[] = $s['staff_id'];
//            }
//        }
//        return array_unique($res);
//    }
    // 获取员工的编排计划id
    static function getStaffPlanIds(int $staff_id)
    {
        $ids = self::where(compact('staff_id'))->value('plan_ids');
        return $ids ? explode(',', $ids) : [];
    }
    static function getStaffComeAndLeaveTime(int $staff_id, string $date = null)
    {
        $time = $date ? strtotime($date) : time();
        $w = date('w', $time);
        $w = $w == 0 ? 7 : $w;
        $plan_ids = self::getStaffPlanIds($staff_id);
        if(empty($plan_ids)) return null;
        $sch = SchedulePlan::where('id','in', $plan_ids)->where("FIND_IN_SET({$w}, `weekdays`)")->find();
        return $sch ? [
            $sch['start_am'],
            $sch['end_pm']
        ] : null;
    }
    static function getNameStrByIds($ids)
    {
        $names = self::where('id', 'in', $ids)->column('name');
        return implode(',', $names);
    }
}