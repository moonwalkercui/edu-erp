<?php

namespace app\backend\controller;

use app\backend\model\ScheduleModel;
use app\backend\model\SchedulePlanModel;
use app\common\model\SchedulePlan;
use app\common\model\Staff;
use app\common\model\StaffOffduty;
use think\Db;

class Schedule extends Base
{
    // 项目编排计划
    function plan()
    {
        $list = SchedulePlanModel::paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]);
        $this->assign('data', [
            'title' => '值班规则',
//            'info' => '每个老师可以有多种值班规则；顾客预约时会按节点进行预约，每个节点可以设置服务人数。',
            'info' => '在值班安排中使用中的规则不能直接删除',
            'collection' => $list,
            'thead' => ['ID', '名称', '每周工作日' ,'工作时间', //'每时间段时长(分钟)', '每时间段顾客数',''
            ],
            'fields' => ['id', 'name', 'weekdays',
                function ($row) {
                    echo  $row->times ?
                        $row->times :
                        '上午'. $row->start_am . '~' . $row->end_am . ' 下午' . $row->start_pm . '~' . $row->end_pm;
                },
//                'duration', 'service_count',
//                function($row){
//                    return $row->times ?
//                        '<a class="ui tiny label" data-title="修改" data-url="'.url('editPlan2').'" onclick="openWin(this, \''.$row['id'].'\', 520)">修改</a> ':
//                        '<a class="ui tiny label" data-title="修改" data-url="'.url('editPlan').'" onclick="openWin(this, \''.$row['id'].'\', 680)">修改</a> ';
//                }
                ],
            'buttons' => [
                ['title' => '修改', 'url' => url('editPlan'), 'height' => '530'],
//                ['title' => '预览时间点', 'url' => url('showBlock')],
                ['title' => '删除', 'url' => url('deletePlan'), 'onclick' => 'delOne'],
            ],
            'mbuttons' => [
//                ['title' => '按时间段添加', 'url' => url('editPlan'), 'height' => '530'],
//                ['title' => '按开始时间点添加', 'url' => url('editPlan2'), 'height' => '520'],
                ['title' => '添加', 'url' => url('editPlan'), 'height' => '530'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    function showBlock()
    {
        $id = input('id');
        $plan = SchedulePlan::find($id);
        $blocks = SchedulePlan::getTimeBlock($plan);
        $count = count($blocks);
        $html = "<div style='padding: 10px;'><i> 每段时长{$plan['duration']}分钟，共{$count}段，开始时间点分别为：</i></div>";
        foreach ($blocks as $v) {
            $html .= "<button style='margin: 10px; border-radius: 5px; font-size: 120%; border:none;'>{$v}</button>";
        }
        echo "<div>{$html}</div>";
    }

    // 新建与编辑时间段计划
    function editPlan()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if ($id = input('id')) {
                $this->modelUpdate(SchedulePlanModel::class, $data, ['id' => $data['id']], 'schedule_save_plan', '修改编排时间段计划');
            } else {
                $this->modelCreate(SchedulePlanModel::class, $data, 'schedule_save_plan', '添加编排时间段计划');
            }
        } else {
            return $this->fetchFormPageHtml(SchedulePlanModel::class, __FUNCTION__);
        }
    }

//    // 时间点计划
//    function editPlan2()
//    {
//        if (request()->isPost()) {
//            $data = input('post.');
//            if ($id = input('id')) {
//                $this->modelUpdate(SchedulePlanModel::class, $data, ['id' => $data['id']], 'schedule_save_plan2', '修改编排开始时间点计划');
//            } else {
//                $this->modelCreate(SchedulePlanModel::class, $data, 'schedule_save_plan2', '添加编排开始时间点计划');
//            }
//        } else {
//            return $this->fetchFormPageHtml(SchedulePlanModel::class, __FUNCTION__, [], 'makeFromData2');
//        }
//    }
//
//    // 只有周计划
//    function editPlan3()
//    {
//        if (request()->isPost()) {
//            $data = input('post.');
//            if ($id = input('id')) {
//                $this->modelUpdate(SchedulePlanModel::class, $data, ['id' => $data['id']], 'schedule_save_plan3', '修改值班规则');
//            } else {
//                $this->modelCreate(SchedulePlanModel::class, $data, 'schedule_save_plan3', '添加值班规则');
//            }
//        } else {
//            return $this->fetchFormPageHtml(SchedulePlanModel::class, __FUNCTION__, [], 'makeFromData3');
//        }
//    }

    function deletePlan()
    {
        $id = input('id/d');
        $find = Db::name('schedule')->where("FIND_IN_SET({$id},plan_ids)")->find();
        if ($find) return 0;
        echo SchedulePlanModel::destroy($id) ? 1 : 0;
    }

    // 值班安排
    function staffSchedule()
    {
        $list = ScheduleModel::paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]);
        $this->assign('data', [
            'title' => '值班安排',
            'info' => '每个老师可以设置多个值班计划，但要注意避免在值班时间上出现冲突',
            'collection' => $list,
            'thead' => ['ID', '老师', '值班计划'],
            'fields' => ['id',
                function ($row) {
                    echo $row->staff->name;
                },
                function ($row) {
                    $plans = SchedulePlan::where('id','in',$row['plan_ids'])->select();
                    $plan_list=[];
                    foreach ($plans as $v) {
                        $plan_list[] = $v['name'] . ' 工作日:'. $v['weekdays']
//                            .($v['times'] ?
//                                ' 节点:'. $v['times'] :
//                                ' 间隔:'. $v['duration'].'分钟' )
                        ;
                    }
                    echo implode('<br/>', $plan_list);
                }],
            'buttons' => [
                ['title' => '修改', 'url' => url('editSchedule'), 'height' => '500'],
                ['title' => '删除', 'url' => url('deleteSchedule'), 'onclick' => 'delOne'],
            ],
            'mbuttons' => [
                ['title' => '值班计划管理', 'url' => url('plan'), 'onclick' => 'openBigWin', 'class_name'=>''],
                ['title' => '排班', 'url' => url('schedule/editSchedule'), 'height' => '500'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    // 新建与编辑计划
    function editSchedule()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if(empty($data['staff_id']) && !$data['staff_id'])
                $this->error('请选择老师');
            if ($id = input('id')) {
                $this->modelUpdate(ScheduleModel::class, $data, ['id' => $data['id']], 'schedule_save_staff', '修改值班');
            } else {
                $find = ScheduleModel::where('staff_id', $data['staff_id'])->find();
                if( $find )  $this->error('请不要重复设置');
                $this->modelCreate(ScheduleModel::class, $data, 'schedule_save_staff', '添加值班');
            }
        } else {
            return $this->fetchFormPageHtml(ScheduleModel::class, __FUNCTION__);
        }
    }

    function deleteSchedule()
    {
        $id = input('id/d');
        echo ScheduleModel::destroy($id) ? 1 : 0;
    }

    function loadPlan()
    {
        return json(SchedulePlanModel::column('name','id'));
    }

    function offduty()
    {
        $list = StaffOffduty::paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]);
        $this->assign('data', [
            'title' => '休假记录',
            'info' => '休假中的人员在微信预约列表中不可见',
            'collection' => $list,
            'thead' => ['ID', '老师', '日期', '添加时间'],
            'fields' => ['id',
                function ($row) {
                    echo $row->staff->name;
                },
                function ($row) {
                    echo $row->date;
                }, 'add_time'],
            'buttons' => [
                ['title' => '作废', 'url' => url('deleteOffduty'), 'onclick' => 'delOne'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    function deleteOffduty()
    {
        $id = input('id/d');
        echo StaffOffduty::destroy($id) ? 1 : 0;
    }
}