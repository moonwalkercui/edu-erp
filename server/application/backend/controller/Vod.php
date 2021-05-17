<?php
namespace app\backend\controller;

use app\backend\model\MediaModel;
use app\backend\model\VcourseModel;
use app\backend\model\VcourseStudtyModel;
use app\common\model\Clazz;
use app\common\model\ClazzSign;
use app\common\model\Media;
use app\common\model\Vcourse;
use app\common\model\VcourseStudty;

class Vod extends Base
{
    // 视频课程列表
    function vcourse()
    {
        $where =[];
        if (input('title'))
            $where[] = ['title', 'like', '%'.input('title').'%'];

        if (input('clazz_id'))
            $where[] = ['clazz_id', '=', input('clazz_id')];

        $data = [
            'title' => '视频课程',
            'collection' => VcourseModel::where($where)->with('clazz')->order('edit_time desc,sn asc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['封面', '序号','标题','班级', '时长','学习数', '简介', '编辑时间'],
            'fields' => [
                function ($row) {
                    return $row['cover'] ? '<image src="' . $row['cover'] . '" class="ui tiny image">' : '';
                },'sn','title',
                function ($row) {
                    return $row->clazz ? $row->clazz['name'] : '无';
                },
                function ($row) {
                    $num = $row->duration;
                    $hour = floor($num/3600);
                    $minute = floor(($num-3600*$hour)/60);
                    $second = floor((($num-3600*$hour)-60*$minute)%60);
                    echo ($hour>0 ? $hour.':' : '').$minute.':'.$second;
                },
                function ($row) {
                    return VcourseStudty::where('vcourse_id',$row['id'])->count();
                },
                function ($row) {
                    echo mb_substr(strip_tags($row['content']), 0, 30) . '...';
                },
                'edit_time'
            ],
            'buttons' => [
                ['title' => '编辑', 'onclick' => 'openBigWin', 'url' => url('saveCourse')],
                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('delMoment')],
            ],
            'mbuttons' => [
                ['title' => '腾讯云视频列表', 'onclick' => 'openBigWin', 'url' => url('tencent/vodList'), 'class_name'=>''],
                ['title' => '添加视频', 'onclick' => 'openBigWin', 'url' => url('saveCourse')],
            ],
            'searcher' => [
                ['type' => 'input', 'name' => 'title', 'placeholder' => '搜索标题'],
                ['type' => 'select', 'name' => 'clazz_id', 'options' => Clazz::column('name', 'id'), 'placeholder' => '选择班级'],
            ],
        ];
        $this->assign('data', $data);
        return $this->fetch('public/table_builder');

    }
    public function saveCourse()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $data['staff_id'] = session('login_id');
            $data['edit_time'] = now();

            if(isset($data['vod_urls'])) {
                $t = explode(',', $data['vod_urls']);
                $data['video_url'] = $t[0];
                $data['cover'] = $t[1];
                $data['vod_id'] = $t[2];
                $data['duration'] = $t[3];
            }

            if ($id = input('id')) {
                $this->modelUpdate(VcourseModel::class, $data, ['id' => $data['id']], 'vcourse_save', '修改视频课程');
            } else {
                $data['add_time'] = now();
                $this->modelCreate(VcourseModel::class, $data, 'vcourse_save', '添加视频课程');
            }
        } else {

            return $this->fetchFormPageHtml(VcourseModel::class, __FUNCTION__);
        }
    }

    public function studyLog() {
        $where =[];
        $map = [];
        if (input('vcourse')) {
            $map['vcourse_id'] = input('vcourse');
            $where[] = ['vcourse_id', '=', input('vcourse')];
        }
        if (input('student_id')) {
            $map['student_id'] = input('student_id');
            $where[] = ['student_id', '=', input('student_id')];
        }

        $data = [
            'title' => '精彩瞬间',
            'collection' => VcourseStudty::where($where)->with('student,vcourse')->order('add_time desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['姓名', '课程名','开始时间', '完成百分比','是否完成','完成时间'],
            'fields' => [
                function ($row) {
                    return $row->student ? $row->student['name'] : '';
                },
                function ($row) {
                    return $row->vcourse ? $row->vcourse['title'] : '';
                }, 'add_time',
                function ($row) {
                    return $row->percent.'%';
                },
                function ($row) {
                    return $row->done == 1 ? '是' : '否';
                },'done_time',
            ],
            'buttons' => [
                ['title' => '编辑', 'onclick' => 'openBigWin', 'url' => url('saveStudyLog')],
            ],
            'mbuttons' => [
                ['title' => '添加记录', 'onclick' => 'openBigWin', 'url' => url('saveStudyLog')],
                ['title' => '导出', 'onclick' => 'openBigWin', 'url' => url('exportLog',$map)],
            ],
            'searcher' => [
                ['type' => 'select', 'name' => 'vcourse', 'options' => Vcourse::column('title', 'id'), 'placeholder' => '选择课程'],
                ['type' => 'select', 'name' => 'student_id', 'options' => \app\common\model\Student::column('name', 'id'), 'placeholder' => '选择学生'],
            ],
        ];
        $this->assign('data', $data);
        return $this->fetch('public/table_builder');
    }

    public function saveStudyLog()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $data['staff_id'] = session('login_id');
            if($data['done'] == 1) {
                $data['percent'] = 100;
                $data['done_time'] = now();
            }
            if ($id = input('id')) {
                $this->modelUpdate(VcourseStudtyModel::class, $data, ['id' => $data['id']], 'vcourse_study', '修改视频学习结果');
            } else {
                $data['add_time'] = now();
                $this->modelCreate(VcourseStudtyModel::class, $data, 'vcourse_study', '添加视频学习结果');
            }
        } else {
            return $this->fetchFormPageHtml(VcourseStudtyModel::class, __FUNCTION__);
        }
    }

    function exportLog()
    {
        $where = [];
        if (input('student_id')){
            $where[] = ['student_id', '=', input('student_id')];
        }
        if (input('vcourse_id')) {
            $where[] = ['vcourse_id', '=', input('vcourse_id')];
        }

        $list = VcourseStudty::where($where)->with('student,vcourse')->where('done_time','>', date('Y-m-d', strtotime('-6months')))->order("id desc")->select();
        $data = [];
        foreach ($list as $v) {
            $data[] = [
                'staff_name' => $v->student ? $v->student['name'] : '',
                'vcourse_tile' => $v->vcourse ? $v->vcourse['title'] : '',
                'add_time' => $v->add_time,
                'done' => $v->done == 1 ? '是' : '否',
                'done_time' => $v->done_time,
            ];
        }
        C_exportExcel($data, [
            'staff_name' => '学生名',
            'vcourse_tile' => '课程名',
            'add_time' => '开始学习时间',
            'done' => '是否完成',
            'done_time' => '完成时间',
        ],[20,20,20,20,20]);
    }

}