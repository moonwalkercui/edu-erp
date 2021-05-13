<?php

namespace app\backend\controller;

use app\backend\model\AuthRole;
use app\backend\model\KnowledgeModel;
use app\backend\model\SectionCategoryModel;
use app\backend\model\SectionModel;
use app\backend\model\StaffModel;
use app\backend\model\TestModel;
use app\common\model\SectionCategory;
use app\common\model\ZoneTask;
use think\Db;
use think\Exception;

/**
 * 课时管理
 */
class Section extends Base
{
    // 列表
    function getList()
    {
        $where = [];
        if($kw = input('kw')) {
            $ids = [];
            $k_ids = \app\common\model\Knowledge::where('title','like',"%{$kw}%")->column('id');
            if(!empty($k_ids)) $ids = array_merge($ids, Db::name('knowledge_section')->where('knowledge_id','in', $k_ids)->column('section_id')) ;
            $ids = array_merge($ids, SectionModel::where('title','like',"%{$kw}%")->column('id'));
            $where[] = ['id', 'in', array_unique($ids)];
        }
        if($category = input('category')) {
            $where[] = ['category_id', '=', $category ];
        }
        if($sort_by = input('sort_by')) {
            $order = $sort_by . " ". input('sort_type','desc');
        } else {
            $order = 'id desc';
        }
        $this->assign('data', [
            'title' => '章节列表',
            'collection' => SectionModel::where($where)->order($order)->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => [
                'ID',
                ['标题',['sort' => 'title','sort_type'=>'asc']],
                ['分类',['sort' => 'category_id','sort_type'=>'asc']],
                '内容',
                '重点',
            ],
            'fields' => [
                'id', 'title',
                function($row) {echo $row->category ? $row->category->name : '-';},
                function($row) {echo substr_cn($row['remark'], 20);},
                function($row) {echo $row['training'] ? substr_cn($row['training'], 20) : '无';},
            ],
            'buttons' => [
//                ['title' => '预览', 'onclick' => 'openBigWin', 'url' => url('preview')],
                ['title' => '编辑', 'onclick' => 'openBigWin', 'url' => url('saveSection'),'permission_id' => 176],
                ['title' => '删除', 'onclick' => 'delOne', 'url'=> url('deleteSection'),'permission_id' => 177],
            ],
            'mbuttons' => [
                ['title' => '分 类', 'onclick' => 'openBigWin', 'url' => url('sectionCategory'), 'class_name' => ''],
                ['title' => '添 加', 'onclick' => 'openBigWin', 'url' => url('saveSection'),'permission_id' => 176],
            ],
            'searcher' => [
                ['type' => 'input', 'name' => 'kw', 'placeholder' => '关键字搜索'],
                ['type' => 'select', 'name' => 'category', 'placeholder' => '分类', 'options' => SectionCategoryModel::getTreeColumn()],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    function getList2()
    {
        $where = [];
        if($kw = input('kw')) {
            $ids = [];
            $k_ids = \app\common\model\Knowledge::where('title','like',"%{$kw}%")->column('id');
            if(!empty($k_ids)) $ids = array_merge($ids, Db::name('knowledge_section')->where('knowledge_id','in', $k_ids)->column('section_id')) ;
            $ids = array_merge($ids, SectionModel::where('title','like',"%{$kw}%")->column('id'));
            $where[] = ['id', 'in', array_unique($ids)];
        }
        if($sort_by = input('sort_by')) {
            $order = $sort_by . " ". input('sort_type','desc');
        } else {
            $order = 'id desc';
        }
        $this->assign('data', [
            'title' => '章节列表',
            'collection' => SectionModel::where($where)->order($order)->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => [
                'ID',
                ['章节标题',['sort' => 'title','sort_type'=>'asc']],
                '重点词汇',
                '重点句型',
                '附加词汇',
                '附加句型',
                ['发音',['style'=>'width:11%']],
                '授课目标'
            ],
            'fields' => [
                'id', 'title',
                function($row) { $t = []; foreach ($row['knWordsImp'] as $v) $t[] = $v['title'] ; echo implode("," ,$t); },
                function($row) { $t = []; foreach ($row['knSentenceImp'] as $v) $t[] = $v['title'] ; echo implode("," ,$t); },
                function($row) { $t = []; foreach ($row['knWordsAtt'] as $v) $t[] = $v['title'] ; echo implode("," ,$t); },
                function($row) { $t = []; foreach ($row['knSentenceAtt'] as $v) $t[] = $v['title'] ; echo implode("," ,$t); },
                function($row) { $t = []; foreach ($row['pronunciation'] as $v) $t[] = $v['title'] ; echo implode("," ,$t); },
                function($row) { echo str_replace(["\r\n","\n"],"<br />",$row['target']); },
            ],
            'buttons' => [
                ['title' => '预览', 'onclick' => 'openBigWin', 'url' => url('preview')],
                ['title' => '编辑', 'onclick' => 'openBigWin', 'url' => url('saveSection'),'permission_id' => 176],
                ['title' => '删除', 'onclick' => 'delOne', 'url'=> url('deleteSection'),'permission_id' => 177],
            ],
            'mbuttons' => [
                ['title' => '分 类', 'onclick' => 'openBigWin', 'url' => url('sectionCategory'), 'class_name' => ''],
                ['title' => '添 加', 'onclick' => 'openBigWin', 'url' => url('saveSection'),'permission_id' => 176],
            ],
            'searcher' => [
                ['type' => 'input', 'name' => 'kw', 'placeholder' => '关键字搜索'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    function preview()
    {
        $id = input('id/d');
        if(!$id) $this->error("缺少参数");
        $data = \app\common\model\Section::find($id);
        $this->assign('data', $data);
        return $this->fetch('test/section_preview');
    }

    function saveSection()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if ($id = input('id')) {
                $this->modelUpdate(SectionModel::class, $data, ['id' => $data['id']], 'section_saving', '修改课时');
            } else {
                $this->modelCreate(SectionModel::class, $data, 'section_saving', '添加课时'
//                    ,function ($item) { ZoneTask::updateTask($item['id']); }
                    );
            }

        } else {
            return $this->fetchFormPageHtml(SectionModel::class, __FUNCTION__);
        }
    }

    // 编辑
    function saveSection2()
    {
        if (request()->isPost()) {
            $id = input('id');

            $words_imp = input('words_imp/a');
            $sentence_imp = input('sentence_imp/a');
            $words_att = input('words_att/a');
            $sentence_att = input('sentence_att/a');
            $pronunciation = input('pronunciation/a');

            if(!empty($words_imp)) KnowledgeModel::scanAndMerge($words_imp, 1);
            if(!empty($sentence_imp)) KnowledgeModel::scanAndMerge($sentence_imp, 2);
            if(!empty($words_att)) KnowledgeModel::scanAndMerge($words_att, 1);
            if(!empty($sentence_att)) KnowledgeModel::scanAndMerge($sentence_att, 2);
            if(!empty($pronunciation)) KnowledgeModel::scanAndMerge($pronunciation, 3);

            $test_ids = input('test_ids/a');
            $h5_ids = input('h5_ids/a');

            $data_sec['title'] = input('title');
            $data_sec['edit_time'] = now();
            $data_sec['target'] = $_POST['target'];
            $data_sec['layout'] = $_POST['layout'];
            $data_sec['remark'] = $_POST['remark'];
            $data_sec['category_id'] = input('category_id');
            Db::startTrans();
            try {
                if ($id) {
                    SectionModel::where(compact('id'))->update($data_sec);
                } else {
                    $data_sec['add_time'] = now();
                    $id = SectionModel::insertGetId($data_sec);
                }
                $section = SectionModel::find($id);
                SectionModel::deleteRel($id);

                if (!empty($words_imp)) $section->knowledge()->attach($words_imp, ['type' => SectionModel::WORDS_IMP]);
                if (!empty($sentence_imp)) $section->knowledge()->attach($sentence_imp, ['type' => SectionModel::SENTENCE_IMP]);
                if (!empty($words_att)) $section->knowledge()->attach($words_att, ['type' => SectionModel::WORDS_ATT]);
                if (!empty($sentence_att)) $section->knowledge()->attach($sentence_att, ['type' => SectionModel::SENTENCE_ATT]);
                if (!empty($pronunciation)) $section->knowledge()->attach($pronunciation, ['type' => SectionModel::PRONUNCIATION]);
                if (!empty($test_ids)) $section->test()->attach($test_ids);
                if (!empty($h5_ids)) $section->h5()->attach($h5_ids);

                Db::commit();
            } catch (Exception $e) {
                Db::rollback();
                return $this->errorJson($e->getMessage());
            }
            return $this->successJson('已提交');
        } else {
            $id = input('id');
            if($id) {
                $data = SectionModel::find($id);
                $data['knWordsImp'] = $data->knWordsImp()->column('id');
                $data['knSentenceImp'] = $data->knSentenceImp()->column('id');
                $data['knWordsAtt'] = $data->knWordsAtt()->column('id');
                $data['knSentenceAtt'] = $data->knSentenceAtt()->column('id');
                $data['pronunciation'] = $data->pronunciation()->column('id');
                $data['test'] = $data->test()->column('id');
                $data['h5'] = $data->h5()->column('id');
                $jsn = json_encode($data);
            } else {
                $jsn = '';
            }
            $this->assign('category', SectionCategoryModel::getTreeColumn());
            $this->assign('data', $jsn);
            return $this->fetch();
        }
    }

    function deleteSection()
    {
        $id = input('id/d');
        echo SectionModel::doDel($id) ? 1 : 0;
    }
    function sectionCategory()
    {
        $this->assign('data', [
            'title' => '章节分类',
            'collection' => SectionCategory::getListTree(),
            'thead' => ['ID', '名称'],
            'fields' => ['id', function ($row) {
                return $row['_html'] . $row['name'];
            }],
            'buttons' => [
                ['title' => '编辑', 'url' => url('saveSectionCategory')],
                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('deleteSectionCategory')],
            ],
            'mbuttons' => [
                ['title' => '添加', 'url' => url('saveSectionCategory')],
            ],
            'searcher' => [
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    function saveSectionCategory()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if ($id = input('id')) {
                if($data['parent_id'] == $id) {
                    $this->error('分类设置错误');
                }
                $this->modelUpdate(SectionCategoryModel::class, $data, ['id' => $data['id']], 'only_name', '修改课时分类');
            } else {
                $this->modelCreate(SectionCategoryModel::class, $data, 'only_name', '添加课时分类');
            }
        } else {
            return $this->fetchFormPageHtml(SectionCategoryModel::class, __FUNCTION__);
        }
    }

    function deleteSectionCategory()
    {
        $id = input('id/d');
        echo SectionCategoryModel::destroy($id) ? 1 : 0;
    }

}