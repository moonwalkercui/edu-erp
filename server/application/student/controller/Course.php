<?php
namespace app\student\controller;

use app\common\model\Student;
use app\common\model\ZoneComment;
use app\common\model\ZoneLike;
use app\common\model\ZoneTask;
use app\common\service\WxService;
use think\facade\Log;

class Course extends Base
{
    function calendar()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
        $student_id = session('student_id');
        $student = Student::find($student_id);

        $date = input('date');
        $date = !$date ? date('Y-m-d') :$date;
        $where[] = ['date','=', $date];
        $where[] = ['clazz_id','=', $student['clazz_id']];

        $list = \app\common\model\Course::with('clazz,staff,times,section')->where($where)->order('times_id')->select();
        return $this->dataJson($list);
    }

}