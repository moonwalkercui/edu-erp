<?php

namespace app\backend\controller;

use app\backend\model\Config;
use think\facade\Request;

/**
 * @author Ray 541720500@qq.com
 */
class System extends Base
{
    public function config(Request $request)
    {
        return $this->settingPage( __FUNCTION__,
            ['system','attendance'],
            function ($k, &$v) {
                if($k == 'analytics') {
                    $v = $_POST['analytics'];
                }
                if (in_array($k, ['site_logo']) && $v) {
                    $v = $v[0];
                }
            }
        );
    }

    public function upload()
    {
        return $this->uploadImage();
    }

    function Upgrade()
    {
        $cur_version = '1.5'; // 传入当前版本
//        dump(Upgrade::checkNewVer($cur_version)); // 检查是否有新版本
        // Upgrade::run($cur_version); // 运行升级程序
    }
    public function mapPicker()
    {
        if(request()->isPost()) {
            $data = input('post.');
            if($data['lat'] == '' || $data['lng'] == "") {
                $this->error("未拾取坐标");
            }
            Config::where('key','lat')->update(['value'=> $data['lat']])
            && Config::where('key','lng')->update(['value'=> $data['lng']]) ?
                $this->success('考勤坐标已更新') : $this->error('坐标更新失败');
        } else{
            $this->assign("lat", \app\common\model\Config::getValue('lat'));
            $this->assign("lng", \app\common\model\Config::getValue('lng'));
            return $this->fetch('public/mappicker');
        }
    }
}