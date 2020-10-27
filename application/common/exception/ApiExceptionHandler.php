<?php
namespace app\common\exception;
use Exception;
use think\exception\Handle;
use think\facade\Log;

class ApiExceptionHandler extends Handle
{
    const NEED_LOGIN = 2;
    const TOKEN_EXPIRED = 3;

    public function render(Exception $e)
    {
        Log::error(var_export([
            'code' => $e->getCode(),
            'msg' => $e->getMessage(),
            'file' => $e->getFile(),
            'line' => $e->getLine(),
        ], true));

        if(self::isReturnJson()) {
            return json([
                'result' => 'error',
                'code' => $e->getCode(),
                'msg' => $e->getMessage(),
                'data' => ''
            ]);
        }
        return parent::render($e);
    }

    static function isReturnJson()
    {
        return request()->module() == 'api' || request()->isAjax();
    }
}