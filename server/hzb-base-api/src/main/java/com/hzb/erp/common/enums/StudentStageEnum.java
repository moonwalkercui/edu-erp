package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hzb.erp.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * 学生阶段
 * */
@Getter
@AllArgsConstructor
public enum StudentStageEnum implements BaseEnum {
    INTENTION(1, "意向学员"),
    STUDYING(2, "在学学员"),
    GRADUATION(3, "结业学员");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
