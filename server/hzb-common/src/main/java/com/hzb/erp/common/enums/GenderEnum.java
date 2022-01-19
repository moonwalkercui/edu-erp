package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * 性别
 * */
@Getter
@AllArgsConstructor
public enum GenderEnum implements BaseEnum {
    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
