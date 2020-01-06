<?php namespace App\Service;

use App\Exceptions\ApiException;
use Illuminate\Support\Facades\Validator;
use Dingo\Api\Routing\Helpers;

trait ApiTrait
{
    use Helpers;
    /*
     * 请求参数验证方法
     * */
    public function validateParam($data, $rule , $msg = [])
    {
        $validator = Validator::make($data, $rule , $msg);
        if ($validator->fails()) {
            throw new ApiException($validator->errors()->first());
        }
    }
    public function fetchResource($collection, $other = null)
    {
        $status = 'success';
        return $collection->additional(compact('status','other'));
    }
    public function fetch($data) // , $msg = ''
    {
//        if(empty($data)) $msg = 'empty';
//        return $this->success($msg , $data);
        return $this->response->array(['status'=>'success','data' => $data ]);
    }
    public function success($msg = '', $data='')
    {
        return $this->response->array(['status'=>'success','data' => $data,'msg'=> $msg]);
    }
    public function error($msg )
    {
        return $this->response->array(['status'=>'error','msg'=> $msg]);
    }
    /*
     * 防止重复请求的验证方法，在需要的控制器里，加上这个方法即可
     * flag为前端发送的token验证标记
     * 前端获取_token的接口：api/normal/mreqtoken
     * */
    public function validateMultiReqToken($request)
    {
        $this->validateParam($request->all() , [
            '_token' => 'required'
        ],[
            '_token.required'=>'缺少TOKEN'
        ]);
        MultiRequestToken::check($request->_token);
    }
}
