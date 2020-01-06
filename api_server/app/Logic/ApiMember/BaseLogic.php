<?php
namespace App\Logic\ApiMember;

use App\Exceptions\ApiException;
use App\Service\Api\LogService;

class BaseLogic
{
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

        if(empty($condition)){
            // 新增
            if ($model = $model_name::create($data)){

                if(is_callable($callback))
                    call_user_func($callback,$model->$pk);

                LogService::memberLog($log_type,$log_remark);
                return true;
            }
            else
                throw new ApiException('创建失败');

        }else{
            // 更新
//            var_dump($data);
//            var_dump($model_name);die;
            $res = $model_name::where($condition)->update($data);
            if ( $res === false ){
                throw new ApiException('修改失败');
            } elseif ($res === 0 && is_callable($callback) == false ){
                throw new ApiException('无更新');
            } else {

                if(is_callable($callback))
                    call_user_func($callback,$condition[$pk]);

                LogService::memberLog($log_type,$log_remark);
                return true;
            }
        }
    }
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

            LogService::memberLog($log_type , $log_remark);
            return true;
        }
        else throw new ApiException('删除失败');
    }
}
