<?php
namespace app\student\controller;

use app\common\model\Student;
use app\common\model\ZoneComment;
use app\common\model\ZoneLike;
use app\common\model\ZoneTask;
use app\common\service\WxService;
use think\facade\Log;

class Zone extends Base
{
    function taskList()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
        $student_id = session('student_id');
        $student = Student::find($student_id);
        $where[] = ['clazz_id', '=', $student['clazz_id']];
        $got_ids = ZoneTask::getTaskIdsByStudentId($student_id);
        $data = \app\common\model\ZoneTask::where($where)->order('id desc')->paginate(20)->each(function($item) use ($got_ids){
            $item->is_finish = 0;
            if(!empty($got_ids) && in_array($item->id, $got_ids)) {
                $item->is_finish = 1;
            }
        });
        return $this->dataJson($data);
    }
    function zoneList()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
        $student_id = session('student_id');
        $student = Student::find($student_id);
        $where[] = ['clazz_id', '=', $student['clazz_id']];
        $where[] = ['status', '=', 1];
        $data = \app\common\model\Zone::getList($where, $student_id, 'id desc');
        return $this->dataJson($data);
    }
    function zoneLike()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
        $student_id = session('student_id');
        $res = ZoneLike::addOne(input('id/d'), $student_id);
        return $res[0] ? $this->successJson($res[1] ? '已赞' : '已取消',['count' => $res[2]]) : $this->errorJson('赞失败');
    }

    function zoneComment()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
        $student_id = session('student_id');

        $data = input('post.');
        if(!$data['id']) return $this->errorJson('缺少参数');

        $res = ZoneComment::addOne($student_id, $data['id'], $data['content']);
        $comments = ZoneComment::getList(['zone_id' => $data['id']], $student_id);
        return $res ? $this->successJson('已评论', ['list' =>$comments]) : $this->errorJson('评论失败');

    }
    function zoneCommentDel()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
        $student_id = session('student_id');

        $id = input('post.id');
        if(!$id) return $this->errorJson('缺少参数');

        $res = ZoneComment::where('id', $id)->where('publisher_id', $student_id)->delete();
        $comments = ZoneComment::getList(['zone_id' => $id], $student_id);
        return $res ? $this->successJson('已评论', ['list' =>$comments]) : $this->errorJson('评论失败');

    }
    // 发布作业
    function zonePublish()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
        $student_id = session('student_id');
        $data = input('post.');
        if(empty($data['task_id'])) return $this->errorJson('缺少参数task_id');
        if(isset($data['images'])) {
            if(empty($data['images'])) return $this->errorJson('缺少图片');
            $file_path = str_replace('\\', '/', implode(',', $data['images']));
            $type = 3;
        } else {
            if(empty($data['voice'])) return $this->errorJson('缺少录音');
            $res = WxService::downloadTempMedia($data['voice']);
            $file_name = make_sn();
            $file_path = '/up/voice/'.$file_name.'.amr';
            file_put_contents('.'.$file_path, $res);
            $type = 2;
        }
        $res = \app\common\model\Zone::addOne($student_id, $data['task_id'], $type, $data['content'], $file_path);
        if($res) {
            ZoneTask::finishOne($student_id, $data['task_id']);
            return $this->successJson('已发布');
        }
        return $this->errorJson('发布失败');
    }
}