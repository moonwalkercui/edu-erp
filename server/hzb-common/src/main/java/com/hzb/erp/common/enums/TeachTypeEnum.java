package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * 授课方式
 * */
@Getter
@AllArgsConstructor
public enum TeachTypeEnum implements BaseEnum {
    OFFLINE(1, "面授"),
    ONLINE(2, "网课");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
