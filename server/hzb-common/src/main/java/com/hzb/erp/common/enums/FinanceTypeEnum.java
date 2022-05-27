package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 财务收支款项类型
 * 规律：小于20的是收入项，大于20的是支出项
 */
@Getter
@AllArgsConstructor
public enum FinanceTypeEnum implements BaseEnum {
    COURSE(1, "课程报名"),
    SUPPLEMENT(2, "课费补缴"),
    ORDER(3, "购课订单"),
    //    RECHARGE(3, "课程续费"),
    REFUND(20, "课程退款"),
    ORDER_REFUND(21, "订单退费");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
