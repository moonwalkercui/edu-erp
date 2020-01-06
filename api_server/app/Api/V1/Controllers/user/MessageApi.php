<?php
namespace App\Api\V1\Controllers\user;

use App\Logic\ApiUser\MessageLogic;
use App\Model\ApiUser\MessageModel;
use App\Service\Api\UserService;
use Illuminate\Http\Request;
use App\Api\V1\Controllers\Base;

class MessageApi extends Base
{
    public function getMine(Request $request)
    {
        $res = MessageModel::getMine($request->input('per_page', config('system.number_paginate')));
        return $this->fetch($res);
    }
    public function read(Request $request)
    {
        $this->validateParam($request->all() , ['id' => 'required' ]);
        $data = MessageModel::withCount('read')->where('id', $request->id)->first();
        if ($data->read_count === 0 && $data->from_user != UserService::getUserName())
            MessageLogic::read($request->id);
        return $this->fetch($data);
    }
    public function send(Request $request)
    {
        $this->validateParam($request->all() , [
            'user' => 'required',
//            'title' => 'required', // 标题自动截取
            'content' => 'required',
        ],[
            'user.requeired' => '未选择接收者',
//            'title.requeired' => '未设置标题',
            'content.requeired' => '未设置内容',
        ]);
        MessageLogic::send($request->all());
        return $this->success('已发送');
    }
//    public function read(Request $request)
//    {
//        $this->validateParam($request->all() , [ 'id' => 'required' ]);
//        MessageLogic::read($request->id);
//        return $this->success('已读');
//    }
    public function delete(Request $request)
    {
        $this->validateParam($request->all() , [ 'id' => 'required' ]);
        MessageLogic::delete($request->id);
        return $this->success('已删除');
    }
}