<?php
namespace App\Service\Api;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class UploaderService
{
    // 参数中filename是区别上传类型的，同时这个文件的文件名也是以filename值为key的
    public static function uploadImage(Request $request,$member_str)
    {
        $config = config('system.upload');
        $file_name = 'file';
        $upload_path = $config['imagePath'] . $member_str . '/' . date('Ymd',time());

        if ($request->hasFile('file') == false) {
            return ["status" => "error", "msg" => "上传文件丢失"];
        }else{
            $validator = Validator::make($request->all(), [
                $file_name => $config['imageValidate']
            ]);
            $errors = $validator->errors();
            if(!empty($err = $errors->get($file_name))){
                return [
                    "status" => "error", "msg" => $err[0]
                ];
            }else{
                $file = request()->file($file_name);
                $res_upload = $file->store($upload_path,'public');
                if($file->isValid() == false)
                    return [
                        "status" => "error", "msg" => "无效文件"
                    ];
                elseif($res_upload == false){
                    return [
                        "status" => "error", "msg" => "上传失败"
                    ];
                }else{
                    return [
                        "status" => "success",
                        "msg" => "已上传",
                        "title" => $file->getClientOriginalName(),
                        "type" => $file->guessExtension(),
                        "size" => $file->getSize(),
                        "url" => getResourceUrl() . $res_upload
                    ];
                }
            }
        }
    }

}
