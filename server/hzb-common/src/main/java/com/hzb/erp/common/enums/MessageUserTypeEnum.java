package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * 消息相关用户身份
 * */
@Getter
@AllArgsConstructor
public enum MessageUserTypeEnum implements BaseEnum {
    ALL(-1, "所有人接收"),
    SYS(0, "系统消息"),
    STAFF(1, "员工消息"),
    STUDENT(2, "学生消息");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
