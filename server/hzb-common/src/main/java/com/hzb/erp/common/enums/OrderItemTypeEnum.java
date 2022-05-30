package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单购买物品类型
 * @author cui
 */
@Getter
@AllArgsConstructor
public enum OrderItemTypeEnum implements BaseEnum {
    COURSE(1, "课程"),
    OTHER(0, "其他");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
