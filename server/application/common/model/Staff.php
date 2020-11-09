<?php

namespace app\common\model;
use think\model\concern\SoftDelete;

class Staff extends BaseModel
{
    use SoftDelete;
    protected $table = 'staff';
    protected $deleteTime = 'delete_time';
    static function getKV($where = [])
    {
        return self::where('status', 1)->where($where)->column('name','id');
    }
    // 请假管辖人员id
    static function getLeaveManageStaffIds($id)
    {
        return self::where(compact('id'))->value('manage_staff_leave');
    }
    // 请款管辖人员id
    static function getPayoutManageStaffIds($id)
    {
        return self::where(compact('id'))->value('manage_staff_payout');
    }
}