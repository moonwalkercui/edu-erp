package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 财务收支款项类型
 */
@Getter
@AllArgsConstructor
public enum FinanceTypeEnum implements BaseEnum {
    COURSE(1, "课程报名"),
    SUPPLEMENT(2, "课费补缴"),
    //    RECHARGE(3, "课程续费"),
    REFUND(10, "课程退款");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
