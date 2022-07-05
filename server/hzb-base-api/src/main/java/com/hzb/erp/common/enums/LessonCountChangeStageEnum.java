package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hzb.erp.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 课次变动记录阶段
 */
@Getter
@AllArgsConstructor
public enum LessonCountChangeStageEnum implements BaseEnum {
    CONTRACT(1, "课程报名"),
    LESSON_DEC(2, "上课消课"),
    ADMIN(3, "后台操作"),
    REFUND(4, "退课"),
    REFUND_REJECT(5, "退课驳回"),
    ROLLBACK(6, "取消消课");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
