<?php

namespace app\backend\controller;

use app\backend\model\CourseModel;
use app\backend\model\CourseScheduleModel;
use app\backend\model\CourseTimesModel;
use app\common\model\Clazz;
use app\common\model\ClazzEvent;
use app\common\model\CourseSchedule;
use app\common\model\CourseTimes;
use app\common\model\Grade;
use app\common\model\ZoneTask;

class Course extends Base
{
    // 列表视图
    function getList()
    {
        $where = [];
        $order = 'date desc';
        if (input('staff_id')) $where[] = ['staff_id', '=', input('staff_id')];
//        if (input('grade_id')) $where[] = ['grade_id', '=', input('grade_id')];
        if (input('assistant_id')) $where[] = ['assistant_id', '=', input('assistant_id')];
        if (input('clazz_id')) $where[] = ['clazz_id', '=', input('clazz_id')];
        if (input('section_id')) $where[] = ['section_id', '=', input('section_id')];

        $start_date = input('start_date');
        $end_date = input('end_date');
        if($start_date && $end_date) {
            $where[] = ['date', 'between', [$start_date, $end_date]];
        } elseif($start_date && !$end_date) {
            $where[] = ['date', '>=', $start_date];
            $order = 'date asc';
        } elseif(!$start_date && $end_date) {
            $where[] = ['date', '<=', $end_date];
        }

        $staffs = \app\common\model\Staff::column('name', 'id');
        $this->assign('data', [
            'title' => '课程表',
            'collection' => CourseModel::where($where)->order($order)->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['ID', '班级', '日期', '课次', '授课老师','助教','章节', '状态'], // '课时','年级',
            'select_type' => 2,
            'select_actions' => [
                ['title' => '批量删除', 'onclick' => 'delMulti', 'url' => url('mulDelCourse'), 'class_name'=>'orange'],
            ],
            'fields' => ['id',
//                function ($row) { return $row->grade->name ?? ''; },
                function ($row) {
                    return $row->clazz->name ?? '';
                },
                function ($row) {
                    return $row['date'] . ' ('. weekdayText(date('w', strtotime($row['date']))) .')';
                },
                function ($row) {
                    return $row->times->name ?? '';
                },
                function ($row) {
                    return $row->staff->name ?? '';
                },
                function ($row) {
                    return $row->assistant->name ?? '';
                },
                function ($row) {
                    return $row->section->title ?? '<span style="color: red">未设置</span>';
                },
                'status_text'
            ],
            'buttons' => [
                ['title' => '编辑', 'onclick' => 'openBigWin', 'url' => url('saveCourse')],
                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('delCourse'), 'permission_id' => 141],
            ],
            'mbuttons' => [
                ['title' => '日历', 'onclick' => 'redirect', 'url' => url('calendar'), 'class_name' => ''],
                ['title' => '课次管理', 'onclick' => 'openBigWin', 'url' => url('courseTimes'), 'permission_id' => 139],
                ['title' => '添加单课', 'onclick' => 'openBigWin', 'url' => url('saveCourse'), 'permission_id' => 140],
                ['title' => '排课', 'onclick' => 'openBigWin', 'url' => url('schedule'), 'permission_id' => 140],
            ],
            'searcher' => [
//                ['type' => 'select', 'name' => 'grade_id', 'options' => Grade::column('name', 'id'), 'placeholder' => '选择年级'],
                ['type' => 'select', 'name' => 'clazz_id', 'options' => Clazz::column('name', 'id'), 'placeholder' => '选择班级'],
                ['type' => 'select', 'name' => 'staff_id', 'options' => $staffs, 'placeholder' => '选择老师'],
                ['type' => 'select', 'name' => 'assistant_id', 'options' => $staffs, 'placeholder' => '选择助教'],
                ['type' => 'select', 'name' => 'section_id', 'options' => \app\common\model\Section::column('title', 'id'), 'placeholder' => '选择章节'],
                ['type' => 'date', 'name' => 'start_date', 'placeholder' => '开始日期'],
                ['type' => 'date', 'name' => 'end_date', 'placeholder' => '结束日期'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    // 课表日历视图
    function calendar()
    {
        $time = input('time', time());
        switch (input('type')) {
            case 'pre':
                $the_time = strtotime('-1 weeks', $time);
                break;
            case 'next':
                $the_time = strtotime('+1 weeks', $time);
                break;
            default:
                $the_time = $time;
        }
        $this->assign('time', $the_time);

        // 按日期查询
        $search_dates = [
            [ 'title' => '前六个月', 'time' => strtotime('-6 months') ],
            [ 'title' => '前三个月', 'time' => strtotime('-3 months') ],
            [ 'title' => '上个月', 'time' => strtotime('-1 month') ],
            [ 'title' => '本周', 'time' => time() ],
            [ 'title' => '下个月', 'time' => strtotime('+1 month') ],
            [ 'title' => '后三个月', 'time' => strtotime('+3 months') ],
            [ 'title' => '后六个月', 'time' => strtotime('+6 months') ]
        ];
        $this->assign('search_dates', $search_dates);

        // 周里的日期
        $week_days = getWeekDays($the_time);
        $the_monday = $week_days[1];
        $the_sunday = $week_days[7];

        $where = [];
        $where[] = ['date', 'between', [$the_monday, $the_sunday]];

        $list = CourseModel::with('clazz,staff,times,section,assistant')->where($where)->order('times_id')->select();

        $data = [];
        if (input('showtype') == 'section') {

            $times = [];
            foreach ($list as $v) {
                foreach ($week_days as $w) {
                    if ($v->staff && $w == $v->date) {
                        $data[$v->times_id][$w][] = $v;
                        $times[$v->times_id] = $v->times->name;
                    }
                }
            }
            $this->assign('times', $times);
        } else {
            $teachers = [];
            $assistants = [];
            foreach ($list as $v) {
                foreach ($week_days as $w) {
                    if ($v->staff && $w == $v->date) {
                        $data[$v->staff_id][$w][] = $v;
                        $teachers[$v->staff_id] = $v->staff->name;
                    }
                }
            }
            foreach ($list as $v) {
                foreach ($week_days as $w) {
                    if ($v->assistant && $w == $v->date) {
                        $data[$v->assistant_id.'-a'][$w][] = $v;
                        $assistants[$v->assistant_id.'-a'] = $v->assistant->name;
                    }
                }
            }
            $this->assign('teachers', $teachers);
            $this->assign('assistants', $assistants);
        }

        $this->assign('data', $data);
        $this->assign('week_days', $week_days);
        return $this->fetch();
    }

    function saveCourse()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $data['creator_id'] = session('login_id');
            $data['edit_time'] = now();
            if ($id = input('id')) {
                $this->modelUpdate(CourseModel::class, $data, ['id' => $data['id']], 'course_saving', '修改课程');
            } else {
                $data['add_time'] = now();
                $this->modelCreate(CourseModel::class, $data, 'course_saving', '添加课程', function ($item) {
                    ClazzEvent::addOne(
                        $item->times->name . $item->staff->name . "老师给学生们上了一节" . $item->section->title,
                        $item['clazz_id'],
                        $item['staff_id'],
                        ClazzEvent::TYPE_COURSE,
                        $item['id'],
                        null,
                        $item['date']
                    );
                });
            }
        } else {
            return $this->fetchFormPageHtml(CourseModel::class, __FUNCTION__);
        }
    }

//    // 排课计划
//    function schedulePlan()
//    {
//        $this->assign('data', [
//            'title' => '排课计划',
//            'info' => '排课计划完成后，需要点生成课程，才可以批量生成课程。',
//            'collection' => CourseSchedule::order('id desc')->paginate(20),
//            'thead' => ['ID', '班级', '老师', '课次', '频率类型', '间隔', '日期值'],
//            'fields' => ['id',
//                function($row) {return $row->clazz ? $row->clazz->name :''; },
//                function($row) {return $row->staff ? $row->staff->name :''; },
//                function($row) {return $row->times ? $row->times->name :''; },
//                'loop_type_text',
//                'loop_interval',
//                'loop_values',
//
//            ],
//            'buttons' => [
//                ['title' => '编辑', 'url' => url('saveSchedule')],
//                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('delSchedule')],
//                ['title' => '生成', 'url' => url('saveSchedule')],
//            ],
//            'mbuttons' => [
//                ['title' => '添加日频率', 'height'=> 600, 'url' => url('saveSchedule', ['type' => CourseSchedule::TYPE_DAY])],
//                ['title' => '添加周频率', 'height'=> 600, 'url' => url('saveSchedule', ['type' => CourseSchedule::TYPE_WEEK])],
//                ['title' => '添加月频率', 'height'=> 600, 'url' => url('saveSchedule', ['type' => CourseSchedule::TYPE_MONTH])],
//            ],
//        ]);
//        return $this->fetch('public/table_builder');
//    }

//    function saveSchedule()
//    {
//        $id = input('id');
//        if (request()->isPost()) {
//            $data = input('post.');
//            $data['loop_interval'] = isset($data['loop_interval']) ? intval($data['loop_interval']) : 0;
//            $data['loop_values'] = isset($data['loop_values']) ? implode(',', $data['loop_values']) : null;
//            if ($id) {
//                $this->modelUpdate(CourseScheduleModel::class, $data, ['id' => $data['id']], 'course_schedule_saving', '修改排课计划');
//            } else {
//                $this->modelCreate(CourseScheduleModel::class, $data, 'course_schedule_saving', '添加排课计划');
//            }
//        } else {
//            $type = input('type');
//            switch ($type) {
//                case CourseSchedule::TYPE_DAY:
//                    $act = 'makeFromData1';
//                    break;
//                case CourseSchedule::TYPE_WEEK:
//                    $act = 'makeFromData2';
//                    break;
//                case CourseSchedule::TYPE_MONTH:
//                    $act = 'makeFromData3';
//                    break;
//                default:
//                    $this->error('类型错误');
//            }
//            $data = [];
//            if ($id) {
//                $data = CourseScheduleModel::find($id);
//            }
//            return $this->fetchFormPageHtml(CourseScheduleModel::class, url(__FUNCTION__, ['type' => $type]), $data, $act);
//        }
//    }

//    function delSchedule()
//    {
//        $id = input('id/d');
//        echo CourseTimes::destroy($id) ? 1 : 0;
//    }

    function delCourse()
    {
        echo CourseModel::destroy(input('id/d')) ? 1 : 0;
    }
    function mulDelCourse()
    {
        echo CourseModel::destroy(input('ids/a')) ? 1 : 0;
    }

    function courseTimes()
    {
        $this->assign('data', [
            'title' => '课次列表',
            'collection' => CourseTimes::paginate(20),
            'thead' => ['ID', '名称'],
            'fields' => ['id', 'name'],
            'buttons' => [
                ['title' => '编辑', 'url' => url('saveTimes'), 'permission_id' => 142],
                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('delTimes'), 'permission_id' => 143],
            ],
            'mbuttons' => [
                ['title' => '添加', 'url' => url('saveTimes'), 'permission_id' => 142],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    function saveTimes()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if ($id = input('id')) {
                $this->modelUpdate(CourseTimesModel::class, $data, ['id' => $data['id']], 'only_name', '修改课次');
            } else {
                $this->modelCreate(CourseTimesModel::class, $data, 'only_name', '添加课次');
            }
        } else {
            return $this->fetchFormPageHtml(CourseTimesModel::class, __FUNCTION__);
        }
    }

    function delTimes()
    {
        $id = input('id/d');
        echo CourseTimes::destroy($id) ? 1 : 0;
    }

    function schedule()
    {
        if (request()->isPost()) {
            $data = input('post.');
//            halt($data);
            $check_empty = function ($items) {
                foreach ($items as $item) {
                    if ($item === '') return true;
                }
                return false;
            };

            if(empty($data['clazz']) || count($data['clazz']) == 0) $this->error('请设置班级');
            if(empty($data['weekday']) || count($data['clazz']) == 0) $this->error('请设置星期');

            $clazz = $data['clazz'];
            $staff = $data['staff'];
            $assistant = $data['assistant'];
            $times = $data['times'];
            $start = $data['start'];
            $end = $data['end'];
            $interval = $data['interval']; // 可空
            $weekday = $data['weekday'];

            if($check_empty($clazz)) $this->error('班级不能为空');
            if($check_empty($staff)) $this->error('老师不能为空');
            if($check_empty($times)) $this->error('课次不能为空');
            if($check_empty($start)) $this->error('开始日不能为空');
            if($check_empty($end)) $this->error('结束日不能为空');

            $count = count($clazz);
            if ($count != count($weekday)) {
                $this->error('星期不能是空');
            }

            $items = [];
            $key = 0;
            for ($i = 0; $i < $count; $i++) {
                if(strtotime($start[$i]) > strtotime($end[$i]) ) $this->error("结束日{$end[$i]}不能在开始日{$start[$i]}之前");
                if($interval[$i] <= 0)  $this->error("周循环数不能小于1");
                $start_date = strtotime($start[$i]);
                $end_date = strtotime($end[$i]);

                $in_weeks = [];

                if($interval[$i] > 1) {
                    $next_date = $start_date;
                    while($next_date <= $end_date) {
                        $ww = date('W', $next_date); // 第几周
                        $in_weeks[] = date('w', $next_date) == 0 ? $ww + 1: $ww; // 星期天是一周的开始
                        $next_date = strtotime("+{$interval[$i]} weeks", $next_date);
                    }
                }
                $next_date = $start_date;
                while($next_date <= $end_date) {
                    $w = date('w', $next_date); // 周几
                    $ww = date('W', $next_date); // 第几周
                    if(in_array($w, $weekday[$i]) && (empty($in_weeks) || in_array($ww, $in_weeks)) ) {
                        $items[] = [
                            'key' => $key++,
                            'clazz_id' => $clazz[$i],
                            'staff_id' => $staff[$i],
                            'assistant_id' => $assistant[$i] ?? null,
                            'times_id' => $times[$i],
                            'date' => date('Y-m-d',$next_date),
                            'add_time' => now(),
                            'edit_time' => now(),
                            'creator_id' => session('login_id'),
//                            'week_day' => $w,
//                            'week_num' => $ww,
//                            'interval' => $interval[$i],
                        ];
                    }
                    $next_date = strtotime('+1 day', $next_date);
                }
            }
//            dump($items);die;
            if(empty($items)) $this->error('未检测到可生成数据');
            $crash = \app\common\model\Course::checkCrash($items);
            if($crash !== null) {
                $this->error($crash);
            }
            foreach ($items as &$v) {
                unset($v['key']);
            }
            $res = \app\common\model\Course::insertAll($items);
            $res>0 ? $this->success("成功生成{$res}节课, 别忘记设置授课章节哦") : $this->error('生成失败');

        } else {
            $data['clazz'] = Clazz::order('id desc')->field('id,name')->select();
            $data['staff'] = \app\common\model\Staff::field('id,name')->select();
            $data['times'] = CourseTimes::field('id,name')->select();
            $this->assign('data', $data);
            return $this->fetch();
        }
    }
}