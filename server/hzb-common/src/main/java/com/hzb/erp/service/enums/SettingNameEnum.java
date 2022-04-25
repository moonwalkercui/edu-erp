package com.hzb.erp.service.enums;

import lombok.Getter;

/**
 * 配置名枚举
 * 系统在setting_options表里增加配置，需要更新该文件
 */
@Getter
public enum SettingNameEnum {

    /**
     * 班级结业时班内学员自动结业
     */
    CLASS_CLOSE_WITH_GRADUATE_STUDENT("class_close_with_graduate_student", "班级结业时班内学员自动结业", "", "str"),

    /**
     * 报名1V1课程时自动生成班级
     */
    CLASS_AUTO_CREATE_ON_ONE2ONE("class_auto_create_on_one2one", "报名1V1课程时自动生成班级", "", "str"),

    /**
     * 课次数不足预警数量
     */
    LESSON_COUNT_LESS_WARNING_COUNT("lesson_count_less_warning_count", "课次数不足预警数量", "", "str"),

    /**
     * 课次数不足提醒次数
     */
    LESSON_COUNT_LESS_WARNING_TIMES("lesson_count_less_warning_times", "课次数不足提醒次数", "", "str"),

    /**
     * 家长端初始密码
     */
    STUDENT_DEFAULT_PWD("student_default_pwd", "家长端初始密码", "", "str"),

    /**
     * 课次数不足预警数量
     */
    SMS_ACCESS_KEY("sms_access_key", "课次数不足预警数量", "", "str"),

    /**
     * 课次数不足预警数量
     */
    SMS_ACCESS_SECRET("sms_access_secret", "课次数不足预警数量", "", "str"),

    /**
     * 课次数不足预警数量
     */
    SMS_SIGN_NAME("sms_sign_name", "课次数不足预警数量", "", "str"),

    /**
     * 微信设置的code
     */
    SETTING_CODE_WX_MP("wx_mp_setting", "微信设置的code", "", "str"),

    /**
     * 公众号AppId
     */
    WX_MP_APP_ID("wx_mp_app_id", "公众号AppId", "", "str"),

    /**
     * 公众号Secret
     */
    WX_MP_SECRET("wx_mp_secret", "公众号Secret", "", "str"),

    /**
     * 公众号Token
     */
    WX_MP_TOKEN("wx_mp_token", "公众号Token", "", "str"),

    /**
     * 公众号AesKey
     */
    WX_MP_AES_KEY("wx_mp_aes_key", "公众号AesKey", "", "str"),

    /**
     * 上课提现时间
     */
    LESSON_REMIND_TIME("lesson_remind_time", "上课提现时间", "", "str"),

    /**
     * 上课次数预警时间
     */
    LESSON_COUNT_WARNING_TIME("lesson_count_warning_time", "上课次数预警时间", "", "str"),

    /**
     * 学员预约后自动加入到课时
     */
    AUTO_JOIN_LESSON_BY_APPOINTMENT("auto_join_lesson_by_appointment", "学员预约后自动加入到课时", "", "str");


    private String code;
    private String desc;
    private String defaultValue;
    private String valueType;

    SettingNameEnum(String code, String desc, String defaultValue, String valueType) {
        this.code = code;
        this.desc = desc;
        this.defaultValue = defaultValue;
        this.valueType = valueType;
    }
}
