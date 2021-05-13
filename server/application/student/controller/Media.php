<?php
namespace app\student\controller;

use app\common\model\MediaLike;
use app\common\model\Student;
use app\common\model\Media as MediaModel;
class Media extends Base
{
    function getList()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
        $student_id = session('student_id');
        $student = Student::find($student_id);
        $student->last_media_viewtime = time();
        $student->save();
        $clazz_id = $student['clazz_id'];

        $data = MediaModel::where(function ($query) use($clazz_id,$student_id) {
            $query->where('clazz_id', $clazz_id)->whereOr("FIND_IN_SET('{$student_id}', student_ids)");
        })->with('clazz')->withCount('likes')->order('id desc')->paginate(10, false, ['query' => $this->request->param()]);

        return $this->dataJson($data);
    }
    function mediaLike()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
        $student_id = session('student_id');
        $res = MediaLike::addOne(input('id/d'), $student_id);
        return $res[0] ? $this->successJson($res[1] ? '已赞' : '已取消',['count' => $res[2]]) : $this->errorJson('赞失败');
    }
}