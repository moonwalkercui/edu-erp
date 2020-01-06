<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Model\Notification;
use App\Service\Api\UserService;

class NormalLogic
{
    public static function advertisementSave($request , $condition = [])
    {
        $data['title'] = cleanXss($request->input('title'));
        $data['position'] = cleanXss($request->input('position',null));
        $data['image'] = cleanImagePrefix($request->input('image'));
        $title = '更新广告';
        if(empty($condition)){
            $title = '添加广告';
        }
        return BaseLogic::save('广告管理',$title , '\App\Model\Advertisement' , $data , $condition);
    }
    public static function advertisementDel($id)
    {
        return BaseLogic::delete('广告管理','删除广告' ,'\App\Model\Advertisement' ,['id' => $id ]);
    }
    public static function notificationSave($request , $condition = [])
    {
        $data['title'] = cleanXss($request->input('title'));
        $data['content'] = cleanXss($request->input('content',null));
        $data['image'] = cleanImagePrefix($request->input('image'));
        $data['is_top'] = $request->filled('is_top') ? getStatusValue($request->input('is_top'),'is_yes') : 0;
        if($request->filled('is_popup')) {
            if ($request->is_popup == '是') {
                if(!$data['image']) throw new ApiException('缺少图片');
                Notification::where('is_popup',1)->update(['is_popup' => 0]);
                $data['is_popup'] = 1;
            }
        }

        $title = '更新消息';
        if(empty($condition)){
            $title = '添加消息';
        }
        return BaseLogic::save('消息管理',$title , '\App\Model\Notification' , $data , $condition);
    }
    public static function notificationDel($id)
    {
        return BaseLogic::delete('消息管理','删除消息' ,'\App\Model\Notification' ,['id' => $id ]);
    }
    public static function zoneSave($request , $condition = [])
    {
        $data['content'] = cleanXss($request->input('content',null));
        $images = $request->input('images' , []);
        $data['images'] = '';
        if(!empty($images)){
            foreach ($images as $img) {
                $data['images'] .= isset($img['url']) ? cleanImagePrefix($img['url']).',' : '';
            }
        }
        $user = UserService::getUser();
        $data['uid'] = $user->id;
        $data['avatar'] = $user->profile->avatar;
        $data['edit_time'] = now();

        $title = '更新消息';
        if(empty($condition)){
            $title = '添加消息';
        }
        return BaseLogic::save('圈子管理',$title , '\App\Model\Zone' , $data , $condition);
    }
    public static function zoneDel($id)
    {
        return BaseLogic::delete('圈子管理','删除消息' ,'\App\Model\Zone' ,['id' => $id ]);
    }
}
