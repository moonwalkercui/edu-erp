package com.hzb.erp.common.enums;

import lombok.AllArgsConstructor;

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
