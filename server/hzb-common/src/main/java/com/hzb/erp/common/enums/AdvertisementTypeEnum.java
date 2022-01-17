package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公告类型
 */
@Getter
@AllArgsConstructor
public enum AdvertisementTypeEnum implements BaseEnum {
    TIP(1, "学生端首页提示"),
    BANNER(2, "学生端首页Banner"),
    ARTICLE(3, "普通文章");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
