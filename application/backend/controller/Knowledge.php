<?php

namespace app\backend\controller;

use app\backend\model\AuthRole;
use app\backend\model\H5Model;
use app\backend\model\KnowledgeCategoryModel;
use app\backend\model\KnowledgeModel;
use app\backend\model\SectionModel;
use app\backend\model\TestModel;
use app\common\model\H5;
use app\common\model\KnowledgeCategory;
use think\Db;

/**
 * 知识点管理
 */
class Knowledge extends Base
{
    // 列表
    function getList()
    {
        $where = [];
        if($kw = input('kw')) {
            $ids = [];
            $ids = array_merge($ids, KnowledgeModel::where('title','like',"%{$kw}%")->column('id'));
            $where[] = ['id', 'in', $ids];
        }
        if($section = input('section')) {
            $ids = [];
//            $s_ids = SectionModel::where('title','like',"%{$section}%")->column('id');
//            if(!empty($s_ids)) $ids = array_merge($ids, Db::name('knowledge_section')->where('section_id','in', $s_ids)->column('knowledge_id')) ;
            $where[] = ['id', 'in', Db::name('knowledge_section')->where('section_id', $section)->column('knowledge_id')];
        }
        if($category_id = input('category_id')) {
            $where[] = ['category_id', '=', $category_id];
        }
        if($sort_by = input('sort_by')) {
            $order = $sort_by . " ". input('sort_type','desc');
        } else {
            $order = 'id desc';
        }
        $this->assign('data', [
            'title' => '知识点列表',
            'collection' => KnowledgeModel::where($where)->order($order)->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['ID',
                ['内容',['style'=>'width:30%']],
                ['分类',['style'=>'width:80px']],
                ['关联课时',['style'=>'width:100px']],
                '关联试题',
//                '关联讲解',
                ['修改时间',['sort' => 'edit_time','sort_type'=>'asc']],
                ['讲解',['style'=>'width:140px']],
            ], // '关联图文', '关联试卷'
            'fields' => [
                'id', 'title',
                function($row) { $t = []; foreach ($row['category'] as $v) $t[] = $v['name'] ; echo implode("<br>" ,$t); },
                function($row) { $t = []; foreach ($row['section'] as $v) $t[] = $v['title'] ; echo implode("<br>" ,$t); },
                function($row) { $t = []; foreach ($row['question'] as $v) $t[] = "·".mb_substr(strip_tags($v['body']), 0, 20) ; echo implode("<br>" ,$t); },
//                function($row) { $t = []; foreach ($row['h5'] as $v) $t[] = "·".$v['title'] ; echo implode("<br>" ,$t); },
                'edit_time',
                function ($row) {
                    return '<a class="ui tiny label" data-title="新建讲解" data-url="' . url('saveH5',['kid' => $row['id']]) . '" onclick="openBigWin(this, 0)">新建</a>' .
                        (
                            count($row->h5) > 0 ?
                                '<a class="ui tiny label" data-title="讲解列表" data-url="' . url('h5List',['kid' => $row['id']]) . '" onclick="openBigWin(this, 0)">列表</a>':
                                ''
                        );
                },
//                function($row) { $t = []; foreach ($row['test'] as $v) $t[] = $v['title'] ; echo implode("<br>" ,$t); },
            ],
            'buttons' => [
                ['title' => '编辑', 'url' => url('saveKnowledge'), 'height'=>'700px','permission_id' => 144],
                ['title' => '删除', 'onclick' => 'delOne', 'url'=> url('delete'),'permission_id' => 146],
            ],
            'mbuttons' => [
                ['title' => '分 类', 'onclick' => 'openBigWin', 'url' => url('category'), 'class_name' => '','permission_id' => 145],
                ['title' => '添 加', 'url' => url('saveKnowledge'), 'height'=>'700px','permission_id' => 144],
            ],
            'searcher' => [
                ['type' => 'input', 'name' => 'kw', 'placeholder' => '关键字搜索'],
                ['type' => 'select', 'name'=>'category_id', 'options' => KnowledgeCategory::column('name','id')],
                ['type' => 'select', 'name'=>'section', 'options' => SectionModel::column('title','id'), 'placeholder' => '按课时搜索'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    function loadData()
    {
        $where = [];
        $data = KnowledgeModel::where($where)->with('category')->order('edit_time desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]);
        return $this->dataJson($data);
    }

    // 编辑
    function saveKnowledge()
    {
        if(request()->isPost()) {
            $data = input('post.');
            $category_id = input('category_id');
            $categories = explode(',', $category_id);
            $h5_id = input('h5_id');
            $h5_ids = explode(',', $h5_id);
            if($id = input('id')) {
                $this->modelUpdate(KnowledgeModel::class, $data, ['id' => $data['id']], 'save_knowledge', '修改知识点', function($condition) use ($categories, $h5_ids){
                    \app\common\model\Knowledge::saveCategoryRel($condition['id'], $categories);
                    \app\common\model\Knowledge::saveH5Rel($condition['id'], $h5_ids);
                });
            } else {
                $this->modelCreate(KnowledgeModel::class, $data, 'save_knowledge', '添加知识点',function($item) use ($categories, $h5_ids){
                    \app\common\model\Knowledge::saveCategoryRel($item->id, $categories);
                    \app\common\model\Knowledge::saveH5Rel($item->id, $h5_ids);
                });
            }
        }else {
            $data = [];
            if(input('id')) {
                $data = KnowledgeModel::find(input('id'));
                $data['category_id'] = empty($data->category) ? "" : implode(',', $data->category()->column('category_id'));
                $data['h5_id'] = empty($data->h5) ? "" : implode(',', $data->h5()->column('h5_id'));
            }
            return $this->fetchFormPageHtml(KnowledgeModel::class, __FUNCTION__, $data);
        }
    }

    function delete()
    {
        $id = input('id/d');

//        if( KnowledgeModel::where('parent_id', $id)->count() > 0 )
//            $this->error('该分类有子分类，无法删除。');
        echo KnowledgeModel::destroy($id) ? 1 : 0;
    }

    function category()
    {
        $where = [];
        $this->assign('data', [
            'title' => '知识点分类',
            'collection' => KnowledgeCategory::where($where)->order('id desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => [
                ['ID',['style'=>'width:80px']],
                '名称'],
            'fields' => [ 'id', 'name' ],
            'buttons' => [
                ['title' => '编辑', 'url' => url('saveCategory'),'permission_id' => 147],
                ['title'=>'删除', 'onclick' => 'delOne', 'url'=> url('deleteCategory'),'permission_id' => 148],
            ],
            'mbuttons' => [
                ['title' => '添加', 'url' => url('saveCategory'),'permission_id' => 147],
            ],
            'searcher' => [
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    // 编辑
    function saveCategory()
    {
        if(request()->isPost()) {
            $data = input('post.');
            if($id = input('id')) {
                $this->modelUpdate(KnowledgeCategoryModel::class, $data, ['id' => $data['id']], 'only_name', '修改知识点分类');
            } else {
                $this->modelCreate(KnowledgeCategoryModel::class, $data, 'only_name', '添加知识点分类');
            }
        }else {
            return $this->fetchFormPageHtml( KnowledgeCategoryModel::class, __FUNCTION__ );
        }
    }
    function deleteCategory()
    {
        $id = input('id/d');
        if($id < 10) return 0; // 10以内的写死了
        echo KnowledgeCategory::destroy($id) ? 1 : 0;
    }

    /*
     * 知识点讲解
     * */

    function h5List()
    {
        $where = [];
        if($kid = input('kid')){
            $ids = H5::findRelKnowledgeIds($kid);
            $where['id'] = ['in', implode(',', $ids)];
        }
        if($kw = input('kw')) {
            $where[] = ['title','like',"%{$kw}%"];
        }
        if($sort_by = input('sort_by')) {
            $order = $sort_by . " ". input('sort_type','desc');
        } else {
            $order = 'id desc';
        }
        $data = [
            'title' => '知识点讲解',
            'collection' => H5::where($where)->order($order)->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => [
                ['ID',['style'=>'width:80px']], '标题', '关联知识点',
                ['修改时间',['sort' => 'edit_time','sort_type'=>'asc']],
            ],
            'fields' => [ 'id', 'title',
                function($row) { $t = []; foreach ($row['knowledge'] as $v) $t[] = $v['title'] ; echo implode("<br>" ,$t); },
                'edit_time' ],
            'buttons' => [
                ['title' => '预览', 'onclick' => 'openBigWin', 'url' => url('h5Preview'),'permission_id' => 149],
                ['title' => '编辑', 'onclick' => 'openBigWin', 'url' => url('saveH5'),'permission_id' => 150],
                ['title'=>'删除', 'onclick' => 'delOne', 'url'=> url('deleteH5'),'permission_id' => 151],
            ],
            'mbuttons' => [
                ['title' => '添加', 'onclick' => 'openBigWin', 'url' => url('saveH5'),'permission_id' => 150],
            ],
            'searcher' => [
                ['type' => 'input', 'name' => 'kw', 'placeholder' => '关键字搜索'],
            ],
        ];
        if (input('act') == 'select') {
            $data['select_type'] = 2;
        }
        $this->assign('data', $data);

        return $this->fetch('public/table_builder');
    }
    // 编辑
    function saveH5()
    {
        if(request()->isPost()) {
            $data = input('post.');
            $knowledge_ids = input('knowledge_id', null);
            if($knowledge_ids)
                $knowledge_ids = explode(',', $knowledge_ids);
            $kid = input('kid');

            if($id = input('id')) {
                $data['edit_time'] = now();
                $this->modelUpdate(H5Model::class, $data, ['id' => $data['id']], 'save_h5', '修改知识点讲解', function($condition) use ($knowledge_ids){
                    H5Model::saveKnowledgeRel($condition['id'], $knowledge_ids);
                });
            } else {
                $data['add_time'] = now();
                $this->modelCreate(H5Model::class, $data, 'save_h5', '添加知识点讲解',function($item) use ($kid, $knowledge_ids){
                    if($kid) H5Model::saveSingleKnowledgeRel($item->id, $kid);
                    if(!empty($knowledge_ids)) H5Model::saveKnowledgeRel($item->id, $knowledge_ids);
                });
            }
        }else {
            $data = [];
            if($kid = input('kid')) {
                $data['knowledge_id'] = $kid;
            }
            if(input('id')) {
                $data = H5::find(input('id'));
                $data['knowledge_id'] = empty($data->knowledge) ? "" : implode(',', $data->knowledge()->column('id'));
            }
            return $this->fetchFormPageHtml( H5Model::class, url(__FUNCTION__,['kid'=> input('kid')]) , $data);
        }
    }
    function deleteH5()
    {
        $h5_id = input('id/d');
        Db::name('section_h5')->where(compact('h5_id'))->delete();
        Db::name('knowledge_h5')->where(compact('h5_id'))->delete();
        echo H5::destroy($h5_id) ? 1 : 0;
    }
    function loadH5Data()
    {
        $where1[] = ['id', 'in', input('ids/a')];
        $data = H5::getList($where1);
        return $this->dataJson($data);
    }
    function loadH5DataByKnowledgeIds()
    {
        $where[] = ['knowledge_id', 'in',  input('ids/a') ];
        $h5_ids = \app\common\model\Knowledge::getH5Ids($where);
        $data = [];
        if(!empty($h5_ids)) {
            $where1[] = ['id', 'in', array_unique($h5_ids)];
            $data = H5::getList($where1);
        }
        return $this->dataJson($data);
    }
    function h5Preview()
    {
        $id = input('id/d');
        if(!$id) $this->error("缺少参数");
        $data = H5::find($id);
        $this->assign('data', $data);
        return $this->fetch('test/h5_preview');
    }
}