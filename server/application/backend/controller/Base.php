<?php

namespace app\backend\controller;

use app\backend\model\AuthNode;
use app\backend\model\Auth;
use app\common\controller\BaseController;
use app\common\model\Config;
use think\Controller;

/**
 * 基础类
 * @author Ray 541720500@qq.com
 */
class Base extends BaseController
{
    protected $beforeActionList = [
        'checkAuth' => ['except' => 'loginform,login,logout,cronJobs'], // 不支持驼峰写法
    ];

    public function dataValidate($data, $type = null)
    {
        $conf = config("validate.");
        $result = $this->validate($data, $conf[$type], isset($conf[$type . '_msg']) ? $conf[$type . '_msg'] : null);
        if (true !== $result)
            $this->error($result);
    }

//    public function initialize()
//    {
//        $this->assign([
//            'ctr_title' => 1,
//            'act_title' => 2
//        ]);
//    }

    // 执行创建方法
    public function handleCreate($input = null)
    {
        if ($input == null) {
            $input = input('post.');
        }
        if (input('_m') == null) {
            $this->error('缺少设置模型');
        }
        $this->modelCreate(input('_m'), $input, input('_vali', ''), input('_title'));
    }

    // 执行修改方法
    public function handleSave($input = null)
    {
        if ($input == null) {
            $input = input('post.');
        }
        if (input('_m') == null) {
            $this->error('缺少设置模型');
        }
        if (input('id') == null) {
            $this->error('缺少修改条件');
        }
        $this->modelUpdate(input('_m'), $input, ['id' => input('id')], input('_vali', ''), input('_remark'), input('_cb'));
    }

    // 执行删除方法
    public function handleDel()
    {
        if (input('_m') == null) {
            $this->error('缺少删除模型');
        }
        if (input('id') == null) {
            $this->error('缺少删除条件');
        }
        return $this->modelDelete(input('_m'), ['id' => input('id')]);
    }

    private function singlePointLogin()
    {
        $staff_id = session('login_id');
        $catch = cache('current_login_ip_' . $staff_id);
        if ($staff_id && $catch && $catch != request()->ip()) {
            session(null);
            $this->error('您的账号已在别处登录！', 'login/loginForm');
        }
    }

    protected function checkAuth()
    {
//        dump(Auth::getAuthNodeIds(session('login_id')));
//        $this->singlePointLogin();
        // 检查session
        $admin_id = session('login_id');
        $admin_name = session('login_name');

        if ($admin_id == false)
            $this->error('请登录', url('backend/login/loginForm'));

        // 判断是否为超级管理员
        $super_admins = explode(',', config('status.auth.super_auth_adm'));
        if (in_array($admin_name, $super_admins) === true)
            return true; // 超级管理员跳过验证

        // 获取当前控制器的id
        $controller = strtolower(request()->controller());
        $action = strtolower(request()->action());

        $no_auth_controller = explode(',', strtolower(config('status.auth.no_auth_controller')));
        if (in_array($controller, $no_auth_controller))
            return true;

        // 获取当前用户的节点列表
        $auth_nodes = Auth::getAuthNodeIds($admin_id);
        if (empty($auth_nodes)) {
            $this->error('该账号还未设置权限！');
        }

        $ca = strtolower($controller.'/'.$action);
        $find_id = null;
        $node_ids = AuthNode::column('url', 'id');
        foreach ($node_ids as $id => $url) {
            $url = strpos($url, '?') ? substr($url, 0, strpos($url, '?')) : $url;
            if(strtolower($url) == $ca) {
                $find_id = $id;
            }
        }
//                dump($find_id);

        // 判断controller是否在许可范围内
        if ($find_id !==null && in_array($find_id, $auth_nodes) === false)
            $this->error('您没有权限访问！');

        return true;
    }

    // 管理员日志
    protected function makeLog($content)
    {
        $data['add_time'] = now();
        $data['login_id'] = session('login_id');
        $data['login_name'] = session('login_name');
        $data['ip'] = request()->ip();
        $data['path'] = request()->path();
        $data['content'] = $content;
        $result = db('system_log')->insert($data);
        if ($result == false) \think\Log::record('管理员日志写入错误');
    }

    /*
    * name：dbAdd数据删除方法
    * param：数据库，数据
    */
    protected function dbAdd($db, $data, $path = "", $word = "添加")
    {
        $res = db($db)->insert($data);
        if ($res) $this->makeLog('[新增成功] [DB] ' . $db);
        $res ? $this->success($word . '成功', $path) : $this->error($word . '失败', $path);
    }

    /*
    * name：dbUpdate数据更新方法
    */
    protected function dbUpdate($db, $data, $condition, $path = "")
    {
        $res = db($db)->where($condition)->update($data);
        if ($res) $this->makeLog('[更新成功] [DB] ' . $db . ' [' . key($condition) . '] ' . current($condition));
        $res ? $this->success('更新成功', $path) : $this->error('更新失败', $path);
    }

    protected function modelUpdate($model, $data, $condition = [], $validate = '', $msg = '修改', callable $callback = null)
    {
        if ($validate) {
            $this->dataValidate($data, $validate);
        }
        $res = model($model)->allowField(true)->save($data, $condition);
        if (false === $res) {
            $this->error(model($model)->getError());
        } elseif ($res === 0) {
            $this->error('无' . $msg);
        } elseif ($res) {
            $this->makeLog("[" . $msg . "成功] [MODEL] " . $model);
            if ($callback) {
                if (is_callable($callback)) {
                    $res_cb = $callback($condition);
                    if(isset($res_cb['code']) && $res_cb['code'] == 0) $this->error($res_cb['msg']);

                } elseif (is_string($callback)) {
                    action($callback, $condition);
                }
            }
            $this->success($msg . ' 成功');
        } else {
            $this->error($msg . ' 错误');
        }
    }

    protected function modelCreate($model, $data, $validate = '', $msg = '添加', callable $callback = null)
    {
        if ($validate) {
            $this->dataValidate($data, $validate);
        }
        $res = model($model)->allowField(true)->create($data);
        if (false == $res) {
            $this->error(model($model)->getError());
        } elseif ($res) {
            $this->makeLog("[" . $msg . "成功] [MODEL] " . $model);
            if ($callback) {
                if (is_callable($callback)) {
                    $callback($res);
                }
            }
            $this->success($msg . '成功');
        } else {
            $this->error($msg . '失败');
        }
    }
    /*
    * name：moDelete数据删除方法
    * param：模型，删除条件
    * author：Ray
    */
    protected function modelDelete($model, $condition)
    {
        $res = model($model)->where($condition)->delete();
        if ($res) $this->makeLog('[删除成功] [MODEL] ' . $model);
        echo $res ? 1 : 0;
    }

    //数据库的删除方法
    protected function dbDelete($db, $condition)
    {
        $res = db($db)->where($condition)->delete();
        if ($res) $this->makeLog('[删除成功] [DB] ' . $db . ' [' . key($condition) . '] ' . current($condition));
        echo $res ? 1 : 0;
    }

    protected function fetchFormPageHtml($class, $form_action, $data = [], $makeFromDataAction = 'makeFromData', $pk = 'id', $btn_text = "提 交")
    {
        $class = model($class);
        if (empty($data) && $id = input($pk)) {
            $data = $class->where($pk, $id)->find()->toArray();
        }
        $this->assign('data', $class->$makeFromDataAction($data));
        $this->assign('action', $form_action);
        $this->assign('btn_text', $btn_text);
        return $this->fetch('public/form_builder');
    }

    protected function checkIsPost()
    {
        if (request()->isPost() == false) $this->error('非法请求');
    }

    // 参数配置页
    protected function settingPage($action, $config_map, callable $callback = null)
    {
        $group_name = input('group', $config_map[0]);
        $model = model('Config');
        if (request()->isPost()) {
            $data = $_POST;
//            $data = input('post.');
            if (isset($data['__token__'])) unset($data['__token__']);
            foreach ($data as $k => $v) {
                $callback != null && $callback($k, $v);
                if ($model->where('key', $k)->find()) {
                    $model->where('key', $k)->update(['value' => $v, 'group' => $group_name]);
                } else {
                    $model->insert(['key' => $k, 'value' => $v, 'group' => $group_name]);
                }
            }
            $this->makeLog('[修改设置]');
            $this->success('已保存');
        } else {
            $load_data = $model->where('group', $group_name)->order('sort')->column('value', 'key');
            $config_group = [];
            foreach ($config_map as $m) {
                $config_group[$m] = \app\backend\model\Config::$maps[$m]['title'];
            }
            $this->assign('config_group', $config_group);
            $this->assign('data', $model->makeFromData($load_data, $group_name));
            $this->assign('action', url($action, ['group' => $group_name]));
            $this->assign('cur_group', $group_name);
            return $this->fetch('system/config');
        }
    }
}