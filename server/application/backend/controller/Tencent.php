<?php

namespace app\backend\controller;

use app\backend\model\WxMenuModel;
use app\common\model\Vod;
use app\common\model\WxMenu;
use app\common\service\TencentCloud;
use app\common\service\WxService;
use think\Exception;

// 腾讯云控制器
class Tencent extends Base
{
    // 上传
    function vodUpload()
    {
        if(request()->isPost()) {
            $data = input('');
            $insert['title'] = $data['title'];
            $insert['cover_url'] = $data['videoUrl'];
            $insert['video_url'] = $data['coverUrl'];
            $insert['file_id'] = $data['fileId'];
            $insert['upload_time'] = now();
            $insert['editor'] = session('login_id');
            $this->modelCreate(Vod::class, $insert, 'vod_saving', '添加点播视频');
        } else {
            $this->assign('sign', TencentCloud::signature());
            return $this->fetch();
        }
    }
    // 列表
    function vodList()
    {
        if(request()->isPost()) {
            try{
                echo TencentCloud::vodList(input('page', 1));
            } catch (Exception $e) {
                return $this->errorJson($e->getMessage());
            }
        } else {
            $this->assign('type', input('type'));
            return $this->fetch();
        }
    }
    function vodDel()
    {
        $file_id = input('file_id');
        try{
            TencentCloud::vodDelMedia($file_id);
        } catch (Exception $e) {
            return $this->errorJson($e->getMessage());
        }
        return $this->successJson('已删除');
    }
}