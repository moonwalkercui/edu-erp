<?php

namespace app\backend\controller;

use app\backend\model\KnowledgeModel;
use app\backend\model\QuestionCategoryModel;
use app\backend\model\QuestionModel;
use app\backend\model\SectionModel;
use app\backend\model\TestCategoryModel;
use app\backend\model\TestModel;
use app\backend\model\TestPartModel;
use app\common\model\H5;
use app\common\model\Question;
use app\common\model\QuestionCategory;
use app\common\model\QuestionGroup;
use app\common\model\TestCategory;
use app\common\model\TestPart;
use app\common\model\TestQuestion;
use think\Db;
use think\Exception;

/**
 * 测试管理
 */
class Test extends Base
{

    function getList()
    {
        $where = [];
        if($kw = input('kw')) {
            $ids = [];
            $s_ids = SectionModel::where('title','like',"%{$kw}%")->column('id');
            if(!empty($s_ids)) $ids = array_merge($ids, Db::name('section_test')->where('section_id','in', $s_ids)->column('test_id')) ;
            $k_ids = KnowledgeModel::where('title','like',"%{$kw}%")->column('id');
            if(!empty($k_ids)) {
                $q_ids = Db::name('knowledge_question')->where('knowledge_id','in', $k_ids)->column('question_id');
                if(!empty($q_ids)) {
                    $ids = array_merge($ids, Db::name('test_question')->where('question_id','in', $q_ids)->column('test_id')) ;
                }
            }
            $ids = array_merge($ids, TestModel::where('title','like',"%{$kw}%")->column('id'));
            $where[] = ['id', 'in', array_unique($ids)];
        }
        if($section = input('section')) {
            $where[] = ['id', 'in', Db::name('section_test')->where('section_id', $section)->column('test_id')];
        }
        if($category_id = input('category_id')) {
            $where[] = ['category_id', '=', $category_id];
        }
        if($sort_by = input('sort_by')) {
            $order = $sort_by . " ". input('sort_type','desc');
        } else {
            $order = 'id desc';
        }
        $data = [
            'title' => '试卷列表',
            'collection' => TestModel::where($where)->order($order)->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['ID', '标题', '关联课时', '关联知识点', '创建人',
                ['修改时间',['sort' => 'edit_time','sort_type'=>'asc']],
            ],
            'fields' => ['id', 'title',
                function ($row) {
                    return implode(',', array_column($row['section']->toArray(), 'title'));
                },
                function ($row) {
                    return implode(',', array_column($row['knowledge']->toArray(), 'title'));
                },
                'editor_id', 'add_time'],
            'buttons' => [
                ['title' => '预览', 'onclick' => 'openBigWin', 'url' => url('preview')],
                ['title' => '修改', 'onclick' => 'openBigWin', 'url' => url('saveTest'),'permission_id' => 168],
                ['title' => '组题', 'onclick' => 'openBigWin', 'url' => url('saveTestQuestion'),'permission_id' => 169],
                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('deleteTest'),'permission_id' => 170],
            ],
            'mbuttons' => [
                ['title' => '分 类', 'onclick' => 'openBigWin', 'url' => url('testCategory'), 'class_name' => '','permission_id' => 171],
                ['title' => '组 卷', 'onclick' => 'openBigWin', 'url' => url('saveTest'),'permission_id' => 168],
            ],
            'searcher' => [
                ['type' => 'input', 'name' => 'kw', 'placeholder' => '关键字搜索'],
                ['type'=>'select', 'name'=>'category_id', 'options' => TestCategory::selectOptions()],
                ['type' => 'select', 'name'=>'section', 'options' => SectionModel::column('title','id'), 'placeholder' => '按课时搜索'],
            ],
        ];
        if (input('act') == 'select') {
            $data['select_type'] = 2;
        }
        $this->assign('data', $data);
        return $this->fetch('public/table_builder');
    }

    function preview()
    {
        $id = input('id/d');
        if(!$id) $this->error("缺少参数");
        $test = \app\common\model\Test::find($id);
        $list = \app\common\model\Test::getQuestionList($id);
//        dump($list);die;
        $this->assign('test', $test);
        $this->assign('list', $list);
        return $this->fetch();
    }

    function saveTest()
    {
        $id = input('id');
        if (request()->isPost()) {
            $data = input('post.');
            $data['edit_time'] = now();
            $data['editor_id'] = session('login_id');
            unset($data['sections']);
            unset($data['parts']);
            unset($data['score']);
            unset($data['part_ids']);
            Db::startTrans();
            try {

                if ($id = input('id')) {
                    if (false === TestModel::where(compact('id'))->update($data)) {
                        $this->error('更新出错');
                    }
                } else {
                    $data['add_time'] = now();
                    $id = TestModel::insertGetId($data);
                    if (!$id) {
                        $this->error('添加试题出错');
                    }
                }

                // 处理课时关联
                $sections = [];
                if (input('sections')) {
                    $sections = input('sections/a');
                }
                TestModel::updateSectionsRel($id, $sections);

                // 处理部分关联
                $parts = $_POST['parts'];
                $part_ids = input('part_ids/a');
                $score = input('score/a');
                if (count($parts) == 0) $this->error('未进行部分划分');
                TestPart::updateTestRel($id, $parts, $part_ids, $score);

                Db::commit();
            } catch (Exception $e) {
                Db::rollback();
                $this->error($e->getMessage());
            }

            $this->redirect('saveTestQuestion', compact('id'));
        } else {
            if ($id) {
                $test = \app\common\model\Test::find($id);
                $sections_ids = array_column($test->section->toArray(), 'id');
                $test['section_ids'] = $sections_ids;
            } else {
                $test = null;
            }
            $this->assign('sections', SectionModel::getList());
            $this->assign('category', TestCategoryModel::getListTree());
            $this->assign('data', $test);
            return $this->fetch();
        }

//        $id = input('id');
//        if (request()->isPost()) {
//            $data = input('post.');
//            $sections = [];
//            if(input('sections')) {
//                $sections = explode(',', $data['sections']);
//            }
//            $data['edit_time'] = now();
//            $data['editor_id'] = session('login_id');
//            if ($id = input('id')) {
//                $this->modelUpdate(TestModel::class, $data, ['id' => $data['id']], 'save_test', '修改试卷', function($cond) use ($sections){
//                    TestModel::updateSectionsRel($cond['id'], $sections);
//                });
//            } else {
//                $data['add_time'] = now();
//                $this->modelCreate(TestModel::class, $data, 'save_test', '添加试卷', function($item) use ($sections){
//                    TestModel::updateSectionsRel($item->id, $sections);
//                });
//            }
//        } else {
//            $data = [];
//            if($id) $data = TestModel::find($id);
//            return $this->fetchFormPageHtml( TestModel::class, url(__FUNCTION__, ['id' => $id]), $data, 'makeFromData', 'id', '下一步 组题');
//        }
    }

    function saveTestQuestion()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $section = input('section/a');
            $score = input('score/a');
            $qids = input('qids/a');
            $sort_values = input('sort_values', null); // id排序
            dump($data);
            die;
            if ($id = input('id')) {
                $this->modelUpdate(TestModel::class, $data, ['id' => $data['id']], 'only_name', '修改试卷');
            } else {
                $this->modelCreate(TestModel::class, $data, 'only_name', '添加试卷');
            }
        } else {
            $id = input('id');
            $test = \app\common\model\Test::find($id);
            $this->assign('test', $test);
            $this->assign('data', $test->part()->with('testQuestion.question')->select());
            return $this->fetch();
        }
    }


    function deleteTest()
    {
        $id = input('id/d');
        echo TestModel::destroy($id) ? 1 : 0;
    }

    function loadData()
    {
        $data = TestModel::where('id', 'in', input('ids/a'))->order('edit_time desc')->select();
        return $this->dataJson($data);
    }

    // 分类
    function testCategory()
    {
        $this->assign('data', [
            'title' => '试卷分类',
            'collection' => TestCategoryModel::getListTree(),
            'thead' => ['ID', '名称'],
            'fields' => ['id', function ($row) {
                return $row['_html'] . $row['name'];
            }],
            'buttons' => [
                ['title' => '编辑', 'url' => url('saveTestCategory')],
                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('deleteTestCategory')],
            ],
            'mbuttons' => [
                ['title' => '添加', 'url' => url('saveTestCategory')],
            ],
            'searcher' => [
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    // 编辑
    function saveTestCategory()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if ($id = input('id')) {
                if ($data['parent_id'] == $id) {
                    $this->error('分类设置错误');
                }
                $this->modelUpdate(TestCategoryModel::class, $data, ['id' => $data['id']], 'only_name', '修改试卷分类');
            } else {
                $this->modelCreate(TestCategoryModel::class, $data, 'only_name', '添加试卷分类');
            }
        } else {
            return $this->fetchFormPageHtml(TestCategoryModel::class, __FUNCTION__);
        }
    }

    function deleteTestCategory()
    {
        $id = input('id/d');
        echo QuestionCategoryModel::destroy($id) ? 1 : 0;
    }

    function getQuestionList()
    {
        $id = input('id');
        if (!$id) return $this->errorJson('缺少参数');
        $data = QuestionModel::getByTestId($id);
        return $this->dataJson($data);
    }

    function loadGroupQuestion()
    {
        $id = input('id');
        if (!$id) return $this->errorJson('缺少参数');
        $data = Question::where('group_id', $id)->order('sort_num')->select();
        return $this->dataJson($data);
    }

    // 试题列表
    function questionList()
    {
        $where = [];
        if($kw = input('kw')) {
            $where[] = ['id', 'in', Question::where('body|explain|answer','like',"%{$kw}%")->column('id')];
        }
        if($section = input('section')) {
//            $ids = [];
//            $s_ids = SectionModel::where('title','like',"%{$section}%")->column('id');
//            if(!empty($s_ids)) $ids = array_merge($ids, Db::name('section_question')->where('section_id','in', $s_ids)->column('question_id')) ;
//            $where[] = ['id', 'in', $ids];

            $where[] = ['id', 'in', Db::name('section_question')->where('section_id',$section)->column('question_id')];
        }
        if($category_id = input('category_id')) {
            $where[] = ['category_id', '=', $category_id];
        }
        if($sort_by = input('sort_by')) {
            $order = $sort_by . " ". input('sort_type','desc');
        } else {
            $order = 'id desc';
        }
        $data = [
            'title' => '试题列表',
            'collection' => Question::selectWithGroup($where, $order),
            'thead' => ['ID', '标题', '类型','知识点讲解',
                ['修改时间',['sort' => 'edit_time','sort_type'=>'asc']],
                ['', ['style' => 'width:70px']]
            ], //  '创建人',
            'fields' => [
                'id', 'body', //'editor_id',
                function ($row) {
                    if($row['q_group']['body']) {
                        return '综合题';
                    } else {
                        return $row['type_name'];
                    }
                },
                function ($row) {
                    $h5_list = H5::getByKnowledge($row->knowledge);
                    foreach ($h5_list as $k => $v) {
                        echo ($k+1).". <a style='cursor: pointer' data-title='预览' data-url='/backend/knowledge/h5preview.html' onclick='openBigWin(this, {$v['id']})'>{$v['title']}</a><br>";
                    }
                },
                'edit_time',
                function ($row) {
                    return $row['q_group']['body'] ?
                        '<a class="ui tiny label" data-title="编辑综合题" data-url="' . url('test/saveQuestionGroup') . '" onclick="openBigWin(this, \'' . $row['group_id'] . '\')">修改</a> ' :
                        '<a class="ui tiny label" data-title="编辑试题" data-url="' . url('test/saveQuestion') . '" onclick="openBigWin(this, \'' . $row['id'] . '\')">修改</a> ';
                }
            ],
            'buttons' => [
                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('deleteQuestion'),'permission_id' => 175],
            ],
            'mbuttons' => [
                ['title' => '分 类', 'onclick' => 'openBigWin', 'url' => url('questionCategory'), 'class_name' => '','permission_id' => 174],
                ['title' => '添加试题', 'onclick' => 'openBigWin', 'url' => url('saveQuestion'),'permission_id' => 172],
                ['title' => '添加综合题', 'onclick' => 'openBigWin', 'url' => url('saveQuestionGroup'),'permission_id' => 173],
            ],
            'searcher' => [
                ['type' => 'input', 'name' => 'kw', 'placeholder' => '关键字搜索'],
                ['type' => 'select', 'name'=>'category_id', 'options' => QuestionCategory::selectOptions()],
                ['type' => 'select', 'name'=>'section', 'options' => SectionModel::column('title','id'), 'placeholder' => '按课时搜索'],
            ],
        ];
        if (input('act') == 'select') {
            $data['buttons'] = [
                ['title' => '选择', 'onclick' => 'chooseOne', 'url' => url('saveTest'), 'class_name' => 'green'],
            ];
        }
        if (input('act') == 'mselect') {
            $data['select_type'] = 2;
        }
        $this->assign('data', $data);

        return $this->fetch('public/table_builder');
    }

    function deleteQuestion()
    {
        $id = input('id/d');
        $q = Question::find($id);
        if ($q->group_id) {
            Question::where('group_id', $q->group_id)->update(['delete_time' => time()]);
            echo QuestionGroup::destroy($q->group_id) ? 1 : 0;
        } else {
            echo Question::destroy($id) ? 1 : 0;
        }
    }

    function loadTestQuestions()
    {
        $ids = input('ids/a');
        if (empty($ids)) return $this->errorJson("参数错误");
        $data = Question::where('id', 'in', $ids)->append(['type_name', 'title'])->order('id desc, group_id desc')->select()->each(function ($item) {
            $item->title2 = $item->group_id ? "[组{$item->group_id}] " . $item->title : $item->title;
        });
        return $this->dataJson($data);
    }

    function updateTestQuestions()
    {
        $ids = input('ids/a');
        if (empty($ids)) return $this->errorJson("参数错误1");

        // 查找综合题，一起追加。
        $add_ids = [];
        foreach ($ids as $q_id) {
            $question = Question::find($q_id);
            if($question['group_id'] > 0) {
                $add_ids = array_merge($add_ids, Question::where('group_id', $question['group_id'])->column('id'));
            }
        }
        $ids = array_unique(array_merge($ids, $add_ids));

        $part_id = input('part_id');
        if (empty($part_id)) return $this->errorJson("参数错误2");

        $part = TestPart::where('id', $part_id)->find();

        $exist_qids = TestQuestion::where('test_id', $part['test_id'])->column('question_id');
        $insert = [];
        foreach ($ids as $k => $question_id) {
            if (empty($exist_qids) || (!empty($exist_qids) && !in_array($question_id, $exist_qids)))
                $insert[] = [
                    'test_id' => $part['test_id'],
                    'question_id' => $question_id,
                    'score' => $part['score'],
                    'group_id' => Question::where('id',$question_id)->value('group_id'),
                    'sort_num' => $k,
                    'part_id' => $part_id,
                ];
        }
        if (!empty($insert)) TestQuestion::insertAll($insert);
//        $data = Question::where('id', 'in', $ids)->append(['type_name', 'title'])->order('id desc, group_id desc')->select()->each(function ($item) {
//            $item->title2 = $item->group_id ? "[组{$item->group_id}] " . $item->title : $item->title;
//        });
        return $this->successJson("已设置问题");
    }

    function deleteTestQuestion()
    {
        $id = input('id/d');
        echo TestQuestion::destroy($id) ? 1 : 0;
    }

    function addTestPart()
    {
//        $test_id = input('test_id/d');
//        if ($test_id == 0) return $this->errorJson("参数错误");
//        TestPart::insert([
//            'test_id' => $test_id,
//            'sort_num' => TestPart::where(compact('test_id'))->count()
//        ]);
        if (request()->isPost()) {
            $data = input('');
            $this->modelCreate(TestPartModel::class, $data, '', '添加试题部分');
        } else {
            return $this->fetchFormPageHtml(TestPartModel::class, url(__FUNCTION__, ['test_id' => input('test_id')]));
        }
    }

    function updateTestPart()
    {
        $type = input('type');
        if (!$type) return $this->errorJson("参数错误1");
        $id = input('id/d');
        if (!$id) return $this->errorJson("参数错误2");

        return TestPart::where('id', $id)->update([$type => input('value', '')]) !== false ? $this->successJson("OK") : $this->errorJson("OK");
    }

    function deleteTestPart()
    {
        $id = input('id/d');
        TestQuestion::where('part_id', $id)->delete();
        echo TestPart::destroy($id) ? 1 : 0;
    }

    function updateTestQuestionsScore()
    {
        $id = input('id');
        if (empty($id)) return $this->errorJson("参数错误1");
        $score = input('score/d');
        if ($score <= 0) return $this->errorJson("分数设置错误");

        $res = TestQuestion::where(compact('id'))->update(['score' => $score]);

        return $res ? $this->successJson("OK") : $this->errorJson("OK");
    }

    function sortTestQuestions()
    {
        $value = input('value');
        if (!$value) return $this->errorJson("参数错误");
        $map = [];
        if (input('part_id')) $map['part_id'] = input('part_id');
        foreach (explode('|', $value) as $k => $id) {
            $map['sort_num'] = $k;
            TestQuestion::where('id', $id)->update($map);
        }
        return $this->successJson("OK");
    }

    // 编辑试题
    function saveQuestionGroup()
    {
        $editor_id = session('login_id');
        if (request()->isPost()) {
            $id = input('group_id');
            $update = [];
            if ($_POST['group_body']) $update['body'] = $_POST['group_body'];

            if (input('type') != 1) {
                $group = QuestionGroup::find($id);
                if (count($group->question) == 0) return $this->errorJson('未设置试题');
                if (!$update['body']) return $this->errorJson('未设置题干');
            }
            $res = null;
            if (!empty($update)) $res = QuestionGroup::where(compact('id'))->update($update);

            return $res !== false ? $this->successJson('已保存') : $this->errorJson('保存失败');
        } else {
            $group_id = input('id');
            if (!$group_id) {
                $group_id = QuestionGroup::addOne($editor_id);
            }
            $this->assign('data', QuestionGroup::find($group_id));
            return $this->fetch();
        }
    }

    // 编辑试题
    function saveQuestion()
    {
        if (request()->isPost()) {
            $editor_id = session('login_id');
            $id = input('id/d');
            Db::startTrans();
            try {
                Question::saveOne(
                    $id,
                    $editor_id,
                    input('question_type/d'),
                    $_POST['question_body'],
                    $_POST['options'],
                    $_POST['right_answer'],
                    input('rel_knowledge/a'),
                    input('rel_section/a'),
                    input('category_id/d'),
                    $_POST['explain'],
                    input('group_id'));
                Db::commit();
            } catch (\Exception $e) {
                Db::rollback();
                return $this->errorJson($e->getMessage());
            }
            return $this->successJson('已保存');
        } else {
            $id = input('id');
            $data = Question::where(compact('id'))->with('knowledge,section,opt')->find();
            $this->assign('data', $id ? json_encode($data) : '');
            $this->assign('group_id', input('group_id'));
            $this->assign('knowledge', KnowledgeModel::select());
            $this->assign('section', SectionModel::field('id,title')->select());
            $this->assign('category', QuestionCategory::getListTree());

            return $this->fetch();
        }
    }

    function delQuestion()
    {
        $id = input('id');
        if (!$id) return $this->errorJson('缺少参数');
        return Question::destroy($id) ?
            $this->successJson('已删除') :
            $this->errorJson('删除失败');
    }

    function sortQuestion()
    {
        $ids = input('ids');
        if (!$ids) return $this->errorJson('缺少参数');
        return Question::doSort(explode("|", $ids)) ?
            $this->successJson('已排序') :
            $this->errorJson('排序失败');
    }

    function questionCategory()
    {
        $this->assign('data', [
            'title' => '试题分类',
            'collection' => QuestionCategory::getListTree(),
            'thead' => ['ID', '名称'],
            'fields' => ['id', function ($row) {
                return $row['_html'] . $row['name'];
            }],
            'buttons' => [
                ['title' => '编辑', 'url' => url('saveCategory')],
                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('deleteCategory')],
            ],
            'mbuttons' => [
                ['title' => '添加', 'url' => url('saveCategory')],
            ],
            'searcher' => [
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    // 编辑
    function saveCategory()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if ($id = input('id')) {
                if ($data['parent_id'] == $id) {
                    $this->error('分类设置错误');
                }
                $this->modelUpdate(QuestionCategoryModel::class, $data, ['id' => $data['id']], 'only_name', '修改试题分类');
            } else {
                $this->modelCreate(QuestionCategoryModel::class, $data, 'only_name', '添加试题分类');
            }
        } else {
            return $this->fetchFormPageHtml(QuestionCategoryModel::class, __FUNCTION__);
        }
    }

    function deleteCategory()
    {
        $id = input('id/d');
        echo QuestionCategoryModel::destroy($id) ? 1 : 0;
    }


//    // 编辑试题
//    function saveQuestionGroup()
//    {
//        if (request()->isPost()) {
//            $editor_id = session('login_id');
//            $group_id = input('group_id/d');
//            if (!$group_id) {
//                $group_id = QuestionGroup::addOne($editor_id, $_POST['group_body']);
//            }
//            $question_id = input('question_id/d');
//            Db::startTrans();
//            try {
//                Question::saveOne(
//                    $question_id,
//                    $editor_id,
//                    input('question_type/d'),
//                    $_POST['question_body'],
//                    input('options/a'),
//                    input('right_answer', ''),
//                    input('rel_knowledge/a'),
//                    input('rel_section/a'),
//                    input('category_id/d'),
//                    $_POST['explain'],
//                    $group_id);
//
//                Db::commit();
//            } catch (\Exception $e) {
//                Db::rollback();
//                return $this->errorJson($e->getMessage() . $e->getFile(). $e->getLine());
//            }
//            return $this->dataJson([
//                'group_id' => $group_id,
//            ]);
//
//        } else {
//            $id = input('id');
////            $this->assign('group_id', $id);
//            $this->assign('data', $id ? QuestionGroup::find($id) : null);
//            return $this->fetch();
//        }
//    }
//
//    // 编辑试题
//    function saveQuestion()
//    {
//        if (request()->isPost()) {
//            $editor_id = session('login_id');
//            $question_id = input('question_id/d');
//            Db::startTrans();
//            try {
//                Question::saveOne(
//                    $question_id,
//                    $editor_id,
//                    input('question_type/d'),
//                    $_POST['question_body'],
//                    input('options/a'),
//                    input('right_answer', ''),
//                    input('rel_knowledge/a'),
//                    input('rel_section/a'),
//                    input('category_id/d'),
//                    input('explain'));
//
//                Db::commit();
//            } catch (\Exception $e) {
//                Db::rollback();
//                return $this->errorJson($e->getMessage());
//            }
//            return $this->successJson('已保存');
//        } else {
//            $id = input('id');
//            $this->assign('question_id', $id);
//            return $this->fetch();
//        }
//    }

//    function upQuestion()
//    {
//        $id = input('id');
//        if (!$id) return $this->errorJson('缺少参数');
//        return Question::up($id) ?
//            $this->successJson('已上移') :
//            $this->errorJson('移动失败');
//    }
//
//    function downQuestion()
//    {
//        $id = input('id');
//        if (!$id) return $this->errorJson('缺少参数');
//        return Question::down($id) ?
//            $this->successJson('已下移') :
//            $this->errorJson('移动失败');
//    }
}