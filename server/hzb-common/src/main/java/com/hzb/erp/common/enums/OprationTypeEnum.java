package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 箱型
 *
 * @author 541720500@qq.com
 */

@Getter
@AllArgsConstructor
public enum OprationTypeEnum implements BaseEnum {
    COURSE(1, "课程"),
    StudentCourse(2, "报名"),
    Refund(3, "退款退课"),
    CASHOUT(4, "请款"),
    ORDER(5, "购课订单"),
    ORDER_REFUND(6, "订单退款");

    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
