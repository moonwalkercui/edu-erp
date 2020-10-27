<?php

namespace app\backend\controller;

use app\backend\model\ClazzModel;
use app\backend\model\MediaModel;
use app\backend\model\StudentModel;
use app\backend\model\ZoneModel;
use app\backend\model\ZoneTaskModel;
use app\common\model\Clazz;
use app\common\model\ClazzEvent;
use app\common\model\Grade;
use app\common\model\Media;
use app\common\model\Question;
use app\common\model\StudentAccount;
use app\common\model\StudentAccountLog;
use app\common\model\Test;
use app\common\model\TestScore;
use app\common\model\UserAccount;
use app\common\model\WxAccess;
use app\common\model\Zone;
use app\common\model\ZoneTask;

class Student extends Base
{
    public function getList()
    {
        $where = [];
        if (input('name'))
            $where[] = ['name', '=', input('name')];
        if (input('mob'))
            $where[] = ['mobile', '=', input('mob')];
        if (input('status'))
            $where[] = ['status', '=', input('status')];
        if (input('grade_id'))
            $where[] = ['grade_id', '=', input('grade_id')];
        if (input('clazz_id'))
            $where[] = ['clazz_id', '=', input('clazz_id')];

        $this->assign('data', [
            'title' => '学生列表',
            'collection' => StudentModel::with('account','wxAccount')->where($where)->order('id desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['学号', '姓名', '手机号', '班级', '性别', '积分', '注册时间', '绑定微信', '状态'], // '年级',  ['照片', 'width:60px'],
            'fields' => ['id',
//                function ($row) {
//                    return '<image src="' . $row['avatar'] . '" class="ui tiny image">';
//                },
                'name', 'mobile',
//                function ($row) {
//                    return $row->grade->name ?? '';
//                },
                function ($row) {
                    return $row->clazz->name ?? '';
                },
                function ($row) {
                    return $row->gender_text;
                },
                function ($row) {
                    return $row->account['point'];
                },
                'add_time',
                function ($row) {
                    return $row->wx_account ? $row->wx_account['nickname'] : '';
                },
                'status_text'
            ],
            'buttons' => [
                ['title' => '编辑', 'onclick' => 'openBigWin', 'url' => url('saveStudent'),'permission_id' => 159],
                ['title' => '绑定微信', 'url' => url('bindWechat')],
                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('deleteStudent'),'permission_id' => 160],
            ],
            'mbuttons' => [
                ['title' => '添加账号', 'onclick' => 'openBigWin', 'url' => url('saveStudent'),'permission_id' => 159],
            ],
            'searcher' => [
                ['type' => 'input', 'name' => 'name', 'placeholder' => '姓名'],
                ['type' => 'input', 'name' => 'mob', 'placeholder' => '手机号'],
                ['type' => 'select', 'name' => 'status', 'options' => config('status.normal'), 'placeholder' => '选择状态'],
//                ['type' => 'select', 'name' => 'grade_id', 'options' => Grade::column('name', 'id'), 'placeholder' => '选择年级'],
                ['type' => 'select', 'name' => 'clazz_id', 'options' => Clazz::column('name', 'id'), 'placeholder' => '选择班级'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    public function saveStudent()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $data['avatar'] = isset($data['avatar']) ? $data['avatar'][0] : null;
            if ($id = input('id')) {
                $this->modelUpdate(StudentModel::class, $data, ['id' => $data['id']], 'student_saving', '修改学员');
            } else {
//                $user_id = \app\common\model\User::findOrCreateByMob($data['mobile']);
//                $data['user_id'] = $user_id;
                $data['add_time'] = now();
                $this->modelCreate(StudentModel::class, $data, 'student_saving', '添加学员');

            }
        } else {
            return $this->fetchFormPageHtml(StudentModel::class, __FUNCTION__);
        }
    }
    public function bindWechat()
    {
        if (request()->isPost()) {
            $id = input('id');
            $access_id = input('access_id');
            WxAccess::bindStudent($access_id, $id) ? $this->success('绑定成功') : $this->error('绑定失败');
        } else {
            return $this->fetchFormPageHtml(StudentModel::class, __FUNCTION__, [], 'makeFromData1');
        }
    }

    function deleteStudent()
    {
        echo StudentModel::destroy(input('id/d')) ? 1 : 0;
    }

    public function clazz()
    {
        $where = [];
        $this->assign('data', [
            'title' => '班级列表',
            'collection' => Clazz::where($where)->order('id desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['ID', '名称', '负责人'], // '年级',
            'fields' => ['id', 'name',
//                function ($row) {
//                    return $row->grade->name ?? '';
//                },
                function ($row) {
                    $staffs = \app\common\model\Staff::where('id', 'in', $row['staff_in_charge'])->column('name');
                    echo implode(', ', $staffs);
                },
            ],
            'buttons' => [
                ['title' => '编辑', 'onclick' => 'openBigWin', 'url' => url('saveClazz'),'permission_id' => 161],
                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('deleteClazz'),'permission_id' => 162],
            ],
            'mbuttons' => [
                ['title' => '添加账号', 'onclick' => 'openBigWin', 'url' => url('saveClazz'),'permission_id' => 161],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    public function saveClazz()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if ($id = input('id')) {
                $this->modelUpdate(ClazzModel::class, $data, ['id' => $data['id']], 'clazz_saving', '修改班级');
            } else {
                $data['add_time'] = now();
                $this->modelCreate(ClazzModel::class, $data, 'clazz_saving', '添加班级');
            }
        } else {
            return $this->fetchFormPageHtml(ClazzModel::class, __FUNCTION__);
        }
    }

    function deleteClazz()
    {
        $id = input('id/d');
        echo ClazzModel::destroy($id) ? 1 : 0;
    }

    function point()
    {
        $where = [];
        if (input('name'))
            $where[] = ['name', '=', input('name')];
        if (input('mob'))
            $where[] = ['mobile', '=', input('mob')];
        $this->assign('data', [
            'title' => '学生积分',
            'collection' => \app\common\model\Student::with('account')->where($where)->order('id desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['学号', '姓名', '手机号', '积分'],
            'fields' => ['id', 'name', 'mobile',
                function ($row) {
                    return $row->account['point'] ?? '0';
                },
            ],
            'buttons' => [
                ['title' => '增加', 'url' => url('changePoint', ['type' => 'inc']),'permission_id' => 163],
                ['title' => '减少', 'url' => url('changePoint', ['type' => 'dec']),'permission_id' => 163],
                ['title' => '明细', 'onclick' => 'openBigWin', 'url' => url('pointLog'),'permission_id' => 164],
            ],
            'searcher' => [
                ['type' => 'input', 'name' => 'name', 'placeholder' => '姓名'],
                ['type' => 'input', 'name' => 'mob', 'placeholder' => '手机号'],
            ],
            'mbuttons' => [
                ['title' => '变动记录', 'onclick' => 'openBigWin', 'url' => url('pointLog'),'permission_id' => 164],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    public function changePoint()
    {
        $student_id = input('id');
        $student = \app\common\model\Student::find($student_id);
        $input_point = $student->account['point'];

        if (request()->isPost()) {
            $point = input('point/d');
            $reason = input('reason');
            $type = input('type');
            if (!$reason) $this->error('缺少原因');
            if ($point <= 0) $this->error('积分数有误');
            if ($type == 'inc') {
                $update['point'] = $input_point + $point;
                $remark = "增加积分$point";
            } else {
                if ($point > $input_point) $this->error('积分不足');
                $update['point'] = $input_point - $point;
                $remark = "减少积分$point";
            }
            $this->modelUpdate(StudentAccount::class, $update, ['student_id' => $student_id], '', $remark, function ($condition) use ($type, $point, $reason, $student_id) {
                StudentAccountLog::addOne($student_id, StudentAccountLog::TYPE['point'], ($type != 'inc' ? '-' : '') . $point, $reason);

                ClazzEvent::addOne(
                    session('login_name') . "老师更新了积分",
                    \app\common\model\Student::where('id', $student_id)->value('clazz_id'),
                    session('login_id'),
                    ClazzEvent::TYPE_POINT,
                    $condition['student_id'],
                    $student_id
                );

            });
        } else {
            $model = new StudentModel();
            $this->assign('data', $model->changePoints(['id' => $student_id]));
            $this->assign('action', url(__FUNCTION__, ['type' => input('type')]));
            $this->assign('btn_text', '保存');
            return $this->fetch('public/form_builder');
        }
    }

    function pointLog()
    {
        $where = [];
        if (input('name')) {
            $where[] = ['student_id', '=', \app\common\model\Student::where('name', input('name'))->value('id')];
        }
        if (input('mob')) {
            $where[] = ['student_id', '=', \app\common\model\Student::where('mobile', input('mob'))->value('id')];
        }
        $where[] = ['type', '=', StudentAccountLog::TYPE['point']];
        $title = '积分变动记录';
        if ($id = input('id')) {
            $student = \app\common\model\Student::find($id);
            $where[] = ['student_id', '=', $student['id']];
            $title .= $student['name'] . '的' . $title;
        }
        $data = [
            'title' => $title,
            'collection' => StudentAccountLog::where($where)->order('id desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['姓名', '日期', '变动数', '变动原因', '剩余数'],
            'fields' => [
                function ($row) {
                    return \app\common\model\Student::where('id', $row['student_id'])->value('name');
                },
                'add_time', 'amount', 'remark', 'remaining_point'
            ],
        ];
        if (!$id) {
            $data['searcher'] = [
                ['type' => 'input', 'name' => 'name', 'placeholder' => '姓名'],
                ['type' => 'input', 'name' => 'mob', 'placeholder' => '手机号'],
            ];
        }
        $this->assign('data', $data);
        return $this->fetch('public/table_builder');
    }

    // 录入成绩
    function editScore()
    {
        $data['clazz'] = Clazz::order('id desc')->column('name', 'id');
        $data['test'] = Test::order('id desc')->column('title', 'id');
        $this->assign('data', $data);
        return $this->fetch();
    }

    // 编辑分数
    function inputScore()
    {
        $type = input('type');
        $clazz_id = input('clazz_id');
        $test_id = input('test_id');
        if (!$clazz_id) $this->error('请选择班级');
        if (!$test_id) $this->error('请选择考试');

        $test = Test::getQuestionList($test_id);
        $students = Clazz::getStudents($clazz_id);
        if (count($students) == 0) $this->error('该班级暂无学生');

        $score_list = TestScore::getListByTestId($test_id);
//        TestScore::cover($test_id);
        $find_score = function ($student_id, $test_id, $question_id, $score_list) {
            if (count($score_list) == 0) return null;
            foreach ($score_list as $v) {
                if ($v['student_id'] == $student_id && $v['question_id'] == $question_id && $v['test_id'] == $test_id) {
                    return $v;
                }
            }
            return null;
        };

        $inputs = $names = $questions = $answers = [];
        $num = 0;
        $recover_ids = [];
        foreach ($test as $v) {
            foreach ($v['group'] as $v2) {
                $num++;
                foreach ($v2['question'] as $k3 => $v3) {
                    $count = count($v2['question']);
                    foreach ($students as $s) {
                        $key = $num . ($count > 1 ? '.' . ($k3 + 1) : '');
                        $questions[$key] = mb_substr(strip_tags($v3['body']), 0, 30) . '...';
                        $answers[$key] = $v3['answer'];
                        $names[$s['id']] = $s['name'];

                        $history = $find_score($s['id'], $test_id, $v3['id'], $score_list);
                        $score = '';
                        if ($history) {
                            $recover_ids[] = $history['id'];
                            $score = $history['score'];
                            $is_right = $history['is_right'];
                            $answer = $history['answer'];
                        }
                        $inputs[$s['id']][$key] = [
                            'student_id' => $s['id'],
                            'test_id' => $test_id,
                            'question_id' => $v3['id'],
                            'clazz_id' => $clazz_id,
                            'score' => $score,
                            'is_right' => $is_right,
                            'answer' => $answer,
                        ];
                    }
                }
            }
        }

        $this->assign('type', $type);
        $this->assign('inputs', $inputs);
        $this->assign('names', $names);
        $this->assign('questions', $questions);
        $this->assign('answers', $answers);
        return $this->fetch();
    }

    function saveScore()
    {
        $code = input('code');
        $value = input('value');
        $arr = explode('-', $code);
        if (count($arr) != 4) $this->error('CODE错误');
        list($student_id, $test_id, $question_id, $type) = $arr;
        $question = Question::find($question_id);
        $is_right = null;
        $answer = null;
        switch ($type) {
            case 0:
                $score = Question::getScoreByJudge($test_id, $question, $value == 1);
                $is_right = $value == 1 ? 1 : -1;
                break; // 按对错录分
            case 1:
                $score = Question::getScoreByAnswer($test_id, $question, $value);
                $answer = $value;
                break; // 按答案录分
            default:
                $score = $value; // 按分数录分
        }

        TestScore::saveScore($student_id, $test_id, $question_id, $score, $is_right, $answer) ?
            $this->success('已保存') : $this->error('保存失败');
    }

    function score()
    {
        $where = [];
        if (input('name')) {
            $user_id = \app\common\model\Student::where('name', input('name'))->value('user_id');
            $where[] = ['user_id', '=', $user_id];
        }
        if (input('test_id'))
            $where[] = ['test_id', '=', input('test_id')];
        if (input('clazz_id'))
            $where[] = ['clazz_id', '=', input('clazz_id')];

        $data = [
            'title' => '学员成绩',
            'collection' => TestScore::where($where)->group('student_id,test_id')->field('*,sum(score) total_score,count(id) total_count, sum(is_right=1) right_count')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['姓名', '班级', '考试','总分','正确率'],
            'fields' => [
                function ($row) {
                    echo $row->student['name'];
                },
                function ($row) {
                    echo $row->clazz['name'];
                },
                function ($row) {
                    echo $row->tests['title'];
                },
                'total_score',
                function ($row) {
                    echo ($row['total_count'] == 0 ? 0 : number_format($row['right_count'] / $row['total_count'] * 100) ) . '%';
                },
            ],
            'buttons' => [
                ['title' => '详情', 'onclick' => 'openBigWin', 'url' => url('scoreDetail'),'permission_id' => 165],
            ],
            'mbuttons' => [
                ['title' => '录成绩', 'onclick' => 'openBigWin', 'url' => url('editScore'),'permission_id' => 166],
            ],
            'searcher' => [
                ['type' => 'input', 'name' => 'name', 'placeholder' => '学员姓名'],
                ['type' => 'select', 'name' => 'clazz_id', 'options' => Clazz::column('name', 'id'), 'placeholder' => '选择班级'],
                ['type' => 'select', 'name' => 'test_id', 'options' => Test::column('title', 'id'), 'placeholder' => '选择考试'],
            ],
        ];
        $this->assign('data', $data);
        return $this->fetch('public/table_builder');
    }

    function scoreDetail()
    {
        $id = input('id');
        $test_score = TestScore::find($id);
        $where[] = ['test_id','=',$test_score['test_id']];
        $where[] = ['student_id','=',$test_score['student_id']];
        $data = [
            'title' => '打分列表',
            'collection' => TestScore::where($where)->order('test_id desc,id desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['姓名', '班级', '考试', '试题', '正确答案', '录入答案', '是否正确','得分', '录入时间'],
            'fields' => [
                function ($row) {
                    echo $row->student['name'];
                },
                function ($row) {
                    echo $row->clazz['name'];
                },
                function ($row) {
                    echo $row->tests['title'];
                },
                function ($row) {
                    echo mb_substr(strip_tags($row['question']['body']), 0, 30) . '...';
                },
                function ($row) {
                    echo $row->question['answer'];
                },  'answer',
                function ($row) {
                    echo $row['is_right'] == 1 ? '是' : '否';
                },
               'score', 'add_time'
            ],
            'mbuttons' => [
                ['title' => '作废', 'onclick' => 'delOne', 'url' => url('delScore'),'permission_id' => 167],
            ],
        ];
        $this->assign('data', $data);
        return $this->fetch('public/table_builder');
    }
    function delScore()
    {
        echo TestScore::destroy(input('id/d')) ? 1 : 0;
    }

    // 精彩瞬间
    function moments()
    {
        $where =[];
        if (input('clazz_id'))
            $where[] = ['clazz_id', '=', input('clazz_id')];
//        $data = [
//            'title' => '精彩瞬间',
//            'collection' => Media::where($where)->with('clazz')->order('edit_time desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
//            'thead' => ['封面', '类型', '班级', '学员', '描述', '编辑时间',''],
//            'fields' => [
//                function ($row) {
//                    return $row['images'] ? '<image src="' . $row['images'][0] . '" class="ui tiny image">' : '';
//                },
//                function ($row) {
//                    return $row['video_url'] ? '视频' : '照片';
//                },
//                function ($row) {
//                    return $row->clazz ? $row->clazz['name'] : '无';
//                },
//                function ($row) {
//                    return \app\common\model\Student::getNameStrByIds($row->student_ids);
//                },
//                function ($row) {
//                    echo mb_substr(strip_tags($row['content']), 0, 30) . '...';
//                },
//                'edit_time',
//                function ($row) {
//                    return '<a class="ui tiny label" data-title="预览" data-url="' . url('previewMoment') . '" onclick="openWin(this, \'' . $row['id'] . '\', \'90%\',500)">预览</a>'
//                        . ($row['video_url'] ?
//                        '<a class="ui tiny label" data-title="修改视频" data-url="' . url('saveMoment',['type' => 2]) . '" onclick="openBigWin(this, \'' . $row['id'] . '\')">修改</a>' :
//                        '<a class="ui tiny label" data-title="修改图片" data-url="' . url('saveMoment',['type' => 1]) . '" onclick="openBigWin(this, \'' . $row['id'] . '\')">修改</a> ' );
//                }
//            ],
//            'buttons' => [
//                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('delMoment')],
//            ],
//            'mbuttons' => [
//                ['title' => '腾讯云视频列表', 'onclick' => 'openBigWin', 'url' => url('tencent/vodList'), 'class_name'=>''],
//                ['title' => '添加图片', 'onclick' => 'openBigWin', 'url' => url('saveMoment',['type' => 1])],
//                ['title' => '添加视频', 'onclick' => 'openBigWin', 'url' => url('saveMoment',['type' => 2])],
//            ],
//        ];
        $this->assign('data', Media::where($where)->with('clazz')->order('id desc')->paginate(10, false, ['query' => $this->request->param()]));
        return $this->fetch();

    }
    function saveMoment()
    {
        $type = input('type');
        if (request()->isPost()) {
            $data = input('post.');
            if(isset($data['images'])) {
                $data['images'] = implode(',', $data['images']);
            }
            if(isset($data['video_url'])) {
                $t = explode(',', $data['video_url']);
                $data['video_url'] = $t[0];
                $data['images'] = $t[1];
                $data['images'] = $t[1];
            }
            $data['edit_time'] = now();
            $data['editor_id'] = session('login_id');

            if($type == 1) {
                $vali = 'moment_saving';
            } else {
                $vali = 'moment_saving1';
            }
            if ($id = input('id')) {
                $this->modelUpdate(MediaModel::class, $data, ['id' => $data['id']], $vali, '修改精彩瞬间');
            } else {
                $data['add_time'] = now();

                $this->modelCreate(MediaModel::class, $data, $vali, '添加精彩瞬间',function($item) {
                    ClazzEvent::addOne(
                        $item->staff->name . "老师上传了精彩媒体",
                        $item['clazz_id'],
                        $item['editor_id'],
                        ClazzEvent::TYPE_MEDIA,
                        $item['id'],
                        $item['student_ids']
                    );
                });
            }

        } else {
            if($type == 1) {
                $func = 'makeFromData1';
            } else {
                $func = 'makeFromData2';
            }
            $id = input('id');
            $data = [];
            if($id) {
                $data = MediaModel::find($id);
                if($data['video_url']) {
                    $data['video_url'] .= ',' . implode('', $data['images']); // 视频地址在前, 封面在后
                }
            }
            return $this->fetchFormPageHtml(MediaModel::class, url(__FUNCTION__, ['type' => $type]), $data, $func);
        }
    }
    function delMoment()
    {
        echo Media::destroy(input('id/d')) ? 1 : 0;
    }
    function previewMoment()
    {
        $id = input('id/d');
        $data = Media::find($id);
        $this->assign('data', $data);
        $this->assign('previous_id', Media::where('id','>', $id)->value('id'));
        $this->assign('next_id', Media::where('id','<', $id)->order('id desc')->value('id'));
        return $this->fetch('preview');

//        $content = "<div style='padding: 20px;'>";
//        if(!empty($data['video_url'])) {
//            foreach ($data['video_url'] as $v) {
//                $content .= "<video style='width: 100%' src='{$v}' controls='controls'>该浏览器不支持video标签</video>";
//            }
//        } else {
//            foreach ($data['images'] as $v) {
//                $content .= "<img style='width: 100%' src='{$v}'/>";
//            }
//        }
//        $content .= "</div><p style='padding: 20px; padding-top: 0;'>{$data['content']}</p>";
//        return $this->returnHtml($content, '精彩瞬间预览');
    }

    // 作业圈
    function zoneTask()
    {
        $where = [];
        if (input('clazz_id'))
            $where[] = ['clazz_id', '=', input('clazz_id')];

        $data = [
            'title' => '作业圈',
            'collection' => ZoneTask::with('clazz,zone')->where($where)->order('id desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['ID','班级', '作业要求', '完成/待点评数量', '发布时间'],
            'fields' => [
                'id',
                function ($row) {
                    echo $row->clazz['name'];
                },
                'content',
                function ($row) {
                    $count = $row->zone()->where('verify_content','')->count();
                    echo $row->zone()->count() .' / <span style="' . ($count > 0 ? 'color:red' : '') . '">'. $count .'</span>';
                },
                'add_time',
            ],
            'buttons' => [
                ['title' => '修改', 'onclick' => 'openBigWin','url' => url('editZoneTask')],
                ['title' => '完成列表', 'onclick' => 'openBigWin', 'url' => url('zonePublished')],
            ],
            'mbuttons' => [
                ['title' => '添加作业', 'onclick' => 'openBigWin','url' => url('editZoneTask')],
            ],
            'searcher' => [
                ['type' => 'select', 'name' => 'clazz_id', 'options' => Clazz::column('name', 'id'), 'placeholder' => '选择班级'],
            ],
        ];
        $this->assign('data', $data);
        return $this->fetch('public/table_builder');
    }
    public function editZoneTask()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $data['content'] = $_POST['content'];
            if ($id = input('id')) {
                $this->modelUpdate(ZoneTaskModel::class, $data, ['id' => $data['id']], 'zone_task_saving', '修改作业');
            } else {
                $data['add_time'] = now();
                $this->modelCreate(ZoneTaskModel::class, $data, 'zone_task_saving', '添加作业');
            }
        } else {
            return $this->fetchFormPageHtml(ZoneTaskModel::class, __FUNCTION__);
        }
    }
    // 作业完成列表
    function zonePublished()
    {
        $where[] = ['task_id', '=', input('id')];
        if (input('clazz_id'))
            $where[] = ['clazz_id', '=', input('clazz_id')];
        if (input('student_id'))
            $where[] = ['student_id', '=', input('student_id')];

        $data = [
            'title' => '作业发布列表',
            'collection' => Zone::with('clazz,student')->where($where)->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['发布时间', '学生', '内容', '媒体', '点评内容'],
            'fields' => [
                'add_time',
                function ($row) {
                    echo $row->student['name'];
                },
                'content',
                function ($row) {
                    if($row['type'] == 2) {
                        echo "<audio src='".$row['attach'] ."' controls>该浏览器不支持audio标签</audio>";
                    }
                    if($row['type'] == 3) {
                        foreach ( explode(',',$row['attach']) as $img ) {
                            echo '<img src="'.$img.'" style="width:80px; margin:0 3px;" onclick="previewImage(this)"/>';
                        }
                    }
                },
                function ($row) {
                    if($row['verify_content'] == false) {
                        echo '<a class="ui tiny orange label" data-title="点评" data-url="' . url('zoneVerify') . '" onclick="openWin(this, \'' . $row['id'] . '\')">点评</a> ' ;
                    } else {
                        echo $row['verify_content'] . '<br>分数：'. $row['verify_score'];
                    }
                },
            ],
            'searcher' => [
                ['type' => 'select', 'name' => 'student_id', 'options' => \app\common\model\Student::column('name', 'id'), 'placeholder' => '选择学生'],
                ['type' => 'select', 'name' => 'clazz_id', 'options' => Clazz::column('name', 'id'), 'placeholder' => '选择班级'],
            ],
        ];
        $this->assign('data', $data);
        return $this->fetch('public/table_builder');
    }
    public function zoneVerify()
    {
        if (request()->isPost()) {
            $id = input('id');
            $content = input('verify_content');
            $score = input('verify_score');
            \app\common\model\Zone::verify($id, session('login_id'), $content, $score) ?
                $this->success('已发布') :
                $this->error('发布失败');
        } else {
            return $this->fetchFormPageHtml(ZoneModel::class, __FUNCTION__);
        }
    }
}
