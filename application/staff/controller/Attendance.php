<?php
namespace app\staff\controller;
use app\common\model\Attendance as AttendanceModel;
use app\common\model\Config;
use app\common\model\OffdutyType;
use app\common\model\PayoutType;
use app\common\model\Schedule;
use app\common\model\StaffOffduty;
use app\common\service\WxService;
use think\Exception;
use think\facade\Log;

// 考勤
class Attendance extends Base
{
    // 签到日历
    function calendar()
    {
        if( $this->checkLogin() == false ) $this->redirect(url('/staff/login'));
        $sign = WxService::jsSign(request()->domain().request()->url());
        $this->assign('jsconfig', json_encode($sign));
        $this->assign('title', "签到日历");
        $this->assign('lat_db', Config::getValue("lat"));
        $this->assign('lng_db', Config::getValue("lng"));
        return $this->fetch();
    }
    function signList()
    {
        if( $this->checkLogin() == false ) return $this->errorJson('未登录',[],2);
        $staff_id = session('login_id');
        $year = input('y',date('Y'));
        $month = input('m',date('m'));

        $list = AttendanceModel::where(compact('staff_id'))
            ->whereTime('date', 'between', [$year.'-'.$month.'-01', $year.'-'.$month.'-31'])
            ->select()
            ->toArray();

        $sign_day = [];
        foreach ($list as $v) {
            $day = date('Y/n/j',strtotime($v['date']));
            $sign_day[$day] = [
                'day' => $day,
                'is_sign' => ($v['come_time'] == null && $v['leave_time']==null) ? 0 : 1,
                'list' => [
                    $v['come_time'] ? (date('a', strtotime($v['come_time'])) == 'am' ? '上午':'下午') . date(' H:i', strtotime($v['come_time'])) : '未签到',
                    $v['leave_time'] ? (date('a', strtotime($v['leave_time'])) == 'am' ? '上午':'下午') . date(' H:i', strtotime($v['leave_time'])) : '未签退',
                ]
            ];
        }
        $data['list'] = array_values($sign_day);
        $data['lat_db'] = Config::getValue("lat");
        $data['lng_db'] = Config::getValue("lng");
        return $this->dataJson($data);
    }
    function signDo()
    {
        if( $this->checkLogin() == false ) return $this->errorJson('未登录',[],2);
        $staff_id = session('login_id');
        $distance = $this->getDistance(input('lat'),input('lng'));
        if($distance > 0 ) {
            if($this->outDistance($distance)) {
                return $this->errorJson('超出打卡距离! 当前距离:'. number_format($distance,2) . 'km' );
            }
        }
        $today_date = date('Y-m-d');
        $now = date('H.is');

        $sign_am_start = str_replace(':','.',Config::getValue("sign_am_start") ?: '5:00');
        $sign_am_end = str_replace(':','.',Config::getValue("sign_am_end") ?: '12:00');
        $sign_pm_start = str_replace(':','.',Config::getValue("sign_pm_start") ?: '14:00');
        $sign_pm_end = str_replace(':','.',Config::getValue("sign_pm_end") ?: '23:59');

        $at_am = $sign_am_start <= $now && $now <= $sign_am_end;
        $at_pm = $sign_pm_start <= $now && $now <= $sign_pm_end;
        if(!($at_am) && !($at_pm)) {
            return $this->errorJson('不在签到或签退时间内');
        }
        $times = Schedule::getStaffComeAndLeaveTime($staff_id);
        $be_late_func = function($now, $times) {
            if($times == null) return false;
            return $now > str_replace(':','.', $times[0]) + 0.0059;
        };
        $leave_early_func = function($now, $times) {
            if($times == null) return false;
            return $now < str_replace(':','.', $times[1]);
        };

//        $find = AttendanceModel::where("date", $today_date)->find();
//        if($find) {
//        }
        $map = [
            'staff_id' => $staff_id,
            'date' => $today_date
        ];
        $exist = AttendanceModel::where($map)->find();
        if($exist) {
            if($at_am) {
                if($exist->come_time) return $this->errorJson('不能重复签到');
                $exist->come_time = now();
                $exist->be_late = $be_late_func($now, $times) ? 1 : -1;
            } elseif($at_pm) {
                if(!$exist->come_time)
                    $exist->be_late = 1;

                $exist->leave_time = now();
                $exist->leave_early = $leave_early_func($now, $times) ? 1 : -1;
            } else {
                return $this->errorJson('不在签退时间');
            }
            $res = $exist->save();
        } else {
            $map['come_time'] = $at_am ? now() : null;
            $map['leave_time'] = $at_pm ? now() : null;
            $map['be_late'] = $at_am && $be_late_func($now, $times) ? 1 : -1;
            $map['leave_early'] = $at_pm && $leave_early_func($now, $times) ? 1 : -1;
            $res = AttendanceModel::insert($map);
        }

        return $res ? $this->successJson('成功') : $this->errorJson('操作出错');
    }
    // 检查超出距离 true未可以打卡 false为超出距离
    private function outDistance($distance)
    {
        $sign_max_distance = Config::getValue("sign_max_distance");
        if($sign_max_distance == 0) return false;
        if( $distance * 1000 > $sign_max_distance ) {
            return true;
        } else {
            return false;
        }
    }
    // 获取距离打卡地点的距离 -1表示无法获取距离
    private function getDistance($lat, $lng)
    {
        if(!$lat || !$lng ) return -1;

        $lat_db = Config::getValue("lat");
        $lng_db = Config::getValue("lng");
        if(!$lat_db || !$lng_db ) return -1;

        return distance($lat, $lng, $lat_db, $lng_db); // km
    }

    function offdutyTypes()
    {
        $data = OffdutyType::column('name');
        return $this->dataJson($data);
    }
    function offdutyApply()
    {
        if( $this->checkLogin() == false ) return $this->errorJson('未登录',[],2);
        $staff_id = session('login_id');

        $start_date = input('startDate');
        $end_date = input('endDate');
        $type = input('type');
        $reason = input('reason');
        $images = input('images/a');
        $type_id = OffdutyType::where("name", $type)->value('id');
        try{

            StaffOffduty::addOne($staff_id, $start_date, $end_date, $type_id, $reason, $images);
        }catch (Exception $e) {
            return $this->errorJson($e->getMessage());
        }
        return $this->successJson('请假提交成功');

    }
    function offdutyHistory()
    {
        if( $this->checkLogin() == false ) return $this->errorJson('未登录',[],2);
        $staff_id = session('login_id');
        $data = StaffOffduty::with('type')->where(compact('staff_id'))->append(['status_text'])->order('end_date desc')->paginate(20);
        return $this->dataJson($data);
    }
}