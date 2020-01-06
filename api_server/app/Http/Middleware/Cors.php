<?php

namespace App\Http\Middleware;
use Closure;
use Illuminate\Support\Facades\Response;

/*
 * 跨域请求中间件
 * */
class Cors
{
    public function handle($request, Closure $next)
    {
        header('Access-Control-Allow-Origin: *');
//        header('Access-Control-Allow-Headers:Origin, Content-Type, Accept, multipart/form-data, application/json');//允许访问的方式
//        header('Access-Control-Allow-Origin:*');//允许所有来源访问
//        header('Access-Control-Allow-Method:GET, POST, PATCH, PUT, OPTIONS');//允许访问的方式

        $headers = [
            'Access-Control-Allow-Methods'=> 'POST, GET, OPTIONS, PUT, DELETE',
            'Access-Control-Allow-Headers'=> 'Content-Type, X-Auth-Token, Origin, multipart/form-data, application/json', // Accept
        ];

        if($request->getMethod() == "OPTIONS") {
            return Response::make('OK', 200, $headers);
        }

        $response = $next($request);
        foreach($headers as $key => $value){
            $response->header($key, $value);
        }
        return $response;

    }
}
