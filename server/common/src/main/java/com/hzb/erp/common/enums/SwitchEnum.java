package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 开关
 *
 * @author Ryan
 */

@Getter
@AllArgsConstructor
public enum SwitchEnum implements BaseEnum {
    YES(1, "启用"),
    NO(0, "禁用");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
