<?php

namespace app\backend\controller;

use app\backend\model\AttendanceModel;
use app\backend\model\OffdutyTypeModel;
use app\backend\model\PayoutModel;
use app\backend\model\PayoutTypeModel;
use app\backend\model\StaffModel;
use app\backend\model\StaffOffdutyModel;
use app\common\model\Attendance;
use app\common\model\Payout;
use app\common\model\SchedulePlan;
use app\common\model\StaffOffduty;

class Staff extends Base
{
    public function getList()
    {
//        $list = StaffModel::paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]);
//        $this->assign('data', [
//            'title' => '老师列表',
//            'collection' => $list,
//            'thead' => ['ID', '姓名', '密码', ['照片', ['style' => '150px']], '电话'],
//            'fields' => [
//                'id', 'name', 'password',
//                function ($row) {
//                    return $row['photo'] ? '<image src="' . $row['photo'] . '" class="ui tiny image">' : '';
//                },
//                'phone'
//            ],
//            'buttons' => [
//                ['title' => '修改', 'url' => url('editStaff')],
//                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('deleteStaff')],
//            ],
//            'mbuttons' => [
////                ['title' => '值班安排', 'url' => url('Schedule/editSchedule')],
//                ['title' => '添加老师', 'url' => url('editStaff')],
//            ],
//        ]);
//        return $this->fetch('public/table_builder');

        $where = [];
        if (input('name')) array_push($where, ['name', '=', input('name')]);
        if (input('status') == -1) array_push($where, ['status', '=', -1]);
        else  array_push($where, ['status', '=', 1]);
        $this->assign('data', [
            'title' => '职员管理',
            'collection' => \app\backend\model\Auth::where($where)->with('role')->order('status desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => ['#', '账号', ['照片', ['style' => '150px']], '电话', '管理组', '登录次数', '上次登录时间', '上次登录IP', '状态'],
            'fields' => ['id', 'name',
                function ($row) {
                    return $row['photo'] ? '<image src="' . $row['photo'] . '" class="ui tiny image">' : '';
                }, 'phone',
                function ($row) {
                    $str = '';
                    foreach ($row->role as $v) {
                        $str .= '[' . $v->name . '] ';
                    }
                    return $str;
                }, 'login_num', 'login_time', 'ip', 'status_text'],
            'buttons' => [
                ['title' => '修改', 'onclick' => 'openBigWin', 'url' => url('auth/newadm'), 'height' => 600,'permission_id' => 32],
                ['title' => '改密码', 'url' => url('auth/changePw') , 'onclick' => 'handlePrompt','permission_id' => 34],
                ['title' => '管辖人员', 'url' =>url('manageStaff'),'permission_id' => 152],
            ],
            'mbuttons' => [
                ['title' => '添加账号', 'onclick' => 'openBigWin', 'url' => url('auth/newAdm'), 'height' => 600,'permission_id' => 32],
            ],
            'searcher' => [
                ['type' => 'input', 'name' => 'name', 'placeholder' => '姓名'],
                ['type' => 'select', 'name' => 'status', 'placeholder' => '状态', 'options' => config('status.normal')],
            ],
        ]);

        return $this->fetch('public/table_builder');
    }

    function editStaff()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $data['photo'] = isset($data['photo']) ? $data['photo'][0] : null;
            if ($id = input('id')) {
                $this->modelUpdate(StaffModel::class, $data, ['id' => $data['id']], 'staff_save', '修改老师');
            } else {
                $this->modelCreate(StaffModel::class, $data, 'staff_save', '添加老师');
            }
        } else {
            return $this->fetchFormPageHtml(StaffModel::class, __FUNCTION__);
        }
    }

    function manageStaff()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if ($id = input('id')) {
                $this->modelUpdate(StaffModel::class, $data, ['id' => $data['id']], '', '修改管辖人员');
            } else {
                $this->modelCreate(StaffModel::class, $data, '', '添加管辖人员');
            }
        } else {
            return $this->fetchFormPageHtml(StaffModel::class, __FUNCTION__, [], 'makeFromData2');
        }
    }

    public function deleteStaff()
    {
        $id = input('id/d');
        echo StaffModel::destroy($id) ? 1 : 0;
    }

    public function offduty()
    {
        $where = [];
        $type = input('type');
//        if ($type == 'mine') {
//            $where[] = ['staff_id', '=', session('login_id')];
//            $title = '我的请假';
//        } else
        $title = '请 假';

        if ($type_id = input('type_id')) {
            $where[] = ['type_id', '=', $type_id];
        }
        if ($status = input('status')) {
            $where[] = ['status', '=', $status];
        }
        $manage_id_list = \app\common\model\Staff::getLeaveManageStaffIds(session('login_id'));
        $staff_id_list[] = session('login_id');
        if ($staff_id = input('staff_id')) {
            $where[] = ['staff_id', '=', $staff_id];
        } else {
            if($manage_id_list) {
                $manage_id_list = explode(',', $manage_id_list);
                $staff_id_list = array_merge($staff_id_list, $manage_id_list);
            }
            $where[] = ['staff_id','in', $staff_id_list];
        }

        $list = StaffOffduty::where($where)->order('id desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]);
        $data = [
            'title' => $title,
            'collection' => $list,
            'thead' => ['ID', '姓名', '类型', '事由', '附图', '请假周期' , '审核状态', '审核人', '审核备注'],
            'fields' => [
                'id',
                function ($row) {
                    return $row->staff->name;
                },
                function ($row) {
                    return $row->type ? $row->type->name : '';
                },
                'reason',
                function ($row) {
                    $img = '';
                    if(!empty($row['images'])) {
                        foreach ($row['images'] as $v) {
                            $img.= '<img src="'.$v.'" style="width:30px; margin:0 3px;" onclick="previewImage(this)"/>';
                        }
                    }
                    return $img;
                },
                function ($row) use ($type) {
                    $text = $row['start_date'] . ' ' . (intval($row['start_date_time']) ? $row['start_date_time']:'') . ' ~ '
                        . $row['end_date'] . ' ' . (intval($row['end_date_time']) ? $row['end_date_time']:'')
                        . (($type == 'mine' && $row->status == 0) ?
                            ' <a class="ui small label" data-title="修改" data-url="/backend/staff/saveOffduty.html" onclick="openBigWin(this, \'' . $row['id'] . '\')">修改</a>
                              <a class="ui small label" data-title="删除" data-url="/backend/staff/deleteOffduty.html" onclick="delOne(this, \'' . $row['id'] . '\')">撤销</a>'
                            : ''
                        );
                    return $text;
                },
                function ($row) {
                    return $row->status == 0 ?
                        $text = '<a class="ui small teal label" data-title="审批" data-url="/backend/staff/verifyoffduty.html" onclick="openWin(this, \'' . $row['id'] . '\' )">审批</a>' :
                        $row->status_text;
                },
                function ($row) {
                    return $row['verifier_name'] . '<br>' . $row['verify_time'];
                },
                'verify_remark'
            ],
        ];
//        if ($type == 'mine') {
//            $data['mbuttons'] = [
//                ['title' => '我要请假', 'onclick' => 'openBigWin', 'url' => url('saveOffduty')]
//            ];
//        } else {
        $data['searcher'] = [
            ['type' => 'select', 'name'=>'type_id', 'options' => OffdutyTypeModel::column('name','id'), 'placeholder' => '请假类型'],
            ['type' => 'select', 'name'=>'status', 'options' => config('status.verify'), 'placeholder' => '请假状态'],
        ];
        if($manage_id_list){
            $data['searcher'][2] = ['type' => 'select', 'name'=>'staff_id', 'options' => \app\common\model\Staff::getKV(), 'placeholder' => '请假人'];
        }
        $data['mbuttons'] = [
            ['title' => '请假类型', 'onclick' => 'openBigWin', 'url' => url('offdutyType'), 'class_name' => ''],
            ['title' => '我要请假', 'onclick' => 'openBigWin', 'url' => url('saveOffduty')]
        ];
//        }
        $this->assign('data', $data);

        return $this->fetch('public/table_builder');
    }

    // 请假
    function saveOffduty()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $data['staff_id'] = session('login_id');
            $data['status'] = 0;
            $data['images'] = isset($_POST['images']) ? implode(',',$_POST['images']) : '';

            if(!$data['start_date']) $this->error('缺少开始日期');
            if(!$data['end_date']) $this->error('缺少结束日期');
            $data['start_date_time'] = intval($data['start_date_time']).':00';
            $data['end_date_time'] = intval($data['end_date_time']).':00';
            $start_time = $data['start_date_time'] ? strtotime($data['start_date'] .' '. $data['start_date_time']) : strtotime($data['start_date']) ;
            $end_time = $data['end_date_time'] ? strtotime($data['end_date'] .' '. $data['end_date_time']) : strtotime($data['end_date']) ;

            if(strtotime('-2day') > $start_time) $this->error('开始时间设置错误');
            if($end_time < $start_time) $this->error('结束日期设置错误');

            if ($id = input('id')) {
                $this->modelUpdate(StaffOffdutyModel::class, $data, ['id' => $data['id']], 'save_offduty', '修改请假');
            } else {
                $data['add_time'] = now();
                $this->modelCreate(StaffOffdutyModel::class, $data, 'save_offduty', '申请请假');
            }
        } else {
            return $this->fetchFormPageHtml(StaffOffdutyModel::class, __FUNCTION__, [], 'makeFromData2');
        }
    }
    // 撤销请假
    function deleteOffduty()
    {
        $id = input('id/d');
        echo StaffOffdutyModel::destroy($id) ? 1 : 0;
    }

    public function verifyOffduty()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $data['verify_time'] = now();
            $data['verifier_name'] = session('login_name');
            if ($id = input('id')) {
                $this->modelUpdate(StaffOffdutyModel::class, $data, ['id' => $data['id']], '', '审批');
            }
        } else {
            return $this->fetchFormPageHtml(StaffOffdutyModel::class, __FUNCTION__);
        }
    }

    public function payout()
    {
        $where = [];
        $type = input('type');
//        if ($type == 'mine') {
//            $where[] = ['staff_id', '=', session('login_id')];
//            $title = '我的请款';
//        } else
        $title = '请 款';

        if ($staff_id = input('staff_id')) {
            $where[] = ['staff_id', '=', $staff_id];
        }
        if ($type_id = input('type_id')) {
            $where[] = ['type_id', '=', $type_id];
        }
        if ($status = input('status')) {
            $where[] = ['status', '=', $status];
        }

        $manage_id_list = \app\common\model\Staff::getPayoutManageStaffIds(session('login_id'));
        if($manage_id_list)
            $where[] = ['staff_id','in', $manage_id_list];
        else
            $where[] = ['staff_id','in', session('login_id')];

        $list = Payout::where($where)->order('id desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]);
        $data = [
            'title' => $title,
            'collection' => $list,
            'thead' => ['ID', '申请人', '类型','事由', '金额','附图', '申请时间 ', '收款人/账号', '进度', '审核信息', '支付信息'],
            'fields' => [
                'id',
                function ($row) {
                    return $row->staff->name;
                },
                function ($row) {
                    return $row->type ? $row->type->name : '';
                },
                'reason',
                function ($row) {
                    $text = $row['money']
                        . (($row->status == 0) ?
                            ' <a class="ui small label" data-title="修改" data-url="/backend/staff/savePayout.html" onclick="openBigWin(this, \'' . $row['id'] . '\')">修改</a>
                              <a class="ui small label" data-title="删除" data-url="/backend/staff/deletePayout.html" onclick="delOne(this, \'' . $row['id'] . '\')">撤销</a>'
                            : ''
                        );
                    return $text;
                },
                function ($row) {
                    $img = '';
                    if(!empty($row['images'])) {
                        foreach ($row['images'] as $v) {
                            $img.= '<image src="'.$v.'" style="width:30px; margin:0 3px;" onclick="previewImage(this)"/>';
                        }
                    }
                    return $img;
                },
                'add_time',
                function ($row) {
                    echo  $row['payee'] . '<br>' . $row['payee_account'] ;
                },
                function ($row) {
                    echo $row['status_text'];
                    if( $row->status == 0 ) {
                        echo ' <a class="ui small teal label" data-title="审批" data-url="/backend/staff/verifyPayout.html" onclick="openWin(this, \'' . $row['id'] . '\' )">审批</a>';
                    } elseif ( $row->status == 1 ) {
                        echo ' <a class="ui small orange label" data-title="支付" data-url="/backend/staff/paidPayout.html" onclick="openWin(this, \'' . $row['id'] . '\' )">支付</a>';
                    }
                },
                function ($row) {
                    if( $row->status != 0 ) {
                        echo  $row['verifier_name'] . ' ' . $row['verify_time']. '<br>' . $row['verify_remark'] ;
                    } else {
                        echo '无';
                    }
                },
                function ($row) use ($type) {
                    if( $row->status == 2 ) {
                        echo $row['paid_name'] . ' ' .  $row['paid_time']. '<br>' . $row['paid_remark'] ;
                    } else {
                        echo '无';
                    }
                },
            ],
        ];
//        if ($type == 'mine') {
//            $data['mbuttons'] = [
//                ['title' => '我要请款', 'url' => url('savePayout')],
//            ];
//        } else {
        $data['searcher'] = [
            ['type' => 'select', 'name'=>'type_id', 'options' => PayoutTypeModel::column('name','id'), 'placeholder' => '请款类型'],
            ['type' => 'select', 'name'=>'status', 'options' => config('status.payout'), 'placeholder' => '请款状态'],
        ];
        if($manage_id_list){
            $data['searcher'][2] = ['type' => 'select', 'name'=>'staff_id', 'options' => \app\common\model\Staff::getKV(), 'placeholder' => '请款人'];
        }
        $data['mbuttons'] = [
            ['title' => '请款类型', 'onclick' => 'openBigWin', 'url' => url('payoutType'), 'class_name' => ''],
            ['title' => '我要请款', 'url' => url('savePayout'), 'height'=>'700'],
        ];
//        }
        $this->assign('data', $data);
        return $this->fetch('public/table_builder');
    }
    function savePayout()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $data['staff_id'] = session('login_id');
            $data['status'] = 0;
            $data['images'] = isset($_POST['images']) ? implode(',',$_POST['images']) : '';

            if ($id = input('id')) {
                $this->modelUpdate(PayoutModel::class, $data, ['id' => $data['id']], 'save_payout', '修改请款');
            } else {
                $data['add_time'] = now();
                $this->modelCreate(PayoutModel::class, $data, 'save_payout', '添加请款');
            }
        } else {
            return $this->fetchFormPageHtml(PayoutModel::class, __FUNCTION__, [], 'makeFromData2');
        }
    }
    function deletePayout()
    {
        $id = input('id/d');
        echo PayoutModel::destroy($id) ? 1 : 0;
    }

    public function verifyPayout()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $data['verify_time'] = now();
            $data['verifier_name'] = session('login_name');
            $this->modelUpdate(PayoutModel::class, $data, ['id' => $data['id']], '', '请款审批');
        } else {
            return $this->fetchFormPageHtml(PayoutModel::class, __FUNCTION__);
        }
    }
    public function paidPayout()
    {
        if (request()->isPost()) {
            $data = input('post.');
            $data['paid_time'] = now();
            $data['paid_name'] = session('login_name');
            $data['status'] = 2;
            $this->modelUpdate(PayoutModel::class, $data, ['id' => $data['id']], '', '请款支付');
        } else {
            return $this->fetchFormPageHtml(PayoutModel::class, __FUNCTION__, [], 'makeFromData3');
        }
    }

    // 考勤
    public function attendance()
    {
        $where = [];
        $staff_id_list[] = session('login_id');
        $manage_id_list = \app\common\model\Staff::getLeaveManageStaffIds(session('login_id'));
        if ($staff_id = input('staff_id')) {
            $where[] = ['staff_id', '=', $staff_id];
        } else {
            if($manage_id_list) {
                $manage_id_list = explode(',', $manage_id_list);
                $staff_id_list = array_merge($staff_id_list, $manage_id_list);
            }
            $where[] = ['staff_id','in', $staff_id_list];
        }
        if (($start_date = input('start_date')) && ($end_date = input('end_date'))) {
            $where[] = ['date', 'between', [$start_date, $end_date]];
        }
        $list = Attendance::where($where)->order('id desc')->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]);
        $data = [
            'title' => '考勤记录',
            'collection' => $list,
            'thead' => ['日期', '姓名', '值班计划', '签到时间', '签退时间', '审核备注'],
            'fields' => [
                function ($row) {
                    return $row['date']. ' ' .weekdayText(date('w', strtotime($row['date'])));
                },
                function ($row) {
                    return $row->staff->name;
                },
                function ($row) {
                    $plan_ids = \app\common\model\Schedule::where('staff_id', $row['staff_id'] )->value('plan_ids');
                    $plans = SchedulePlan::where('id','in',$plan_ids)->select();
                    $plan_list=[];
                    foreach ($plans as $v) {
                        $plan_list[] = $v['name'] . ' 工作日:'. $v['weekdays'];
                    }
                    echo implode('<br/>', $plan_list);
//                    return $row->status == 1 ? '正常' : '休假';
                },
                function ($row) {
                    if($row->be_late == 1) {
                        echo '<span style="color: red;">' . ($row['come_time']??'无') . '</span>';
                        echo '<a class="ui tiny label" data-title="补签" data-url="'.url('attendanceApply',['type' =>'late_apply']).'" onclick="openWin(this, \''.$row['id'].'\')">补签</a>';
                    } elseif($row->be_late == 0) {
                        echo '<span style="color: red;">' . ($row['come_time']??'无') . '</span>';
                        echo '<br>申请补签:' . $row->late_reason;
                        echo '<a class="ui tiny green label" data-title="审核" data-url="'.url('attendanceApply',['type' =>'late_verify']).'" onclick="openWin(this, \''.$row['id'].'\')">审核</a>';
                    } else {
                        echo $row['come_time'] . ' ' . $row['late_reason'];
                    }
                },
                function ($row) {
                    if($row->leave_early == 1) {
                        echo '<span style="color: red;">' . ($row['leave_time']??'无') . '</span>';
                        echo '<a class="ui tiny label" data-title="补签" data-url="'.url('attendanceApply',['type' =>'leave_apply']).'" onclick="openWin(this, \''.$row['id'].'\')">补签</a>';
                    } elseif($row->leave_early == 0) {
                        echo '<span style="color: red;">' . ($row['leave_time']??'无') . '</span>';
                        echo '<br>申请补签:' . $row->leave_reason;
                        echo '<a class="ui tiny green label" data-title="审核" data-url="'.url('attendanceApply',['type' =>'leave_verify']).'" onclick="openWin(this, \''.$row['id'].'\')">审核</a>';
                    } else {
                        echo $row['leave_time'] . ' ' . $row['leave_reason'];
                    }
                },
                function ($row) {
                    return $row['verify_remark'] ?  $row['verifier_name'] . ': ' . $row['verify_remark'] : '';
                },
            ],
            'mbuttons' => [
//                ['title' => '打印', 'onclick' => 'handlePrint','class_name'=>''],
                ['title' => '导出', 'onclick' => 'openBigWin','url' => url('exportAttendance'),'class_name'=>'','permission_id' => 178],
                ['title' => '值班安排', 'onclick' => 'openBigWin','url' => url('schedule/staffSchedule'),'permission_id' => 153],
            ],
            'searcher' => [
                ['type' => 'date', 'name'=>'start_date', 'placeholder' => '开始时间'],
                ['type' => 'date', 'name'=>'end_date', 'placeholder' => '结束时间'],
            ],
        ];

        if($manage_id_list)
            $data['searcher'][] = ['type' => 'select', 'name'=>'staff_id', 'options' => \app\common\model\Staff::getKV([['id','in', $manage_id_list]]), 'placeholder' => '选择员工'];

        $this->assign('data', $data);
        return $this->fetch('public/table_builder');
    }
    function exportAttendance()
    {
        $list = Attendance::with('staff')->where('come_time','>', date('Y-m-d', strtotime('-3months')))->select();
        $data = [];
        foreach ($list as $v) {
            $data[] = [
                'staff_name' => $v->staff ? $v->staff['name'] : '',
                'come_time' => $v->come_time,
                'leave_time' => $v->leave_time,
                'be_late' => $v->be_late == 1 ? '是' : '否',
                'late_reason' => $v->late_reason,
                'leave_early' => $v->leave_early == 1 ? '是' : '否',
                'leave_reason' => $v->leave_reason,
                'verifier_name' => $v->verifier_name,
                'verify_remark' => $v->verify_remark,
            ];
        }
        C_exportExcel($data, [
            'staff_name' => '姓名',
            'come_time' => '上班打卡',
            'leave_time' => '下班打卡时间',
            'be_late' => '是否迟到',
            'late_reason' => '迟到原因',
            'leave_early' => '是否早退',
            'leave_reason' => '早退原因',
            'verifier_name' => '审核人',
            'verify_remark' => '审核说明',
        ],[10,20,20,20,20,20,20,20,20]);
    }
    // 编辑
    function attendanceApply()
    {
        $type = input('type');
        if (request()->isPost()) {
            $data = input('post.');
            if($type == 'late_apply') {
                $remark = '迟到补签';
                $data['be_late'] = 0;
                if(!$data['late_reason']) $this->error('缺少原因');
            } elseif ($type == 'leave_apply') {
                $remark = '早退补签';
                $data['leave_early'] = 0;
                if(!$data['leave_reason']) $this->error('缺少原因');
            } elseif ($type == 'late_verify') {
                $remark = '迟到补签审核';
                $data['be_late'] = -1;
                $data['verifier_name'] = session('login_name');
                if(!$data['verify_remark']) $this->error('缺少内容');
            } else {
                $remark = '早退补签审核';
                $data['leave_early'] = -1;
                $data['verifier_name'] = session('login_name');
                if(!$data['verify_remark']) $this->error('缺少内容');
            }
            $this->modelUpdate(AttendanceModel::class, $data, ['id' => $data['id']], '', $remark);
        } else {
            if($type == 'late_apply') {
                $act = 'makeFromData1';
            } elseif ($type == 'leave_apply') {
                $act = 'makeFromData2';
            } else {
                $act = 'makeFromData3';
            }
            return $this->fetchFormPageHtml(AttendanceModel::class, url(__FUNCTION__, ['type' => $type]), [], $act);
        }
    }

    function offdutyType()
    {
        $where = [];
        $this->assign('data', [
            'title' => '请假类型管理',
            'collection' => OffdutyTypeModel::where($where)->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => [
                ['ID', ['style' => 'width:80px']],
                '名称'],
            'fields' => ['id', 'name'],
            'buttons' => [
                ['title' => '编辑', 'url' => url('saveOffdutyType'),'permission_id' => 155],
                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('delOffdutyType'),'permission_id' => 156],
            ],
            'mbuttons' => [
                ['title' => '添加', 'url' => url('saveOffdutyType'),'permission_id' => 155],
            ],
            'searcher' => [
            ],
        ]);
        return $this->fetch('public/table_builder');
    }


    // 编辑
    function saveOffdutyType()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if ($id = input('id')) {
                $this->modelUpdate(OffdutyTypeModel::class, $data, ['id' => $data['id']], 'only_name', '修改请假类型');
            } else {
                $this->modelCreate(OffdutyTypeModel::class, $data, 'only_name', '添加请假类型');
            }
        } else {
            return $this->fetchFormPageHtml(OffdutyTypeModel::class, __FUNCTION__);
        }
    }

    function delOffdutyType()
    {
        $id = input('id/d');
        echo OffdutyTypeModel::destroy($id) ? 1 : 0;
    }

    function payoutType()
    {
        $where = [];
        $this->assign('data', [
            'title' => '请款类型管理',
            'collection' => PayoutTypeModel::where($where)->paginate(config('paginate.per_page'), false, ['query' => $this->request->param()]),
            'thead' => [
                ['ID', ['style' => 'width:80px']],
                '名称'],
            'fields' => ['id', 'name'],
            'buttons' => [
                ['title' => '编辑', 'url' => url('savePayoutType'),'permission_id' => 157],
                ['title' => '删除', 'onclick' => 'delOne', 'url' => url('delPayoutType'),'permission_id' => 158],
            ],
            'mbuttons' => [
                ['title' => '添加', 'url' => url('savePayoutType'),'permission_id' => 157],
            ],
            'searcher' => [
            ],
        ]);
        return $this->fetch('public/table_builder');
    }

    // 编辑
    function savePayoutType()
    {
        if (request()->isPost()) {
            $data = input('post.');
            if ($id = input('id')) {
                $this->modelUpdate(PayoutTypeModel::class, $data, ['id' => $data['id']], 'only_name', '修改请款类型');
            } else {
                $this->modelCreate(PayoutTypeModel::class, $data, 'only_name', '添加请款类型');
            }
        } else {
            return $this->fetchFormPageHtml(PayoutTypeModel::class, __FUNCTION__);
        }
    }

    function delPayoutType()
    {
        $id = input('id/d');
        echo PayoutTypeModel::destroy($id) ? 1 : 0;
    }
}