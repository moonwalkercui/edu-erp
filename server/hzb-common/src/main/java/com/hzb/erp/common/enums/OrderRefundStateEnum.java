package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hzb.erp.common.constants.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 退款状态
 */
@Getter
@AllArgsConstructor
public enum OrderRefundStateEnum implements BaseEnum {
    APPLY(CommonConst.VERIFY_READY_CODE, "已申请"),
    SUCCESS(CommonConst.VERIFY_PASS_CODE, "已退款"),
    FAIL(CommonConst.VERIFY_REJECT_CODE, "退款失败");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
