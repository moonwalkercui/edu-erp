<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\ApiUser\MessageModel;
use App\Model\UserProfile as UserProfileModel;
use App\Model\MessageRead;
use App\Service\Api\UserService;

class MessageLogic
{
    public static function delete($id)
    {
        $self = MessageModel::find($id);
        $res = $self->delete();
        if($res) {
            $self->read()->delete();
            return true;
        } else {
            throw new ApiException('删除失败');
        }
    }
    public static function read($id)
    {
        $res = MessageRead::create([
            'message_id' => $id,
            'read_at' => now()
        ]);
        if($res == false) throw new ApiException('已读操作失败');
    }
    // 发站内信
    public static function send($request)
    {
        $data = cleanXss($request);
        $post['to_user'] = $data['user']; // 需要post
        $post['content'] = $data['content']; // 需要post
        $post['title'] = isset($data['title']) && $data['title'] ? $data['title'] : mb_substr($data['content'],0,12) ;
        $post['to_name'] = $data['user'] == 0 ? '系统' : UserProfileModel::where('username', $data['user'])->value('name');
        $post['from_user'] = UserService::getUserName();
        $post['from_name'] = UserService::getUserProfileName();
        $post['created_at'] = now();
        if ($post['to_user'] == $post['from_user'])
            throw new ApiException('不能给自己发送消息');
        return BaseLogic::save('发送消息', $post['title'] , '\App\Model\ApiUser\MessageModel' , $post );
    }
}