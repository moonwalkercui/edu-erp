package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hzb.erp.common.constants.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 财务认款状态类型
 */
@Getter
@AllArgsConstructor
public enum FinanceStateEnum implements BaseEnum {

    /**
     * 注意该枚举各项和必须和 VerifyStateEnum 的code值一致.
     */
    APPLY(CommonConst.VERIFY_READY_CODE, "认款中"),
    PASS(CommonConst.VERIFY_PASS_CODE, "已认款"),
    REJECT(CommonConst.VERIFY_REJECT_CODE, "已拒绝");

    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
