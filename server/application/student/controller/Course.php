<?php

namespace app\student\controller;

use app\backend\model\SectionModel;
use app\common\model\Clazz;
use app\common\model\ClazzSign;
use app\common\model\Student;
use app\common\model\ZoneComment;
use app\common\model\ZoneLike;
use app\common\model\ZoneTask;
use app\common\service\WxService;
use think\Exception;
use think\facade\Log;
use utils\Time;

class Course extends Base
{
    function calendar()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $student_id = session('student_id');
        $student = Student::find($student_id);

        $date = input('date');
        $date = !$date ? date('Y-m-d') : $date;
        $where[] = ['date', '=', $date];
        $where[] = ['clazz_id', '=', $student['clazz_id']];

        $list = \app\common\model\Course::with('clazz,staff,times,section')->where($where)->order('times_id')->select();
        return $this->dataJson($list);
    }

    function courseByWeek()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $time = input('time');
        $time = $time ?: null;
        $week_start = Time::beginWeek(false, $time);
        $end_week = Time::endWeek(false, $time);
        $where[] = ['date', '>=', $week_start];
        $where[] = ['date', '<=', $end_week];
        $student_id = session('student_id');

        $student = Student::find($student_id);
        $where[] = ['clazz_id', '=', $student['clazz_id']];

        $list = \app\common\model\Course::with('clazz,staff,times')
            ->where($where)
            ->order('date,times_id')
            ->select()
            ->each(function ($item) {
                $item['week'] = weekdayText(date('w', strtotime($item['date'])));
//                if ($item['section']) {
//                    $item['section']['remark'] = substr_cn($item['section']['remark'], 20);
//                }
            });
        return $this->dataJson(['list' => $list, 'time' => strtotime($week_start)]);
    }

    function history()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $student_id = session('student_id');
        $student = Student::find($student_id);
        $where[] = ['clazz_id', '=', $student['clazz_id']];
        $where[] = ['date', '<', date('Y-m-d')];

        $list = \app\common\model\Course::with('clazz,staff,times')
            ->where($where)
            ->order('date desc,times_id')
            ->paginate()
            ->each(function ($item) {
                $item['week'] = weekdayText(date('w', strtotime($item['date'])));
            });
        return $this->dataJson($list);
    }

    function detail()
    {
        $id = input('id');
        return $this->dataJson(\app\common\model\Course::with('clazz,staff,times,section.task')->find($id));
    }
    function section()
    {
        $id = input('id');
        return $this->dataJson(ZoneTask::find($id));
    }

    /*
     * 签到记录
     * */
    function clazzSign()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $where = [];
        $student_id = session('student_id');
//        $student = Student::find($student_id);
//        $clazz_id = $student['clazz_id'];
        $where[] = ['student_id', '=', $student_id];

        $list = \app\common\model\ClazzSign::with('clazz')
            ->where($where)
            ->order('id desc')
            ->paginate(10, false, ['query' => $this->request->param()]);
        return $this->dataJson($list);
    }

    /*
     * 班级签到信息
     * */
    function clazzSignInfo()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $clazz_id = input('id');
        $student_id = session('student_id');

        $clazz = Clazz::field("id,name,start_date,end_date,sign_start1,sign_end1,sign_start2,sign_end2")->where('id', $clazz_id)->find();

        if(!$clazz) return $this->errorJson("无效班级");
        if(strtotime($clazz['start_date']) > \time()) return $this->errorJson("该班级还没开始");
        if(strtotime($clazz['end_date']) < \time()) return $this->errorJson("该班级已结束");

        $data['info'] = $clazz;
        $data['stage'] = Clazz::validateSignTime($clazz_id);
        $data['state'] = ClazzSign::lastSignState($student_id, $clazz_id, $data['stage'][1]);

        return $this->dataJson($data);
    }
    /*
     * 班级签到
     * */
    function signDo()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $clazz_id = input('id');
        $student_id = session('student_id');
        $student = Student::find($student_id);
        if($clazz_id != $student['clazz_id']) {
            return $this->errorJson("你不属于该班级");
        }

        $clazz = Clazz::find($clazz_id);
        if(!$clazz) return $this->errorJson("无效班级");
        if(strtotime($clazz['start_date']) > \time()) return $this->errorJson("该班级还没开始");
        if(strtotime($clazz['end_date']) < \time()) return $this->errorJson("该班级已结束");


        try{
            $res = ClazzSign::addOne($student_id, $clazz_id);
        }catch (Exception $e) {
            return $this->errorJson($e->getMessage());
        }
        return $this->successJson(Clazz::makeRemark($res));
    }
}