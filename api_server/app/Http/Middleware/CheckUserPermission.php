<?php

namespace App\Http\Middleware;

use App\Exceptions\ApiException;
use App\Model\Permission;
use App\Service\Api\UserService;
use Closure;

class CheckUserPermission
{
    public function handle($request, Closure $next)
    {
        $user = UserService::getUserInfo();
        // 判断是否是管理员
        // 如果是组织管理员，就不用验证权限了。
        if(UserService::isManager() == false){
            // 检查权限列表
            $permissions = Permission::getList();
            // 获取当前uri
            $uri = strtolower($request->route()->uri());
            $pm = $permissions->where('uri',$uri)->first();
//            if($pm == null) throw new ApiException('未知权限');
            if($pm != null && $pm->need_check == 1) {
                if(false == $user->hasPermission($uri))
                    throw new ApiException('权限不足');
            }
        }

        return $next($request);
    }
}
