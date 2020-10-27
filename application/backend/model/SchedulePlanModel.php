<?php

namespace app\backend\model;
class SchedulePlanModel extends \app\common\model\SchedulePlan
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $data = [
            [ 'type' => 'input', 'name' => 'name', 'label' => '计划名称', 'require' => true ],
            [ 'type' => 'checkbox', 'name' => 'weekdays', 'label' => '每周工作日', 'require' => true,  'options' => config('status.weekdays') ],
            [ 'type' => 'time', 'name' => 'start_am', 'label' => '上午开始时间', 'require' => true ],
            [ 'type' => 'time', 'name' => 'end_am', 'label' => '上午结束时间', 'require' => true],
            [ 'type' => 'time', 'name' => 'start_pm', 'label' => '下午开始时间', 'require' => true ],
            [ 'type' => 'time', 'name' => 'end_pm', 'label' => '下午结束时间', 'require' => true],
//            [ 'type' => 'number', 'name' => 'duration', 'label' => '每时间段时长(分钟)',  'require' => true, 'info' => '系统会根据上下午时间,以该时间段为单位进行自动分割' ],
//            [ 'type' => 'number', 'name' => 'service_count', 'label' => '每时间段顾客数',  'require' => true ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
    public function makeFromData2($default_value = [])
    {
        $data = [
            [ 'type' => 'input', 'name' => 'name', 'label' => '计划名称', 'require' => true ],
            [ 'type' => 'checkbox', 'name' => 'weekdays', 'label' => '每周工作日',  'require' => true, 'options' => config('status.weekdays') ],
            [ 'type' => 'input', 'name' => 'times', 'label' => '自定义开始时间点', 'require' => true , 'info' => '多个时间点用,分开。例 8:30,10:00,13:00' ],
//            [ 'type' => 'number', 'name' => 'duration', 'label' => '每时间段时长(分钟)',  'require' => true],
//            [ 'type' => 'number', 'name' => 'service_count', 'label' => '每时间段顾客数',  'require' => true ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
    public function makeFromData3($default_value = [])
    {
        $data = [
            [ 'type' => 'input', 'name' => 'name', 'label' => '计划名称', 'require' => true ],
            [ 'type' => 'checkbox', 'name' => 'weekdays', 'label' => '每周工作日',  'require' => true, 'options' => config('status.weekdays') ],

        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}