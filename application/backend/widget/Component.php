<?php

namespace app\backend\widget;

use app\backend\model\AuthNode;
use think\Controller;
use think\facade\Url;

class Component extends Controller
{

    function menu($pid = null)
    {
        $this->assign('menus', AuthNode::getNodeMenuTree($pid));
        return $this->fetch('widget/menu');
    }
    function form()
    {
        return $this->fetch('widget/form');
    }

    function table()
    {
        $this->assign('page_url',  request()->baseUrl());
        return $this->fetch('widget/table');
    }

    function details()
    {
        return $this->fetch('widget/details');
    }

    function searcher()
    {
        return $this->fetch('widget/searcher');
    }

    function title()
    {
        return $this->fetch('widget/title');
    }

    function tab()
    {
        return $this->fetch('widget/tab');
    }

    function info()
    {
        return $this->fetch('widget/info');
    }

    // 选择video的表格
    function videoPicker($action)
    {
        $where = [ ['is_del','=',0], ['status','=',1] , ['transcoding_status','=',1] ];
        if (input('title')) array_push($where, ['title', 'like', '%' . input('title') . '%']);
        $this->assign('list', CourseVideo::where($where)->order('id desc')->paginate(10, false, ['query' => $this->request->param()]));
        $this->assign('action', $action);
        return $this->fetch('widget/video_picker');
    }
}