<?php
namespace app\backend\controller;
use app\backend\model\UserLevelModel;
use app\backend\model\UserModel;
use app\common\model\UserAccountLog;
use app\common\model\UserLog;

class User extends Base
{
    public function users()
    {
        $where = [];
        if(input('name')) {
            array_push($where, ['name', '=', input('name')]);
        }
        if(input('mob')) {
            array_push($where, ['mobile', '=', input('mob') ]);
        }

        if(input('status') !== null && input('status') !== '')
            array_push($where, ['status', '=', input('status')]);

        $this->assign('data', [
            'title' => '会员列表',
            'collection' => UserModel::where($where)->order('id desc')->paginate(config('paginate.per_page'), false, ['query'=>$this->request->param() ]),
            'thead' => ['ID', ['#','width:60px'],  '姓名', '手机号', '登录时间', '注册时间', '状态'],
            'fields' => ['id',
                function($row) {
                    return '<image src="'.$row['avatar'].'" class="ui tiny image">';
                },
                'name','mobile','login_time','reg_time',  function ($row) {
                    return $row->status == 1 ? '正常' : '已禁用';
                }
            ],
            'buttons' => [
//                ['title'=>'详情', 'onclick' => 'openBigWin', 'url'=> url('details')], // 'onclick'=>'redirect',
//                ['title'=>'改状态','onclick'=>'ajaxReq','url'=> url('switchStatus')],
                ['title'=>'编辑','onclick'=>'openBigWin','url'=> url('saveUser')],
            ],
            'mbuttons' => [
                ['title' => '添加账号', 'onclick' => 'openBigWin', 'url' => url('saveUser')],
            ],
            'searcher' => [
                ['type'=>'input','name'=>'name','placeholder'=>'姓名'],
                ['type'=>'input','name'=>'mob','placeholder'=>'手机号'],
                ['type'=>'select','name'=>'status', 'options' => config('status.normal')],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    public function saveUser()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if ($id = input('id')) {
                $this->modelUpdate('app\backend\model\UserModel', $data, ['id' => $data['id']], 'user_saving', '修改用户信息');
            } else {
                $this->modelCreate('app\backend\model\UserModel', $data, 'user_saving', '添加用户信息');
            }
        } else {
            return $this->fetchFormPageHtml('app\backend\model\UserModel', __FUNCTION__);
        }
    }

    public function accountLog()
    {
        $where = [];
        if(input('name')) {
            $user = \app\common\model\User::getOneBy(['name' => input('name')]);
            array_push($where, ['uid', '=',  $user ? $user->id : null]);
        }
        if(input('mob')) {
            $user = \app\common\model\User::getOneBy(['mobile' => input('mob')]);
            array_push($where, ['uid', '=',  $user ? $user->id : null]);
        }
        $this->assign('data', [
            'title' => '会员账单',
            'collection' => UserAccountLog::where($where)->with('user')->order('id desc')->paginate(config('paginate.per_page'), false, ['query'=>$this->request->param() ]),
            'thead' => [
                ['时间','width:200px'], '会员', '金额', '原因', '备注',
            ],
            'fields' => ['add_time',
                function($row) {
                    return $row->user['name'];
                },
                'amount','stage','remark'
            ],
            'searcher' => [
                ['type'=>'input','name'=>'name','placeholder'=>'姓名'],
                ['type'=>'input','name'=>'mob','placeholder'=>'手机号'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    public function switchStatus()
    {
        $res = \app\user\model\UserModel::changeStatus(input('id/d'));
        return $res['code'] == 1 ?  $this->success($res['msg']) : $this->error($res['msg']);
    }

    public function withdrawal()
    {
        $where = [];
        if (input('phone')) {
            $user = \app\common\model\User::getOneBy(['mobile'=> input('phone')]);
            array_push($where, ['user_id', '=', $user->id ?? '']);
        }
        if (input('name')) {
            array_push($where, ['name', '=', input('name')]);
        }
        $this->assign('data', [
            'title' => '提现列表',
            'collection' => Withdrawal::where($where)->order('id desc')->paginate(config('paginate.per_page'),false, ['query'=>$this->request->param() ]),
            'thead' => [
                '学员ID','学员姓名','提现金额','银行名称','银行卡号','姓名','电话','申请时间','处理状态',''
            ],
            'fields' => [
                'user_id',  function($row) {
                    return $row['user']['name'];
                },'amount','bank_name','account','name','phone','add_time','status',function($row) {
                    return $row['status'] == '申请中' ?
                        '<a class="ui small teal label" data-title="确认通过" data-url="user/withdrawalAgree.html" onclick="handleConfirm(this, \''.$row['id'].'\')">通 过</a> ' .
                        '<a class="ui small orange label" data-title="确认驳回" data-url="user/withdrawalRefuse.html" onclick="handleConfirm(this, \''.$row['id'].'\')">驳 回</a> ' :
                        '';
                }
            ],
            'searcher' => [
                ['type' => 'input', 'name' => 'phone', 'placeholder' => '学员手机号'],
                ['type' => 'input', 'name' => 'name', 'placeholder' => '收款人姓名'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    public function withdrawalAgree()
    {
        $id = input('id/d');
        if( Withdrawal::where('id',$id)->update(['status' => 1]) ) {
            $this->success('已处理');
        } else {
            $this->error('操作失败');
        }
    }
    public function withdrawalRefuse()
    {
        $id = input('id/d');
        if( Withdrawal::where('id',$id)->update(['status' => -1]) ) {
            $this->success('已驳回');
        } else {
            $this->error('操作失败');
        }
    }
    public function teachers()
    {
        $where = [];
        if(input('title')) array_push($where, ['name', '=', input('name')]);
        if(input('status') === '0') array_push($where, ['status', '=', 0]);
        else  array_push($where, ['status', '=', 1]);
        $this->assign('data', [
            'title' => '老师列表',
            'collection' => Teacher::where('is_teacher=1')->where($where)->with('role')->withCount('courses')
                ->order('status desc')->paginate(config('paginate.per_page'), false, ['query'=>$this->request->param() ]),
            'thead' => ['#',['头像','width:60px'],'账号','姓名','手机号','课程数','管理组','登录次数','状态'],
            'fields' => ['id',
                function($row) {
                    return '<image src="'.$row['avatar'].'" class="ui tiny image">';
                }, 'account', 'name', 'mobile',
                function($row){
                    return $row->courses_count;
                },
                function($row){
                    $str = '';
                    foreach ($row->role as $v) {
                        $str .=  '[' . $v->name . '] ';
                    }
                    return $str;
                },
                'login_num','status'],
            'buttons' => [
                ['title'=>'修改', 'onclick' => 'openBigWin', 'url'=> url('auth/newadm')],
//                ['title'=>'改密码','url'=> url('auth/changePw'),'onclick'=>'handlePrompt'],
            ],
            'mbuttons' => [
                ['title'=>'添加账号', 'onclick' => 'openBigWin','url'=> url('auth/newAdm')],
            ],
            'searcher' => [
                ['type'=>'input','name'=>'name','placeholder'=>'姓名'],
                ['type'=>'select','name'=>'status','placeholder'=>'状态','options'=>config('status.normal')],
            ],
        ]);

        return $this->fetch('public/table_builder');
    }
    public function level()
    {
        $this->assign('data', [
            'title' => '会员等级',
            'collection' => UserLevelModel::all(),
            'thead' => [ 'ID', '等级名称', '所需积分' ],
            'fields' => [ 'id', 'name', 'need_points' ],
            'buttons' => [
                ['title'=>'修改','url'=> url('user/createLevel')],
            ],
            'mbuttons' => [
                ['title'=>'添加等级','url'=> url('user/createLevel')],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
    function createLevel()
    {
        if (request()->isPost()) {
            $data = input('post.');
            cache('user_levels', null);
            if ($id = input('id')) {
                $this->modelUpdate('app\backend\model\UserLevelModel', $data, ['id' => $data['id']], 'user_level_save', '修改等级');
            } else {
                $this->modelCreate('app\backend\model\UserLevelModel', $data, 'user_level_save', '添加等级');
            }
        } else {
            return $this->fetchFormPageHtml('app\backend\model\UserLevelModel', __FUNCTION__);
        }
    }
    public function log()
    {
        $where = [];
        if (input('phone')) {
            $user = \app\common\model\User::getOneBy(['mobile'=> input('phone')]);
            array_push($where, ['user_id', '=', $user->id ?? '']);
        }
        $this->assign('data', [
            'title' => '学员操作日志',
            'collection' => UserLog::where($where)->order('id desc')->paginate(config('paginate.per_page'),false, ['query'=>$this->request->param() ]),
            'thead' => [ '时间', '会员', '日志内容' ],
            'fields' => [ 'add_time', function($row){return $row->user['name'] . ' ' . $row->user->mobile;}, 'remark' ],
            'searcher' => [
                ['type'=>'input','name'=>'phone','placeholder'=>'手机号'],
            ],
        ]);
        return $this->fetch('public/table_builder');
    }
}
