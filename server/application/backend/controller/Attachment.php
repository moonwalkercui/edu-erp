<?php
namespace app\backend\controller;
use app\backend\model\AttachmentCateModel;
use service\UploadService;
use think\Request;
use app\common\model\Attachment as AttachmentModel;
use app\common\model\AttachmentCate;
use service\JsonService as Json;
use service\UtilService as Util;

/**
 * 文件校验控制器
 * Class SystemFile
 * @package app\admin\controller\system
 *
 */
class Attachment extends Base
{
    /**
     * 附件列表
     * @return \think\response\Json
     */
    public function index()
    {
        $pid = input('pid', 0);
        $cates = AttachmentCate::getAll();
        $this->assign('typearray',$cates);
        $this->assign('pid', $pid);
        $this->assign('list', AttachmentModel::getAll($pid));
        $this->assign('manager', 0);
        return $this->fetch();
    }
    public function manage()
    {
        $pid = input('pid', 0);
        $cates = AttachmentCate::getAll();
        array_push($cates, [
            'id' => -1,
            'pid' => 0,
            'name' => '隐藏资料',
            '_child' => [],
        ]);
        $this->assign('typearray', $cates);
        $this->assign('pid', $pid);
        $this->assign('list', AttachmentModel::getAll($pid));
        $this->assign('manager', 1);
        return $this->fetch('index');
    }
    public function handleCreate($input = null)
    {
        $input = input('');
        $input['name'] = input('value');
        parent::handleCreate($input);
    }

    public function handleSave($input = null)
    {
        if(input('id/d') == 0) return;
        $input = input('');
        $input['name'] = input('value');
        parent::handleSave($input);
    }

    public function handleDel()
    {
        if(AttachmentCate::where('pid',input('id'))->count())
            return $this->error('有子栏目不能删除');
        if(AttachmentModel::where('pid',input('id'))->count())
            return $this->error('栏目内有图片不能删除');
        return parent::handleDel();
    }
    /**
     * 图片管理上传图片
     * @return \think\response\Json
     */
    public function upload()
    {
        $pid = input('pid') ? input('pid') : 0;
        $res = UploadService::uploadImage('file', 'up/attach');
        if($res['code'] == 1) {
            if( AttachmentModel::attachmentAdd(
                $res['name'],
                $res['size'],
                $res['ext'],
                $res['path'],
                '', // 压缩后的地址
                $pid
            )) {
                $this->success('已上传');
            } else
                $this->error('上传失败');
        } else {
            $this->error($res['msg']);
        }
    }
    public function uploadFile()
    {
        $pid = input('pid') ? input('pid') : 0;

        $res = UploadService::uploadFile('file', 'up/attach');
        if($res['code'] == 1) {
            if( AttachmentModel::attachmentAdd(
                $res['name'],
                $res['size'],
                $res['ext'],
                $res['path'],
                '', // 压缩后的地址
                $pid,
                $res['f_name']
            )) {
                $this->success('已上传', null, [
                    'path' => $res['path'],
                    'ext' => get_file_icon($res['ext']),
                    'is_img' => in_array($res['ext'], ['jpg','jpeg','png','gif']) ? 1 : 0
                ]);
            } else
                $this->error('上传失败');
        } else {
            $this->error($res['msg']);
        }
    }
    /**
     * ajax 提交删除
     */
    public function deleteImg()
    {
        $ids = input('ids');
        if(empty($ids))
            $this->error('还没选择要删除的图片呢？');
        foreach ($ids as $v){
            self::deleteimganddata($v);
        }
        $this->success('已删除');
    }
    /**删除图片和数据记录
     * @param $att_id
     */
    private function deleteimganddata($att_id){
        AttachmentModel::deleteItem(['att_id' => intval($att_id)]);
    }
    /**
     * 移动图片分类显示
     */
    public function moveImg()
    {
        if(request()->isPost()) {
            $image_ids = input('ids');
            return $this->modelUpdate('app\backend\model\AttachmentModel',
                ['pid' => input('cate/d')],
                [['att_id','in', $image_ids]]
            );
        }else {
            $cates = AttachmentCateModel::column('name','id');
            $cates[-1] = '隐藏资料';
            $this->assign('data',  [
                [
                    'type' => 'select',
                    'name' => 'cate',
                    'label' => '请选择分类',
                    'options' => $cates
                ],[
                    'type' => 'hidden',
                    'name' => 'ids',
                    'label' => '',
                    'value' => input('ids')
                ]
            ]);
            $this->assign('action', __FUNCTION__);
            return $this->fetch('public/form_builder');
        }
//
//        $formbuider = [];
//        $formbuider[] = Form::hidden('imgaes',$imgaes);
//        $formbuider[] = Form::select('pid','选择分类')->setOptions(function (){
//            $list = AttachmentCate::getCateList();
//            $options =  [['value'=>0,'label'=>'所有分类']];
//            foreach ($list as $id=>$cateName){
//                $options[] = ['label'=>$cateName['html'].$cateName['name'],'value'=>$cateName['id']];
//            }
//            return $options;
//        })->filterable(1);
//        $form = Form::make_post_form('编辑分类',$formbuider,Url::build('moveImgCecate'));
//        $this->assign(compact('form'));
//        return $this->fetch('public/form-builder');
    }

    /**移动图片分类操作
     * @param Request $request
     * @param $id
     */
    public function moveImgCecate(Request $request)
    {
        $data = Util::postMore([
            'pid',
            'imgaes'
        ],$request);
        if($data['imgaes'] == '') return Json::fail('请选择图片');
        if(!$data['pid']) return Json::fail('请选择分类');
        $res = AttachmentModel::where('att_id','in',$data['imgaes'])->update(['pid'=>$data['pid']]);
        if($res)
            Json::successful('移动成功');
        else
            Json::fail('移动失败！');
    }
}