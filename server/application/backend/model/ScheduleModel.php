<?php

namespace app\backend\model;
use app\common\model\SchedulePlan;
use app\common\model\Staff;

class ScheduleModel extends \app\common\model\Schedule
{
    use FormDateTrait;

    public function makeFromData($default_value = [])
    {
        $plans = SchedulePlan::all();
        $plan_list = [];
        foreach ($plans as $v) {
            $plan_list[$v['id']] = $v['name'] . ' 工作日:'. $v['weekdays']
//                .($v['times'] ?
//                    ' 节点:'. $v['times'] :
//                    ' 间隔:'. $v['duration'].'分钟' )
            ;
        }

        $data = [
            [
                'type' => 'select',
                'name' => 'staff_id',
                'label' => '选择员工',
                'options' => Staff::column("name", 'id'),
                'require' => true
            ],
//            [
//                'type' => 'select',
//                'name' => 'project_id',
//                'label' => '选择项目',
//                'options' => Project::column("name", 'id'),
//                'require' => true
//            ],
            [
                'type' => 'mselect',
                'name' => 'plan_ids',
                'label' => '设置时间计划',
                'options' => $plan_list,
                'require' => true
            ],
        ];
        return self::mergeDefaultFormData($data, $default_value);
    }
}