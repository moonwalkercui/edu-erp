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
    POPUP(0, "学生端首页弹出"),
    TIP(1, "学生端首页公告"),
    BANNER(2, "学生端首页Banner"),
    ARTICLE(3, "普通公告");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
