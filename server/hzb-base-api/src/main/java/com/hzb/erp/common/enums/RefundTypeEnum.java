package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hzb.erp.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 退款类型
 *
 * @author cui
 */
@Getter
@AllArgsConstructor
public enum RefundTypeEnum implements BaseEnum {
    COURSE(1, "课程退费"),
    OTHER(2, "其他退费");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
