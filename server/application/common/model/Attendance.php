<?php
namespace app\common\model;

// 考勤记录
class Attendance extends BaseModel
{
    use BelongsToStaff;
    protected $table = 'attendance';
    function offduty()
    {
        return $this->belongsTo(StaffOffduty::class, 'offduty_id');
    }
    static function generateAllStaffLogByDate($date)
    {
        if(self::where('date', $date)->find() == null) {

            $list = Staff::where('status', 1)->select();
            $data = [];
            foreach ($list as $v) {
                $data[] = [
                    'staff_id' => $v['id'],
                    'status' => StaffOffduty::checkIsOffduty($v['id'], $date) ? 2 : 1,  // 判断请假
                    'date' => $date,
                ];
            }
            if(!empty($data)) {
                self::insertAll($data);
            }
        }
    }
}