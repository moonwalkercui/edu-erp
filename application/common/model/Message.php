<?php

namespace app\common\model;

use think\facade\Request;

class Message extends BaseModel
{
    protected $table = 'message';
    const ATTACH_TYPE_USER = 'user';
    const ATTACH_TYPE_CASE = 'case';

    // @type 0 系统消息 1 个人消息
    static function send($to_user_id, $title, $content, $from_user_id=null, $attach_type = null, $attach_id = null, $type = 1)
    {
        $data = compact('to_user_id', 'title', 'content', 'from_user_id', 'attach_type', 'attach_id', 'type');
        $data['send_time'] = now();
        if($from_user_id) {
            if (self::where(compact('from_user_id'))->where('send_time', '>', date("Y-m-d H:i:s", time() - 300))->count() > 0) {
                exception("发送太频繁，请稍后再试");
            }
        }
        return self::insert($data);
    }

    function getAttachAttr($value, $data)
    {
        $attach = null;
        if ($data['attach_type'])
            switch ($data['attach_type']) {
                case 'user':
                    $attach = User::get($data['attach_id']);
                    break;
                case 'case':
                    $attach = Cases::get($data['attach_id']);
                    break;
                default:
            }
        return $attach;
    }

    // 获取未读数
    static function getUnreadCount($user)
    {
        return $user['msg_read_time'] ? Message::where('send_time', '>', $user['msg_read_time'])->where('to_user_id', $user['id'])->count() : 1;
    }
}