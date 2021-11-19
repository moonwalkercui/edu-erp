package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 时间筛选用
 */
@Getter
@AllArgsConstructor
public enum DateRangeNameEnum implements BaseEnum {
    WEEK(1, "本周"),
    LAST_WEEK(2, "上周"),
    MONTH(3, "本月"),
    LAST_MONTH(4, "上个月"),
    NEXT_MONTH(5, "下个月"),
    SEASON(6, "本季度"),
    YEAR(7, "本年");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
