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
public enum StudentLeaveSateEnum implements BaseEnum {
    SUCCESS(1, "已请假"),
    CANCELED(0, "已撤消");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
