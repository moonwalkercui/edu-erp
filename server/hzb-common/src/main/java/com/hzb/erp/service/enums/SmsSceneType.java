package com.hzb.erp.service.enums;

import lombok.Getter;

/**
 * 图片验证码业务类型
 */
@Getter
public enum SmsSceneType {

    /**
     * 发送验证码
     */
    STUDENT_LOGIN("student_login", "学生端验证码登录"),
    STUDENT_REGISTER("student_register", "学生注册验证码"),
    STUDENT_FORGETPW("student_forgetpw", "学生找回密码"),
    /**
     * 以下是系统发送的。与表setting_notice的code对应。
     */
    STUDENT_LESSON_START("studentLessonStart", "学员上课提醒"),
    STUDENT_SIGN("studentSign", "学员签到与点名通知"),
    STUDENT_LESSON_ONCHANGE("studentLessonOnChange", "课次状态变更通知给学员"),
    STUDENT_NEW_CONTRACT("studentNewContract", "报名通知"),
    STUDENT_LESSON_COUNTLESS("studentLessonCountLess", "缴费预警通知"),
    TEACHER_LESSON_START("teacherLessonStart", "老师上课提醒"),
    TEACHER_STUDENT_LEAVE("teacherStudentLeave", "学员请假提醒"),
    TEACHER_STUDENT_LESSON_LESS("teacherStudentLessonLess", "学员课次不足提醒"),
    TEACHER_LESSON_ONCHANGE("teacherLessonOnChange", "课次状态变更通知给老师");

    private String code;
    private String desc;

    SmsSceneType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
