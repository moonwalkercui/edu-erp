package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hzb.erp.constants.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核状态
 */
@Getter
@AllArgsConstructor
public enum VerifyEnum implements BaseEnum {
    APPLY(CommonConst.VERIFY_READY_CODE, "待审核"), // 初始状态
    PASS(CommonConst.VERIFY_PASS_CODE, "审核通过"),
    REJECT(CommonConst.VERIFY_REJECT_CODE, "已驳回");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
