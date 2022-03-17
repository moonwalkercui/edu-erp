package com.hzb.erp.service.notification;

import com.hzb.erp.service.notification.bo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ryan
 */

@Getter
@AllArgsConstructor
public enum NoticeCodeEnum {

    /**
     * 学员上课提醒
     */
    STUDENT_LESSON_START("studentLessonStart", LessonStartBO.class),
    /**
     * 学员签到与点名通知
     */
    STUDENT_SIGN("studentSign", StudentSignBO.class),
    /**
     * 课次状态变更通知给学员
     */
    STUDENT_LESSON_ONCHANGE("studentLessonOnChange", LessonChangeBO.class),
    /**
     * 报名通知
     */
    STUDENT_NEW_CONTRACT("studentNewContract", NewContractBO.class),
    /**
     * 缴费预警通知
     */
    STUDENT_LESSON_COUNT_LESS("studentLessonCountLess", LessonNotEnoughBO.class),
    /**
     * 老师上课提醒
     */
    TEACHER_LESSON_START("teacherLessonStart", LessonStartBO.class),
    /**
     * 学员请假提醒
     */
    TEACHER_STUDENT_LEAVE("teacherStudentLeave", StudentLeaveBO.class),
    /**
     * 学员课次不足提醒
     */
    TEACHER_STUDENT_LESSONLESS("teacherStudentLessonLess", LessonNotEnoughBO.class),
    /**
     * 课次状态变更通知给老师
     */
    TEACHER_LESSON_ONCHANGE("teacherLessonOnChange", LessonChangeBO.class);

    private final String code;
    private final Class<? extends NoticeBO> bo;
}
