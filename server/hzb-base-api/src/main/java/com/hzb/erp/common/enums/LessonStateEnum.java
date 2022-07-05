package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hzb.erp.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 课次状态
 */

@Getter
@AllArgsConstructor
public enum LessonStateEnum implements BaseEnum {
    STOPPED(0, "已停课"),
    UNDERWAY(1, "进行中"),
    COMPLETE(2, "已结课");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
