<?php
namespace app\student\controller;


use app\common\model\Student;
use app\common\model\Vcourse;
use app\common\model\VcourseStudty;

class Vod extends Base
{
    public function vcourse()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $student_id = session('student_id');
        $student = Student::find($student_id);
        $where['clazz_id'] = $student['clazz_id'];
        $data = Vcourse::where($where)->order('id desc')->paginate(30)->each(function ($item) use ($student_id) {
            $item->is_done = VcourseStudty::isDone($item->id, $student_id) ?  1 : 0;
            $item->percent = VcourseStudty::where('vcourse_id',$item->id)->where('student_id', $student_id)->value('percent');
        });
        return $this->dataJson($data);
    }

    public function vcourseDetail()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $student_id = session('student_id');

        $id = input('id');
        $data = Vcourse::find($id);
        $data['is_done'] = VcourseStudty::isDone($id, $student_id) ?  1 : 0;
        $data['percent'] = VcourseStudty::where('vcourse_id',$id)->where('student_id', $student_id)->value('percent');
        $data['count'] = VcourseStudty::where('vcourse_id',$id)->count();

        return $this->dataJson($data);
    }

    public function playLoop()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $student_id = session('student_id');
        $id = input('id');
        $pass_sec = input('sec');
        $vcourse = Vcourse::find($id);

        $percent = VcourseStudty::where('vcourse_id',$id)->where('student_id', $student_id)->value('percent');

        if( VcourseStudty::isDone($id, $student_id, $vcourse['duration'] == 0 ? 0 : $pass_sec * 100 / $vcourse['duration'])) {
            return $this->dataJson([
                'done' => 1,
                'percent' => $percent == null ? 0 : $percent,
            ]);
        }

        if($vcourse['duration'] * 0.9 <= $pass_sec) {
            VcourseStudty::handleDone($id, $student_id);
            return $this->dataJson([
                'done' => 1,
                'percent' => 100,
            ]);
        }

        return $this->dataJson([
            'done' => 0,
            'percent' => $percent == null ? 0 : $percent,
        ]);
    }

    public function vcourseHistory()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        $student_id = session('student_id');
        $where[] = ['student_id', '=', $student_id];
        $where[] = ['done', '=', 1];

        $list = \app\common\model\VcourseStudty::with('vcourse')
            ->where($where)
            ->order('id desc')
            ->paginate(30);
        return $this->dataJson($list);
    }
}