<?php

namespace app\backend\controller;

use app\backend\model\WxMenuModel;
use app\common\model\WxMenu;
use app\common\service\WxService;
use think\Exception;

class Wx extends Base
{
    // 菜单管理
    function menu()
    {
        $all = WxMenu::getAll();
        $this->assign('tree', WxMenu::getTree($all));
        $this->assign('list', WxMenu::getPlainTree($all));
        return $this->fetch();
    }

    public function saveMenu()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if ($id = input('id')) {
                $this->modelUpdate(WxMenuModel::class, $data, ['id' => $data['id']], 'save_wx_menu', '修改公众号菜单');
            } else {
                $data['sort_num'] = WxMenuModel::getNewSort($data['parent_id']);
                $this->modelCreate(WxMenuModel::class, $data, 'save_wx_menu', '添加公众号菜单');
            }
        } else {
            return $this->fetchFormPageHtml(WxMenuModel::class, __FUNCTION__,[],'makeFromData','id');
        }
    }
    function publishMenu()
    {
        $menu = WxMenu::makeMenuArr();
        $wx = WxService::menu();
        try{
            $res = $wx->create($menu);
        } catch (\Exception $e) {
            $this->error($e->getMessage());
        }
        if($res['errcode'] == 0) {
            $this->success('设置成功,请登录公众号查看');
        } else {
            $this->error($res['errmsg']);
        }
    }
    function loadMedia()
    {
        $page = input('page',1);
        $per_page = 20;
        $res = WxService::loadMedia(($page - 1) * $per_page, $per_page);
        $item_count = $res['item_count'];

        $total_page = intval($item_count / $per_page);

        $pre_page = $page == 1 ? 0 : $page - 1;
        $next_page = $total_page > $page ? $page + 1 : 0;

        $this->assign('data', $res);
        $this->assign('pre_page',  $pre_page);
        $this->assign('next_page', $next_page);
        return $this->fetch();
    }
    function delMenu()
    {
        echo WxMenuModel::destroy(input('id/d')) ? 1 : 0;
    }
    function menuSort()
    {
        echo WxMenuModel::handleSort(input('ids/a')) ? 1 : 0;
    }
    // 片段内容
//    public function setting()
//    {
//        return $this->settingPage( __FUNCTION__, [
//            'about' => '企业信息',
//            'ui_text' => '界面文字',
//        ]);
//    }
}