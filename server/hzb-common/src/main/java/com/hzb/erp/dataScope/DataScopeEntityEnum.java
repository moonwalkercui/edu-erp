package com.hzb.erp.dataScope;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限类型
 */
@Getter
@AllArgsConstructor
public enum DataScopeEntityEnum {

    STUDENT("student", "学生表", "counselor"),
    COURSE("course", "课程表", "creator"),
    CLASS("class", "班级表", "teacher_id"),
    LESSON("lesson", "课次表", "creator"),
    STUDENT_COURSE("student_course", "报名表", "creator"),
    CONTACT_RECORD("contact_record", "跟进记录表", "creator"),
    LESSON_SCHEDULE("lesson_schedule", "排课计划表", "creator"),
    HOMEWORK("homework", "作业表", "creator"),
    STAFF("staff", "员工账号表", "id"),
    TEACH_EVALUATION("teach_evaluation", "学评教表", "teacher_id"),
    CASHOUT("cashout", "请款表", "creator"),
    GRADE("grade", "成绩表", "creator"),
    FINANCE_RECORD("finance_record", "财务认款表", "operator"),
    SYS_LOG("sys_log", "系统日志表", "operator"),
    HOLIDAY("holiday", "节假日表", "creator"),
    LESSON_STUDENT("lesson_student", "教评学", "counselor"),
    OPERATION_RECORD("operation_record", "操作记录表", "creator");

    /**
     * 数据表名
     */
    private final String code;

    /**
     * 描述
     */
    private final String dist;

    /**
     * 表中数据默认负责人字段,会记录到DataPermission实体的ownerField属性里
     */
    private final String defaultField;
}
