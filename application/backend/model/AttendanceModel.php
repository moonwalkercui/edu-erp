<?php
namespace app\backend\model;

use app\common\model\Attendance;

class AttendanceModel extends Attendance
{
    use FormDateTrait;
    public function makeFromData1($default_value = [])
    {
        $data = [
            [
                'type' => 'textarea',
                'name' => 'late_reason',
                'label' => '迟到补签原因',
                'verify' => true,
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
    public function makeFromData2($default_value = [])
    {
        $data = [
            [
                'type' => 'textarea',
                'name' => 'leave_reason',
                'label' => '早退补签原因',
                'verify' => true,
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
    public function makeFromData3($default_value = [])
    {
        $data = [
            [
                'type' => 'textarea',
                'name' => 'verify_remark',
                'label' => '审批备注',
                'verify' => true,
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }

}