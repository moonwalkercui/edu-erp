package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 联系方式
 */
@Getter
@AllArgsConstructor
public enum ContactTypeEnum implements BaseEnum {
    FACE(1, "面谈"),
    PHONE(2, "电话联系"),
    ONLINE(3, "在线沟通"),
    OTHER(4, "其他");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
