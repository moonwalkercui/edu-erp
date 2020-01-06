<?php

namespace App\Http\Controllers\Common;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

class Resources extends Controller
{
//    获取上传的文件
    public function getUploadFile(Request $request)
    {
        $path = $request->input('p');
        return response()->file(storage_path('app/public') . '/' .$path);
    }
//    下载文件
    public function downLoadFile(Request $request)
    {
//        return response()->download();
    }
}
