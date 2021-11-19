package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公司类型 类型0本公司1货代公司2船务公司3贸易公司
 * */
@Getter
@AllArgsConstructor
public enum OrgTypeEnum implements BaseEnum {
    DEFAULT(0, "本机构"),
    HD(1, "货代公司"),
    CW(2, "船务公司"),
    MY(3, "贸易公司");

    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
