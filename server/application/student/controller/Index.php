<?php

namespace app\student\controller;

use app\common\model\Clazz;
use app\common\model\ClazzNotice;
use app\common\model\ClazzNoticeRead;
use app\common\model\Student;
use app\common\model\WxAccess;
use app\common\model\ZoneTask;
use app\common\service\Sms;
use app\common\service\WxService;
use utils\Time;

class Index extends Base
{
    function home()
    {

        if ($this->checkLogin() == false) return $this->errorJson('未绑定学生', [], 2);
        $student_id = session('student_id');
        $student = Student::find($student_id);
        $clazz_id = $student['clazz_id'];

//        $where[] = ['clazz_id', '=', $student['clazz_id']];
//        $got_ids = ZoneTask::getTaskIdsByStudentId($student_id);
//        $data['task_num'] = \app\common\model\ZoneTask::where('id','not in', $got_ids)->count();
        $data['task_num'] = 0;

        $data['media_num'] = \app\common\model\Media::where(function ($query) use ($clazz_id, $student_id) {
            $query->where('clazz_id', $clazz_id)->whereOr("FIND_IN_SET('{$student_id}', student_ids)");
        })->whereTime('add_time', '>', $student['last_media_viewtime'])->count();

        $data['zone_num'] = \app\common\model\Zone::where(function ($query) use ($clazz_id, $student_id) {
            $query->where(compact('clazz_id'))->whereOr(compact('student_id'));
        })->whereTime('add_time', '>', $student['last_zone_viewtime'])->count();

        $wx_info = WxAccess::field('nickname,headimg')->where('student_id', $student_id)->find();
        $student = Student::where('id', $student_id)->find();
        $data['user_info'] = [
            'avatar' => $wx_info ? $wx_info['headimg'] : '',
            'name' => $student['name'],
            'birthday' => $student['birthday'],
            'score' => $student['score'],
            'class' => $student['clazz_id'] ? Clazz::where('id', $student['clazz_id'])->value('name') : '',
        ];
        $data['notice'] = ClazzNotice::getOneBy(['clazz_id' => $clazz_id], 'id desc');
        return $this->dataJson($data);
    }
    function studentInfo()
    {
        $data = [];
        if ($this->checkLogin() == false) return $this->dataJson($data);
        $student_id = session('student_id');
        $data = Student::where('id', $student_id)->field('id,name,mobile,clazz_id,id_card,birthday,address,score,gender')->find();
        $data['type'] = WxAccess::where(compact('student_id'))->value('type');
        $data['clazz_name'] = Clazz::where('id', $data['clazz_id'])->value('name');

        return $this->dataJson($data);
    }
    // 绑定学员
    function bind()
    {
        $wx_access_id = session('wx_access_id');
        if (!$wx_access_id) return $this->errorJson('请从微信公众号底部菜单登录');

        $mobile = input('post.mobile');
        if (!$mobile) return $this->errorJson('缺少手机号');

        $clazz_name = input('post.clazz');
        if (!$clazz_name) return $this->errorJson('请选择班级');
        $name = input('post.name');
        if (!$name) return $this->errorJson('缺少学生姓名');
        $password = input('post.password');
        if (input('updating') != 1 && !$password) return $this->errorJson('缺少密码，请联系负责老师获取');

        $type = input('post.type'); // 1'学生' 2'家长'

        //        $code = input('post.code');
//        if (!$code) return $this->errorJson('缺少短信验证码');
//        $check = Sms::checkMobileCode($mobile, $code);
//        if ($check == false) return $this->errorJson('验证码错误, 请重新获取');

//        $exist_mob = Student::where(compact('mobile'))->find();
//        if ($exist_mob)
//            return $this->errorJson('该手机号已使用绑定过学员，不能重复绑定');

        $clazz = Clazz::where('name', $clazz_name)->find();
        if (!$clazz) return $this->errorJson('未知班级');
        $clazz_id = $clazz['id'];

        $student = Student::where(compact('name', 'clazz_id'))->find();
        if (input('updating') == 1) {
            // 更新
            if (!$student) {
                return $this->errorJson($name . '不是有效学员，请联系负责老师');
            }

//            $access_log = WxAccess::find($wx_access_id);
//            if($access_log['student_id'] != $student->id) {
//                return $this->errorJson('该微信已经绑定过学员，如需更换请联系负责老师.');
//            }

            $student->mobile = $mobile;
            $student->gender = input('gender');
            $student->id_card = input('id_card');
            $student->address = input('address');
            $student->birthday = input('birthday');

            if ($student->save()) {
                session('student_id', $student['id']);
                session("student_name", $student['name']);
                return $this->successJson('保存成功');
            } else {
                return $this->errorJson('保存错误');
            }
        } else {
            // 新注册
            if ($student) {
                return $this->errorJson($name . '已经注册过了，请联系负责老师.');
            }

            if ($clazz['password'] && $clazz['password'] != $password) {
                return $this->errorJson('班级密码有误，请联系负责老师');
            }

            $data = [
                'name' => $name,
                'clazz_id' => $clazz_id,
                'mobile' => $mobile,
                'id_card' => input('id_card'),
                'address' => input('address'),
                'gender' => input('gender'),
                'birthday' => input('birthday'),
                'status' => 1,
                'add_time' => now(),
            ];
            $student_id = Student::insertGetId($data);
            if ($student_id && WxAccess::bindStudent($wx_access_id, $student_id, $type)) {
                session('student_id', $student_id);
                session("student_name", $name);
                return $this->successJson('注册成功');
            } else {
                return $this->errorJson('注册错误');
            }
        }

    }

    function clazzList()
    {
        $data['role'] = array_values(config('status.role'));
        $data['clazz'] = Clazz::column('name');
        return $this->dataJson($data);
    }

    function scanLogin()
    {
        if (request()->isPost()) {
            $login_code = input('code');
            $wx_access = WxAccess::where(compact('login_code'))->find();
            if ($wx_access && $wx_access['student_id']) {
                session("student_id", $wx_access['student_id']);
                session("student_name", Student::where('id', $wx_access['student_id'])->value('name'));
                return $this->successJson('OK');
            } else
                return $this->errorJson('还未登录');
        } else {
            $code = make_sn();
            $url = request()->domain() . '/wxauth.html?state=scanlogin_' . $code;
            $this->assign('img_data', makeQRcode($url, 8));
            $this->assign('code', $code);
            return $this->fetch();
        }
    }

    // pc客户端入口
    function pcCli()
    {
        if (isMob()) {
            header('location:' . request()->domain() . '/wxauth.html?state=studentcenter');
        }
        return $this->fetch();
    }

    function clazzNotice()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);

        $where = [];
        $student_id = session('student_id');
        $student = Student::find($student_id);
        $clazz_id = $student['clazz_id'];
        $where[] = ['clazz_id', '=', $clazz_id];

        $list = \app\common\model\ClazzNotice::with('staff')
            ->where($where)
            ->order('id desc')
            ->paginate(10, false, ['query' => $this->request->param()])
            ->each(function ($item) {
                $item['be_read'] = ClazzNoticeRead::checkRead($item->id, session('student_id')) ? 1 : 0;
            });
        return $this->dataJson($list);
    }

    function clazzNoticeDetail()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录', [], 2);
        \app\common\model\ClazzNotice::handleRead(input('id'), session('student_id'));
        $data = \app\common\model\ClazzNotice::with('staff')->find(input('id'));
        return $this->dataJson($data);
    }

    function player() {
        return $this->fetch();
    }
}