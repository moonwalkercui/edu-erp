<?php
namespace App\Logic\ApiUser;

use App\Exceptions\ApiException;
use App\Service\Api\LogService;
use Illuminate\Support\Facades\DB;

class BaseLogic
{
    // 检查状态的值是否在配置中
    public static function checkStatus($status_val,$status_name)
    {
        if(!in_array($status_val,array_keys(config('system.status.'.$status_name))))
            throw new ApiException('请求参数中状态错误');
    }
    /*
     * 创建和更新方法
     * @param $log_remark 日志标题
     * @param $model_name 含有命名空间的模型名字
     * @param $data 创建和更新的拼接数据
     * @param $condition 更新条件 []表示创建 否则表示新增
     * @param $callback 可空，成功后的回调
     * */
    public static function save($log_type, $log_remark , $model_name , $data , $condition = [] , $callback = null, $pk = 'id' )
    {
        DB::beginTransaction();
        try {
            if(empty($condition)){
                // 新增
                if ($model = $model_name::create($data)){

                    if(is_callable($callback))
                        call_user_func($callback,$model->$pk);

                    LogService::userLog($log_type,$log_remark);
                } else throw new \Exception('创建失败');
            }else{
                // 更新
                $res = $model_name::where($condition)->update($data);
                if ( $res === false ){
                    throw new \Exception('修改失败');
                } elseif ($res === 0 && is_callable($callback) == false ){
                    throw new \Exception('无更新');
                } else {
                    if(is_callable($callback))
                        call_user_func($callback,$condition[$pk]);
                    LogService::userLog($log_type,$log_remark);
                }
            }
            DB::commit();
            return true;
        } catch (\Exception $e) {
            DB::rollBack();
            throw new ApiException($e->getMessage());
        }
    }
//    /*
//    * 批量更新方法
//    * @param $log_remark 日志标题
//    * @param $model_name 含有命名空间的模型名字
//    * @param $data 创建和更新的拼接数据
//    * @param $condition 更新条件 []表示创建 否则表示新增
//    * @param $callback 可空，成功后的回调
//    * */
//    public static function saveAll( $log_remark , $model_name , $datas , $condition = [] , $callback = null )
//    {
//        if(empty($condition)){
//            // 新增
//            if ( $insert = $model_name::insert($datas) ){
//
//                if(is_callable($callback))
//                    call_user_func($callback,$datas);
//
//                LogService::userLog($log_remark);
//                return true;
//            } else
//                throw new ApiException('批量添加数据失败');
//
//        }else{
//            // 更新
//            $res = $model_name::where($condition)->update($datas);
//
//            if ( $res === false ){
//                throw new ApiException('更新失败');
//            } elseif ($res === 0){
//                throw new ApiException('无更新');
//            } else {
//
//                if(is_callable($callback))
//                    call_user_func($callback,$datas);
//
//                LogService::userLog($log_remark);
//                return true;
//            }
//        }
//    }
    /*
     * 删除方法
     * @param $log_type 类型
     * @param $log_remark 日志标题
     * @param $model_name 含有命名空间的模型名字
     * @param $ids 要删除的id值 可以是单个数字或数组
     * @param $callback 可空，成功后的回调
     * */
    public static function delete($log_type, $log_remark, $model_name , $condition , $callback = null, $pk = 'id')
    {
        $model = $model_name::where($condition)->first();
        if( $model->delete() ){
            if(is_callable($callback))
                call_user_func($callback,$model->$pk);

            LogService::userLog($log_type , $log_remark);
            return true;
        }
        else throw new ApiException('删除失败');
    }
}
