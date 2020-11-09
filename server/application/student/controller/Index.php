<?php

namespace app\student\controller;

use app\common\model\Clazz;
use app\common\model\Student;
use app\common\model\WxAccess;
use app\common\model\ZoneTask;
use app\common\service\Sms;
use app\common\service\WxService;

class Index extends Base
{
    function home()
    {
        $wx_access_id = session('wx_access_id');
        if (!$wx_access_id) return $this->errorJson('请从微信公众号底部菜单登录');

        if ($this->checkLogin() == false) return $this->errorJson('未绑定学生',[],2);
        $student_id = session('student_id');

        $data['user_info'] = WxAccess::field('nickname,headimg')->find($wx_access_id);

        $student = Student::find($student_id);
        $where[] = ['clazz_id', '=', $student['clazz_id']];
        $got_ids = ZoneTask::getTaskIdsByStudentId($student_id);
        $data['task_num'] = \app\common\model\ZoneTask::where($where)->where('id','not in', $got_ids)->count();
        $data['user_info'] = [
            'avatar' => WxAccess::where('id', $wx_access_id)->value('headimg'),
            'name' => Student::where('id', $student_id)->value('name'),
        ];
        return $this->dataJson( $data );
    }
    // 绑定学员
    function bind()
    {
        $wx_access_id = session('wx_access_id');
        if (!$wx_access_id) return $this->errorJson('请从微信公众号底部菜单登录');

        $mobile = input('post.mobile');
        if (!$mobile) return $this->errorJson('缺少手机号');
        $code = input('post.code');
        if (!$code) return $this->errorJson('缺少短信验证码');
        $clazz_name = input('post.clazz');
        if (!$clazz_name) return $this->errorJson('请选择班级');
        $name = input('post.name');
        if (!$name) return $this->errorJson('缺少学生姓名');
        $password = input('post.password');
        if (!$password) return $this->errorJson('缺少密码，请联系负责老师获取');

        $type = input('post.type'); // 1'学生' 2'家长'
        $check = Sms::checkMobileCode($mobile, $code);
        if ($check == false) return $this->errorJson('验证码错误, 请重新获取');

//        $exist_mob = Student::where(compact('mobile'))->find();
//        if ($exist_mob)
//            return $this->errorJson('该手机号已使用绑定过学员，不能重复绑定');

        $clazz_id = Clazz::where('name', $clazz_name)->value('id');
        $student = Student::where(compact('name','clazz_id'))->find();
        if (!$student)
            return $this->errorJson($name.'不是有效学员，请联系负责老师');

        if($student['password'] != $password)
            return $this->errorJson('密码有误，请联系负责老师');

        $student->mobile = $mobile;
        $student->save();

        if (WxAccess::bindStudent($wx_access_id, $student['id'], $type)) {
            session('student_id', $student['id']);
            session("student_name", $student['name']);
            return $this->successJson('绑定成功');
        } else {
            return $this->errorJson('绑定错误');
        }
    }
    function clazzList()
    {
        $data['role'] = array_values(config('status.role'));
        $data['clazz'] = Clazz::column('name');
        return $this->dataJson($data);
    }
}