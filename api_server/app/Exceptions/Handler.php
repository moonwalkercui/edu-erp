<?php

namespace App\Exceptions;

use Exception;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Illuminate\Foundation\Exceptions\Handler as ExceptionHandler;
use Illuminate\Support\Facades\Log;
use Illuminate\Validation\ValidationException;
use Symfony\Component\HttpKernel\Exception\UnauthorizedHttpException;
class Handler extends ExceptionHandler
{
    /**
     * A list of the exception types that are not reported.
     *
     * @var array
     */
    protected $dontReport = [
        //
    ];

    /**
     * A list of the inputs that are never flashed for validation exceptions.
     *
     * @var array
     */
    protected $dontFlash = [
        'password',
        'password_confirmation',
    ];

    /**
     * Report or log an exception.
     *
     * This is a great spot to send exceptions to Sentry, Bugsnag, etc.
     *
     * @param  \Exception  $exception
     * @return void
     */
    public function report(Exception $e)
    {
//        parent::report($exception);
        if (!config('app.debug')) {
            Log::error('['.$e->getCode().'] "'.$e->getMessage().'"');
//            Log::error('['.$e->getCode().'] "'.$e->getMessage().'" on line '.$e->getTrace()[0]['line'].' of file '.$e->getTrace()[0]['file']);
        } else {
            parent::report($e);
        }
    }

    /**
     * Render an exception into an HTTP response.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Exception  $exception
     * @return \Illuminate\Http\Response
     */
    public function render($request, Exception $exception)
    {
        // jwt异常 参数验证错误的异常，我们需要返回 400 的 http code 和一句错误信息
        if ($exception instanceof ModelNotFoundException) {
            return response(['status'=>'error','msg' => '未查询到数据模型'], 499);
        }
        if ($exception instanceof ValidationException) {
            return response(['status'=>'error','msg' => array_first(array_collapse($exception->errors()))],400);
        }
        // jwt异常 用户认证的异常，我们需要返回 401 的 http code 和错误信息
        else if ($exception instanceof UnauthorizedHttpException) {
            return response(['status'=>'error','msg' => $exception->getMessage()],401);
        }
        return parent::render($request, $exception);
    }
}
