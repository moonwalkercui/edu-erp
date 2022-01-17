package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 员工状态
 *
 * @author cui
 */
@Getter
@AllArgsConstructor
public enum StaffStateEnum implements BaseEnum {
    ON_JOB(1, "在职"),
    DIMISSION(0, "离职");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
