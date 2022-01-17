package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * 签到状态
 * */
@Getter
@AllArgsConstructor
public enum SignTypeEnum implements BaseEnum {
    TEACHER(1, "老师点名"),
    STUDENT(2, "学员端签到");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
