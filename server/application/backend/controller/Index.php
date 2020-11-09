<?php

namespace app\backend\controller;

use app\backend\model\AuthNode;
use app\common\model\Attendance;
use app\common\model\Payout;
use app\common\model\Question;
use app\common\model\Region;
use app\common\model\StaffOffduty;
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
        // 课表
        // 周里的日期
        $week_days = getWeekDays();
        $the_monday = $week_days[1];
        $the_sunday = $week_days[7];

        $where[] = ['date', 'between', [$the_monday, $the_sunday]];
        $list = \app\common\model\Course::with('clazz,staff,times,section,assistant')->where($where)->order('times_id')->select();
        $data = [];
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
        $this->assign('data', $data);
        $this->assign('week_days', $week_days);
        // 课表 end

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
            '考试数' => \app\common\model\Test::count(),
            '试题数' => Question::count(),
            '职员数' => \app\common\model\Staff::count(),
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

    // 定时每分钟的扫描执行任务：http://mlwssc.top/cronjobs/handle_remind
    public function cronJobs($action)
    {
        switch ($action) {
            case "handle_remind" :
                echo 'get it';

                $remind_first = function () {
                    $limit_sec = 60; // 首次订单接单失败的提醒的秒数
                    $orders = OrderModel::whereTime('add_time', '<', date('Y-m-d H:i:s', time() - $limit_sec))
                        ->where('status', 1)
                        ->where('remind_stage', 0)
                        ->with('user')
                        ->select();
                    if (empty($orders)) return;
                    // 发送打印通知接单
                    $ids = [];
                    foreach ($orders as $order) {
                        $ids[] = $order->id;
                        \app\common\model\Order::printerRemindOrderFirst($order);
                    }
                    if (empty($ids)) {
                        \app\common\model\Order::where('id', 'in', $ids)->update(['remind_stage' => 1]);
                        echo '第1次提醒订单Ids:' . implode(',', $ids);
                        echo '\n';
                    }
                };
                $remind_second = function () {
                    $limit_sec = 60 * 3; // 首次订单接单失败的提醒的秒数
                    $orders = OrderModel::whereTime('add_time', '<', date('Y-m-d H:i:s', time() - $limit_sec))
                        ->where('status', 1)
                        ->where('remind_stage', 1)
                        ->with('user')
                        ->select();
                    if (empty($orders)) return;
                    // 发送打印通知接单
                    $ids = [];
                    foreach ($orders as $order) {
                        $ids[] = $order->id;
                        \app\common\model\Order::printerRemindOrderSecond($order);
                    }
                    if (empty($ids)) {
                        \app\common\model\Order::where('id', 'in', $ids)->update(['remind_stage' => 2]);
                        echo '第2次提醒订单Ids:' . implode(',', $ids);
                        echo '\n';
                    }
                };

                // 不接单自动取消订单
                $expired_handler = function () {
                    $sec = sys_config('order_cancel_limit');
                    $limit_sec = 60 * intval($sec);
                    if ($limit_sec) {
                        $order_ids = OrderModel::whereTime('add_time', '<', date('Y-m-d H:i:s', time() - $limit_sec))
                            ->whereTime('add_time', '>', date('Y-m-d H:i:s', time() - 1200))
                            ->column('id');
                        if (empty($order_ids)) return;
                        OrderModel::where('id', 'in', $order_ids)
                            ->where('status', 1)
                            ->update(['status' => -1, 'cancel_time' => now()]);
                        OrderModel::handleSaleNumber(OrderItems::where('order_id', 'in', $order_ids)->select(), true);
                        echo '超时取消订单Ids:' . implode(',', $order_ids);
                        echo '\n';
                    }
                };

                $remind_first();
                $remind_second();
                $expired_handler();

                break;

            default:
                exception("非法请求");
        }

    }
}
