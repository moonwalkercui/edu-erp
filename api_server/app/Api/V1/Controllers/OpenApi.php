<?php
namespace App\Api\V1\Controllers;
//use App\Model\City;
use App\Model\Region;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

// 开放数据api，无需登录的数据
class OpenApi extends Controller
{
    public function getStatus(Request $request)
    {
        $config_name = $request->input('name');
        $config = config('system.status.'.$config_name);
        $list = [];
        if(!empty($config)) 
            foreach($config as $k => $v) {
                $list[] = [
                    'k' => $k,
                    'v' => $v
                ];
            }
        return response()->json(['list' => $list ]);
    }
//    public function getCities()
//    {
//        $list = City::getTree();
//        return response()->json([ 'list' => $list ]);
//    }
    public function regions()
    {
        $list = Region::getTree()[0]['_child'];
        return response()->json([ 'list' => $list ]);
    }
    public function captcha($code)
    {
        $image = new \App\Service\Api\Captcha(80, 32);
        $content = $image->outImg();
        $image->cacheCode($code, $image->checkcode);
//        \Illuminate\Support\Facades\Log::notice('验证码'.$code.'-'.$image->checkcode);
        return response($content, 200, [
            'Content-Type' => 'image/png',
        ]);
    }

//    public function checkOrgcodeExist(Request $request)
//    {
//        $connections = config('database.connections');
//        unset($connections['mysql']);
//        unset($connections['sqlite']);
//        unset($connections['pgsql']);
//        unset($connections['sqlsrv']);
//
//        $key = cleanXss($request->prefix);
//
//        if (!$key || !isset($connections[$key])) {
//            return response()->json([ 'result' => 'error','msg' => '操作出错']);
//        }
//        return response()->json([ 'result' => 'success']);
//    }
    // 验证码

}