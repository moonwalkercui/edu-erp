package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * 签到状态
 * */
@Getter
@AllArgsConstructor
public enum SignStateEnum implements BaseEnum {
    /**
     * 默认为未到课
     */
    NONE(0, "未到课"),
    NORMAL(1, "出勤"),
    LATE(2, "迟到"),
    LEAVE(3, "请假");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
