<?php

namespace App\Http\Middleware;

use App\Exceptions\ApiException;
use App\Model\Organization;
use App\Service\Api\UserService;
use Closure;

class CheckUserStatus
{
    public function handle($request, Closure $next)
    {
//        $user = UserService::getUserInfo();
        // 验证组织状态：判断是否已经有组织了。否则。。。 判断组织是否注销关停了
        // 接口中对 异常字符串 有判断，不要改字
//        if($user->org->status == getStatusText(-1,'organization'))  throw new ApiException('组织审核中');
//        if($user->org->status == getStatusText(0,'organization'))   throw new ApiException('组织已关闭');
        return $next($request);
    }
}
