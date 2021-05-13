<?php

namespace app\backend\controller;

use app\backend\model\AuthNode;
use app\backend\model\CourseModel;
use app\common\model\Attendance;
use app\common\model\Clazz;
use app\common\model\Payout;
use app\common\model\Question;
use app\common\model\Region;
use app\common\model\StaffOffduty;
use app\common\model\Zone;
use app\common\model\ZoneTask;
use think\facade\Config;

class Index extends Base
{
    public function index()
    {
        $this->assign('top_menu', AuthNode::getMenus(0));
        $this->assign('menu_pid', input('pid', null));
        return $this->fetch();
    }

    public function dashboard()
    {
        $serverInfo['系统名称'] = Config::get('base.app_name');
        $serverInfo['系统版本'] = Config::get('base.app_version');
        $serverInfo['服务器时间'] = date('Y年m月d日 H时i分');
        $serverInfo['版权所有'] = Config::get('base.app_powered');
        $serverInfo['系统描述'] = Config::get('base.app_description');
        $serverInfo['技术支持'] = Config::get('base.app_support');
//        $serverInfo['技术网址'] = Config::get('base.app_support_site');
//        $serverInfo['浏览器信息'] = $this->request->header('user-agent');
        $this->assign('serverInfo', $serverInfo);
        $this->assign('count', [
            '课时数' => \app\common\model\Section::count(),
            '练习数' => ZoneTask::count(),
            '点评数' => Zone::where('content', '<>', '')->count(),
            '学员数' => \app\common\model\Student::count(),
        ]);
//        $alarm2 = Withdrawal::where('status=0')->count();

        $manage_staff_leave = session('manage_staff_leave');
        $manage_staff_payout = session('manage_staff_payout');
        $this->assign('alarm', [
            ['title' => '请款审核', 'num' => Payout::where('staff_id', 'in', $manage_staff_payout)->where('status', 0)->count(), 'url' => url('staff/payout'), 'icon' => 'phone-incoming'],
            ['title' => '请假审核', 'num' => StaffOffduty::where('staff_id', 'in', $manage_staff_leave)->where('status', 0)->count(), 'url' => url('staff/offduty'), 'icon' => 'phone-incoming'],
            ['title' => '考勤审核', 'num' => Attendance::where('staff_id', 'in', $manage_staff_leave)->where('be_late|leave_early', 0)->count(), 'url' => url('staff/attendance'), 'icon' => 'phone-incoming'],
        ]);
//        $menu = AuthNode::getNodeMenuTree();
//        halt($menu);die;
        return $this->fetch();
    }

    public function region()
    {
        return Region::getListByPid(input('pid', 0));
    }

    function test()
    {
        $date = [
            [
                'title' => '前六个月',
                'date' => date('Y-m-d', strtotime('-3 months'))
            ],
            [
                'title' => '前三个月',
                'date' => date('Y-m-d', strtotime('-3 months'))
            ],
            [
                'title' => '上个月',
                'date' => date('Y-m-d', strtotime('-3 months'))
            ],
            [
                'title' => '本周',
                'date' => date('Y-m-d', time())
            ],
            [
                'title' => '下个月',
                'date' => date('Y-m-d', strtotime('-3 months'))
            ],
            [
                'title' => '后三个月',
                'date' => date('Y-m-d', strtotime('-3 months'))
            ],
            [
                'title' => '后六个月',
                'date' => date('Y-m-d', strtotime('-3 months'))
            ]
        ];

        dump($date);

//        $mix_date = \app\common\model\Course::where('status',1)->order('date')->value('date');
//        $max_date = \app\common\model\Course::where('status',1)->order('date desc')->value('date');
//        dump($mix_date);
//        dump($max_date);

//        $max = date('W', strtotime(date('Y-12-31')));
//        $year_weeks=[];
//        for ($i = 1; $i <= $max; $i++) {
//            $w = $i<10?'0'.$i:$i;
//            $the_time = strtotime("2020W{$w}");
//            if(strtotime($mix_date) < $the_time && $the_time > strtotime($max_date))
//                $year_weeks[] = [
//                    'title' => date('Y')."第{$i}周",
//                    'date' => date("Y-m-d", $the_time)
//                ];
//        }
//        dump($year_weeks);
    }

    // 定时每分钟的扫描执行任务：
    public function cronJobs()
    {
        $action = input('act');
//        switch ($action) {
//            case "per_day" :
//                CourseModel::generateTrainingNotice();
//                break;
//            default:
//                exception("非法请求1");
//        }

    }

    public function importData()
    {
        $data = [

        ];
        if(empty($data)) return;

        $class_names = array_column($data, 'class_name');
        $insert_class = [];
        foreach (array_unique($class_names) as $cn) {
            if (Clazz::where('name', $cn)->find() == null)
                $insert_class[] = [
                    'name' => $cn
                ];
        }
        if (!empty($insert_class)) Clazz::insertAll($insert_class);

        $insert = [];
        foreach ($data as $v) {
            $clazz_id = Clazz::where('name', $v['class_name'])->value('id');
            if(null == \app\common\model\Student::where('name', $v['name'])->where('clazz_id', $clazz_id)->find()) {
                $insert[] = [
                    'clazz_id' => $clazz_id,
                    'name' => $v['name'],
                    'gender' => $v['gender'],
                    'password' => 123456,
                ];
            }

        }
        if (!empty($insert)) \app\common\model\Student::insertAll($insert);
    }
}
