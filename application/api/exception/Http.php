<?php
namespace app\api\exception;

use Exception;
use think\exception\Handle;
use think\exception\HttpException;
use think\exception\ValidateException;
use think\facade\Log;

class Http extends Handle
{
    public function render(Exception $e)
    {
        Log::error(var_export([
            'code' => $e->getCode(),
            'msg' => $e->getMessage(),
            'line' => $e->getLine(),
            'file' => $e->getFile(),
        ],true));
        return json([
            'result' => 'error',
            'code' => $e->getCode(),
            'msg' => $e->getMessage(),
            'line' => is_debug() ? $e->getLine() : '',
            'file' => is_debug() ? $e->getFile() : '',
        ]);

//        // 参数验证错误
//        if ($e instanceof ApiException) {
//        }

//        // 请求异常
//        if ($e instanceof HttpException && request()->isAjax()) {
//            return response($e->getMessage(), $e->getStatusCode());
//        }
//        return parent::render($e);
    }
}