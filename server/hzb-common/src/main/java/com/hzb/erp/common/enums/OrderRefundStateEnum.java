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
    PASS(CommonConst.VERIFY_PASS_CODE, "已审核通过"),
    REJECT(CommonConst.VERIFY_REJECT_CODE, "退款驳回"),
    SUCCESS(CommonConst.SUCCESS_CODE, "已退款成功"),
    FAIL(CommonConst.FAIL_CODE, "退款失败");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
