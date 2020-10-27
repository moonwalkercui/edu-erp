<?php
namespace app\home\controller;
use think\Controller;

class Base extends Controller
{
    function successJson($msg, $data = [])
    {
        return json([
            'result' => 'success',
            'code' => 1,
            'msg' => $msg,
            'data' => $data
        ]);
    }
    function errorJson($msg, $data = [])
    {
        return json([
            'result' => 'error',
            'code' => 0,
            'msg' => $msg,
            'data' => $data
        ]);
    }
    function dataJson($data, $msg="")
    {
        return json([
            'result' => 'success',
            'code' => 1,
            'msg' => $msg,
            'data' => $data
        ]);
    }
}