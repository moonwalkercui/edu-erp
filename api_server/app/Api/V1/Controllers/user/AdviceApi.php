<?php
namespace App\Api\V1\Controllers\user;

use App\Api\V1\Controllers\Base;
use App\Model\Advice;
use App\Service\Api\UserService;
use Illuminate\Http\Request;

class AdviceApi extends Base
{
    public function create(Request $request)
    {
        $this->validateParam($request->all() , [
            'msg' => 'required',
        ],[
            'msg.requeired' => '未设置内容',
        ]);
        $res = Advice::create([
            'user' => UserService::getUserName(),
            'content' => cleanXss($request->msg),
            'created_at' => now()
        ]);
        return $res ? $this->success('感谢反馈') : $this->error('提交失败');
    }
}