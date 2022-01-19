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
public enum ContactStageEnum implements BaseEnum {
    TARGET(10, "目标客户阶段"),
    POTENTIAL(20, "潜在客户阶段"),
    INTENTION(30, "意向阶段"),
    ACCEPT(40, "认可阶段"),
    SUCCESS(50, "签约阶段"),
    AFTER_SALE(60, "售后阶段"),
    LOSE(0, "丢失阶段");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
