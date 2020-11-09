<?php
/**
 * Created by PhpStorm.
 * User: Ray
 * Date: 2019/4/3
 * Time: 13:59
 */

namespace app\common\model;

use think\Db;
use think\facade\Validate;
use think\Model;

class BaseModel extends Model
{
    static $err_msg;
    protected static $allowFields = true;

    static function getOneBy($condition, $order_by='')
    {
        return self::where($condition)->order($order_by)->find();
    }
    // 事务处理函数
    public static function handleDbTrans($func)
    {
        Db::startTrans();
        try {
            $func();
            Db::commit();
        } catch (\Exception $e) {
            Db::rollback();
            return $e->getMessage();
        }
        return true;
    }
    /**
     * 分页
     */
    public static function paging($where = [], $sort = '', \Closure $handler = null, $limit = 15)
    {
        $current_page = input('page/d', 1); // 当前页码
        $per_page = input('per_page', $limit); // 每页显示数
        $model = self::where($where);
        if ($handler && is_callable($handler)) {
            $handler($model);
        }
        $data_count = $model->count(); // 数据数量
        $list = $model->page($current_page, $per_page)->order($sort)->select();
        $total_page = ceil($data_count / $limit); // 总页数
        return compact('list', 'current_page', 'per_page', 'total_page', 'data_count');
    }

    public static function handleCreate($data, $vali_rule = [])
    {
        self::handleValidate($data, $vali_rule);
        if ($model = self::create(
            $data, (is_array(self::$allowFields) && !empty(self::$allowFields)) ? self::$allowFields : null
        )) {
            return $model;
        } else {
            exception('新建失败');
        }
    }

    public function handleInsert($data, $vali_rule = [], $get_id = false)
    {
        self::handleValidate($data, $vali_rule);
        if ($get_id) {
            $res = $this->insertGetId($data);
        } else {
            $res = $this->insert($data);
        }
        return $res ? $res : exception('添加失败');
    }

    public function handleUpdate($data, $condition, $vali_rule = [])
    {
        self::handleValidate($data, $vali_rule);
        if (false === $this->allowField(true)->isUpdate(true)->save($data, $condition)) {
            exception('更新失败');
        } else {
            return true;
        }
    }

    public static function handleSoftDelete($condition)
    {
        if (self::where($condition)->update(['is_del' => 1]) !== false) {
            return true;
        } else {
            exception('删除失败');
        }
    }


    private static function handleValidate($data, $vali_rule = [])
    {
        if (!empty($vali_rule)) {
            $validate = Validate::make($vali_rule);
            if (!$validate->check($data)) {
                exception($validate->getError());
            }
        }
    }

    // 转换状态文字
    protected function transferStatusText($status_key, $config_key)
    {
        $status = config('status.'.$config_key);
        return isset($status[$status_key]) ? $status[$status_key] : $status_key;
    }
}