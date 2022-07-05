package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hzb.erp.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 星期
 *
 * @author cui
 */
@Getter
@AllArgsConstructor
public enum WeekdaysEnum implements BaseEnum {
    MON(1, "一"),
    TUE(2, "二"),
    WED(3, "三"),
    THU(4, "四"),
    FRI(5, "五"),
    SAT(6, "六"),
    SUN(7, "日");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
