package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 学生积分变动类型
 * */
@Getter
@AllArgsConstructor
public enum StudentCreditChangeTypeEnum implements BaseEnum {
    LESSON_EVALUATE(1, "课后点评奖励"),
    CREDIT_EXCHANGE(2, "积分商城兑换礼品消费"),
    CREDIT_EXCHANGE_FAIL(3, "积分商城兑换失败返还"),
    /**
     * 管理端奖励
     */
    REWARD(4, "奖励积分");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
