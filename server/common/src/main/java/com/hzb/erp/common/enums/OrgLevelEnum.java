package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公司类型 级别 1集团2公司3部门
 * */
@Getter
@AllArgsConstructor
public enum OrgLevelEnum implements BaseEnum {
    GROUP(1, "集团"),
    COM(2, "分校"),
    DPT(3, "部门");

    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
