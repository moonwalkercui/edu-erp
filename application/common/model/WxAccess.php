<?php

namespace app\common\model;

class WxAccess extends BaseModel
{
    protected $table = 'wx_access';

    static function getAccessItem($openid, $unionid = null, $user_info = [])
    {
        $exist = self::where(compact('openid'))->find();
        // 如果记录不存在 那么新建一条记录 并新建一个会员 然后绑定openid
        if(!$exist) {
            $data = [
                'openid' => $openid,
                'unionid' => $unionid,
                'add_time' => now(),
                'staff_id' => null,
                'student_id' => null,
            ];
            if(!empty($user_info)) {
                $data['nickname'] = $user_info['nickname'];
                $data['gender'] = $user_info['sex'];
                $data['city'] = $user_info['city'];
                $data['province'] = $user_info['province'];
                $data['country'] = $user_info['country'];
                $data['headimg'] = $user_info['headimgurl'];
            }
            $access = self::create($data);
        } else {
            $access = $exist;
        }
        return $access;
    }
    // 处理取消关注
    static function handleUnsubscribe($openid)
    {
        self::where(compact('openid'))->update([
            'unsubscribe' => 1,
            'unsub_time' => now(),
        ]);
    }
    // 绑定学院
    static function bindStudent($id, $student_id, $type = 1)
    {
        return self::where(compact('id'))->update(compact('student_id','type')) !== false;
    }
}