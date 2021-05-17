<?php

namespace app\student\controller;

use app\common\model\Student;
use app\common\model\WxAccess;
use app\common\model\ZoneComment;
use app\common\model\ZoneLike;
use app\common\model\ZoneTask;
use app\common\service\WxService;

class Zone extends Base
{
    function taskList()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $student_id = session('student_id');
        $student = Student::find($student_id);
        $got_ids = ZoneTask::getTaskIdsByStudentId($student_id);
//        $where[] = ['section_id', 'in', Section::getIdsByClassId($student['clazz_id'])];
        $where[] = ['clazz_id', '=', $student['clazz_id']];
        $data = \app\common\model\ZoneTask::with('clazz')->where($where)->order('id desc')->paginate(20)->each(function ($item) use ($got_ids) {
            $item->is_finish = 0;
            $item->course_time = '';
//            $item->section = Section::with('task')->where('id', $item->section_id)->field('id,title,training')->find();
//            if (!empty($item->section)) {
//                $item->course_time = CourseModel::where('section_id', $item->section['id'])->value('date');
//            }
            if (!empty($got_ids) && in_array($item->id, $got_ids)) {
                $item->is_finish = 1;
            }
        });
        return $this->dataJson($data);
    }

    function zoneList()
    {
        $task_id = input('task_id/d');
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $student_id = session('student_id');
        $student = Student::find($student_id);
        $student->last_zone_viewtime = time();
        $student->save();

        $where[] = ['task_id', '=', $task_id];
        $where[] = ['clazz_id', '=', $student['clazz_id']];
        $where[] = ['status', '=', 1];
//        $data = \app\common\model\Zone::getList($where, $student_id, 'id desc');
        $data = \app\common\model\Zone::with('comments.publisher,studentBind,verifier')->withCount('likes')->where($where)->order('id desc')->paginate(20)->each(function ($item) use ($student_id) {
            if ($item['attach']) {
                $item['attach'] = explode(',', $item['attach']);
            }
            $item['student_avatar'] = WxAccess::getStudentHeadImg($item['student_id']);
            foreach ($item->comments as $c) {
                $c->is_my = ($student_id && $c['publisher_id'] == $student_id) ? 1 : 0;
            }
        });

        return $this->dataJson($data);
    }

    function zoneLike()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $student_id = session('student_id');
        $res = ZoneLike::addOne(input('id/d'), $student_id);
        return $res[0] ? $this->successJson($res[1] ? '已赞' : '已取消', ['count' => $res[2]]) : $this->errorJson('赞失败');
    }

    function zoneComment()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $student_id = session('student_id');

        $data = input('post.');
        if (!$data['id']) return $this->errorJson('缺少参数');

        $res = ZoneComment::addOne($student_id, $data['id'], $data['content']);
        $comments = ZoneComment::getList(['zone_id' => $data['id']], $student_id);
        return $res ? $this->successJson('已评论', ['list' => $comments]) : $this->errorJson('评论失败');

    }

    function zoneCommentDel()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $student_id = session('student_id');

        $id = input('post.id');
        if (!$id) return $this->errorJson('缺少参数');
        $zone_id = ZoneComment::where('id', $id)->value('zone_id');
        $res = ZoneComment::where('id', $id)->where('publisher_id', $student_id)->delete();
        $comments = ZoneComment::getList(['zone_id' => $zone_id], $student_id);
        return $res ? $this->successJson('已评论', ['list' => $comments]) : $this->errorJson('评论失败');

    }

    // 发布作业
    function zonePublish()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $student_id = session('student_id');
        $data = input('post.');
        if (empty($data['task_id'])) return $this->errorJson('缺少参数task_id');
        $imgs = $voices = [];

        $media_list = input('media_list/a');
        foreach ($media_list as $v) {
            if ($v['type'] == 'img') {
                $imgs[] = $v['url'];
            }
            if ($v['type'] == 'voice') {

                $res = WxService::downloadTempMedia($v['voice']);
                $up_file = 'up/voice/' . make_sn();
                file_put_contents('./' . $up_file . '.amr', $res);
                $voices[] = $up_file . '.mp3';
                amrToMp3('./' . $up_file . '.amr', './' . $up_file . '.mp3');
                @unlink('./' . $up_file . '.amr');
            }
        }
        $attach = str_replace('\\', '/', implode(',', $imgs));
        $voice = str_replace('\\', '/', implode(',', $voices));

//        $type = 1;
//        $attach = "";
//        if(isset($data['images'])) {
//            if(empty($data['images'])) return $this->errorJson('缺少图片');
//            $attach = str_replace('\\', '/', implode(',', $data['images']));
//            $type = 3;
//        }
//        if(isset($data['voice'])) {
//            $res = WxService::downloadTempMedia($data['voice']);
//            $file_name = make_sn();
//            $attach = 'up/voice/'.$file_name.'.amr';
//            file_put_contents('./'.$attach, $res);
//            $type = 2;
//        }
//        if(isset($data['images'])) {
//            if(empty($data['images'])) return $this->errorJson('缺少图片');
//            $attach = str_replace('\\', '/', implode(',', $data['images']));
//            $type = 3;
//        } else {
//            if(empty($data['voice'])) return $this->errorJson('缺少录音');
//            $res = WxService::downloadTempMedia($data['voice']);
//            $file_name = make_sn();
//            $attach = '/up/voice/'.$file_name.'.amr';
//            file_put_contents('.'.$attach, $res);
//            $type = 2;
//        }
        $res = \app\common\model\Zone::addOne($student_id, $data['task_id'], $data['content'], $attach, $voice);
        if ($res) {
            ZoneTask::finishOne($student_id, $data['task_id']);
            return $this->successJson('已发布');
        }
        return $this->errorJson('发布失败');
    }
}