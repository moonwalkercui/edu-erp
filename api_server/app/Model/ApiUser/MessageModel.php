<?php

namespace App\Model\ApiUser;

use App\Exceptions\ApiException;
use App\Model\Message;
use App\Service\Api\UserService;

class MessageModel extends Message
{
//    use OrgScopeTrait;

    public static function getMine($paginate = true)
    {
        $model = self::withCount('read')
            ->where('to_user', UserService::getUserName())
            ->orWhere('from_user', UserService::getUserName())
            ->orWhere('to_user', 0)
            ->orderBy('created_at','desc');
        return $paginate ? $model->paginate($paginate) : $model->get();
    }
    public static function getMyUnreadNumber()
    {
        return self::getMine(false)->where('read_count','=',0)->count();
    }

}