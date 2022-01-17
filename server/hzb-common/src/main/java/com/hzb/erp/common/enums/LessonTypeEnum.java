package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 课程类型
 */
@Getter
@AllArgsConstructor
public enum LessonTypeEnum implements BaseEnum {
    BIG(1, "大班课"),
    SMALL(2, "小班课"),
    VIP(3, "一对一");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
