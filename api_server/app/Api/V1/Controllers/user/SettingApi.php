<?php
namespace App\Api\V1\Controllers\user;

use App\Model\Resources\UserLogResourse;
use App\Model\Setting;
use App\Model\UserLog;
use App\Model\WxMsgTemplate;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class SettingApi extends Base
{
    // 高级设置
    public function saveSetting(Request $request)
    {
        $data = $request->all();
        $data = cleanXss($data);
        if($request->filled('org_logo')) $data['org_logo'] = strip_tags(cleanImagePrefix($request->org_logo));
        if($request->filled('org_img')) $data['org_img'] = strip_tags(cleanImagePrefix($request->org_img));

        unset($data['token']);
        if(Setting::saveSetting($data))
            return $this->success('已保存');
    }
    public function getSetting()
    {
        $list = Setting::getSetting();
        return $this->fetch(compact('list'));
    }
    // 获取操作日志
    public function getLogs(Request $request)
    {
        $where = [];
        if($request->filled('search_user'))
            array_push($where, ['username' , '=' , $request->search_user]);
        if($request->filled('search_type'))
            array_push($where, ['type' , '=' , $request->search_type]);

        $model = UserLog::where($where)->orderBy('id','desc');
        $data = $model->paginate($request->input('per_page', config('system.number_paginate')));
        return $this->fetchResource(UserLogResourse::collection($data));
    }
    public function getLogType()
    {
        $types = array_unique(UserLog::groupBy('type')->pluck('type')->toArray());
        $res = [];
        foreach ($types as $t) {
            $res[] = [
                'id' => $t,
                'name' => $t,
            ];
        }
        return $this->fetch($res);
    }
    public function wxMsgTemplate()
    {
        $where = [];
        $data = WxMsgTemplate::where($where)->get();
        return $this->fetch($data);
    }
    public function saveWxMsgTemplate(Request $request)
    {
        $this->validateParam($input = $request->all() , [
            'id' => 'required',
//            'temp_name' => 'required',
            'temp_sn' => 'required',
//            'temp_id' => 'required',
            'temp_content' => 'required',
        ]);

        $res = WxMsgTemplate::where('id', intval($input['id']))->update([
//            'temp_name' => $input['temp_name'],
            'temp_sn' => $input['temp_sn'],
//            'temp_id' => $input['temp_id'],
            'temp_content' => $input['temp_content'],
            'status' => getStatusValue($request->input('status', '正常'), 'switch')
        ]);

        if($res !== false) return $this->success('已保存');
        else return $this->error('保存失败');

//    ["id"]=> string(1) "1"
//    ["temp_name"]=> string(18) "支付成功通知"
//    ["temp_sn"]=> string(4) "sdfd"
//    ["temp_id"]=> string(43) "xK0qyWXf-Zxm-ng9BTHAbDinybIRrohI17x9rGMeXaI"
//    ["temp_content"]=> string(188) ""
//    ["status"]=> string(1) "1"

    }
}