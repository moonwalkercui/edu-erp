<?php
namespace app\staff\controller;

use app\common\model\ClazzEvent;
use app\common\model\Student;
use app\common\model\StudentAccount;
use app\common\model\StudentAccountLog;
use app\common\model\UserAccount;
use app\common\model\UserAccountLog;
use app\common\model\ZoneLike;
use app\common\model\ZoneTask;

class Zone extends Base
{
    function zoneList()
    {
//        $where[] = ['clazz_id', '=', input('clazz_id'];
        $where[] = ['task_id', '=', input('task_id')];
        $data = \app\common\model\Zone::getList($where);
        return $this->dataJson($data);
    }

    function taskList()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
        $staff_id = session('login_id');
        $where[] = ['clazz_id', '=', input('class_id')];
        $where[] = ['staff_id', '=', $staff_id];
        $tasks = ZoneTask::withCount("students")->where($where)->order('id desc')->paginate(20)->each(function($item) {
            $new_content = substr_cn($item->content, 40);
            $item->content = $new_content ?  $new_content : '无文字';
            $item->count = $item->students_count;
        });
        return $this->dataJson($tasks);
    }

//    function zoneLike()
////    {
////        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
////        $staff_id = session('login_id');
////        $res = ZoneLike::addOne(input('id/d'), $staff_id, true);
////        return $res ? $this->successJson('已赞') : $this->errorJson('赞失败');
////    }
    // 发布点评
    function zoneCheck()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
        $staff_id = session('login_id');

        $id = input('id/d');
        $score = input('score/d');
        $content = input('content');

        $score = $score < 1 ? 1 : $score;
        $score = $score > 5 ? 5 : $score;

        return \app\common\model\Zone::verify($id, $staff_id, $content, $score) ? $this->successJson('已发布') : $this->errorJson('发布失败');
    }
}