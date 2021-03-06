package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hzb.erp.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态
 * @author cui
 */
@Getter
@AllArgsConstructor
public enum OrderStateEnum implements BaseEnum {
    UNPAID(0, "未支付"),
    PAID(1, "已付款"),
    EVALUATE(10, "已评价"),
    CANCELED(-1, "已取消");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
