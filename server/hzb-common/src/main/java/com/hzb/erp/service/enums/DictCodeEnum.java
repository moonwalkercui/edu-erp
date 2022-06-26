package com.hzb.erp.service.enums;

import lombok.Getter;

/**
 * 字典CODE
 */
@Getter
public enum DictCodeEnum {

    CUSTOMER_SOURCE("customer_source", "客户来源"),
    CASHOUT_TYPE("cashout_type", "请款类型"),
    REFUND_TYPE("refund_type", "退款类型"),
    CREDIT_MALL_CATEGORY("credit_mall_category", "积分商城分类");

    private String code;
    private String desc;

    DictCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
