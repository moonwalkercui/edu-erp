package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设置表code
 * @author cui
 */
@AllArgsConstructor
public enum SettingCodeEnum {
    /**
     * 系统设置
     * */
    NORMAL_SETTING,
    /**
     * 短信设置
     * */
    SMS_SETTING,
    /**
     * 微信公众号设置
     * */
    WX_MP_SETTING;
}
