package com.hzb.erp.service.enums;

import lombok.Getter;

/**
 * 配置组
 */
@Getter
public enum SettingGroupEnum {

    NORMAL_SETTING("normal_setting", "系统设置"),
    SMS_SETTING("sms_setting", "短信设置"),
    WX_MP_SETTING("wx_mp_setting", "微信公众号设置");

    private String code;
    private String desc;

    SettingGroupEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
