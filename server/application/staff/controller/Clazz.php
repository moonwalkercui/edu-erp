<?php

namespace app\staff\controller;

use app\common\model\Course;
use app\common\model\Staff;
use app\common\model\Zone;
use app\common\model\ZoneLike;

class Clazz extends Base
{
    function index()
    {
//        1，基本信息，包含班级名称，学生数量，负责老师，
//2，班级动态（几月几日第几节课xxx老师给学生们上了一节xxx课程（点击查看课时详情）；几月几日xxx老师给学生们进行了xxx考试（点击查看试卷详情页）；几月几日xxx老师上传了精彩媒体（点击查看媒体页面）；几月几日xxx老师更新了积分（点击查看积分列表）。
//3，本班课程（显示跟本班级所有相关的课时列表以及上课时间，已经上过的课程后面跟着按钮【复习】，还没上的课程跟着按钮【预习】，点击都是打开课时关联的知识点讲解；点击每条课表可以打开课时详情页）
//4，成绩排行（展示本班的最近一次测试正确率排名前10的同学，点击查看所有学生的最近一次测试成绩列表）
//5，积分排行（展示积分排名前十的同学，点击查看积分列表）
//6，精彩瞬间（媒体缩略图平铺，每张缩略图下带简要描述）。

//        $id = input('id');
//        $id = 1;
//        $clazz = \app\common\model\Clazz::find(1);
//        $data['clazz_name'] = $clazz['name'];
//        $data['student_count'] = $clazz->student()->count();
//        $data['teachers'] = Staff::where('id', 'in', $clazz['staff_in_charge'])->field('id,name,photo');


        return $this->fetch();
    }

    function getList()
    {
        if ($this->checkLogin() == false) return $this->errorJson('未登录',[],2);
        $staff_id = session('login_id');
        $data = \app\common\model\Clazz::getByStaffId($staff_id);
        foreach ($data as &$v) {
            $v['count'] = Zone::where('clazz_id', $v['id'])->where('status', 0)->count();
        }
        return $this->dataJson($data);
    }

}