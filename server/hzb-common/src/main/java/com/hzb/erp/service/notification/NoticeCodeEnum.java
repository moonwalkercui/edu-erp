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

    STUDENT_LESSON_START("studentLessonStart", LessonStartBO.class),
    STUDENT_SIGN("studentSign", StudentSignBO.class),
    STUDENT_LESSON_ONCHANGE("studentLessonOnChange", LessonChangeBO.class),
    STUDENT_NEW_CONTRACT("studentNewContract", NewContractBO.class),
    STUDENT_LESSON_COUNT_LESS("studentLessonCountLess", LessonNotEnoughBO.class),
    TEACHER_LESSON_START("teacherLessonStart", LessonStartBO.class),
    TEACHER_STUDENT_LEAVE("teacherStudentLeave", StudentLeaveBO.class),
    TEACHER_STUDENT_LESSONLESS("teacherStudentLessonLess", LessonNotEnoughBO.class),
    TEACHER_LESSON_ONCHANGE("teacherLessonOnChange", LessonChangeBO.class);

    private final String code;
    private final Class<? extends NoticeBO> bo;
}
