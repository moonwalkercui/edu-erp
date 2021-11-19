package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 学历
 */
@Getter
@AllArgsConstructor
public enum DegreeEnum implements BaseEnum {
    DOCTOR(7, "博士及以上"),
    MASTER(6, "硕士"),
    BACHELOR(5, "本科"),
    DIPLOMA(4, "专科"),
    TECHNICAL(3, "中专"),
    SENIOR(2, "高中"),
    JUNIOR(1, "初中及以下"),
    UNKNOWN(0, "未知");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
