package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * 班级类型
 * */
@Getter
@AllArgsConstructor
public enum ClassTypeEnum implements BaseEnum {
    NORMAL(1, "普通班"),
    ONE2ONE(2, "一对一");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
