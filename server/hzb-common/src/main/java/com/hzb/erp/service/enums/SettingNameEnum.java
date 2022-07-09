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
    CLASS_CLOSE_WITH_GRADUATE_STUDENT("class_close_with_graduate_student", "班级结业时班内学员自动结业", "true", "bool"),

    /**
     * 报名1V1课程时自动生成班级
     */
    CLASS_AUTO_CREATE_ON_ONE2ONE("class_auto_create_on_one2one", "报名1V1课程时自动生成班级", "true", "bool"),

    /**
     * 课次数不足预警数量
     */
    LESSON_COUNT_LESS_WARNING_COUNT("lesson_count_less_warning_count", "课次数不足预警数量", "5", "int"),

    /**
     * 课次数不足提醒次数
     */
    LESSON_COUNT_LESS_WARNING_TIMES("lesson_count_less_warning_times", "课次数不足提醒次数", "2", "int"),

    /**
     * 家长端初始密码
     */
    STUDENT_DEFAULT_PWD("student_default_pwd", "家长端初始密码", "1234567", "str"),

    /**
     * 短信接口KEY
     */
    SMS_ACCESS_KEY("sms_access_key", "短信接口KEY", "", "str"),

    /**
     * 短信接口秘钥
     */
    SMS_ACCESS_SECRET("sms_access_secret", "短信接口秘钥", "", "str"),

    /**
     * 短信签名
     */
    SMS_SIGN_NAME("sms_sign_name", "短信签名", "", "str"),

    /**
     * 验证码短信模板ID
     */
    SMS_CODE_TEMPLATE("sms_code_template", "验证码短信模板ID", "", "str"),

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
     * 微信支付相关
     */
    WX_PAY_MCHID("wx_pay_mchid", "微信支付商户号", "", "str"),
    WX_PAY_MCHKEY("wx_pay_mchkey", "微信支付商户密钥", "", "str"),
    WX_PAY_SUBAPPID("wx_pay_subappid", "服务商模式子商户公众账号ID", "", "str"),
    WX_PAY_SUBMCHID("wx_pay_submchid", "服务商模式子商户号", "", "str"),
    WX_PAY_KEYPATH("wx_pay_keypath", "apiclient_cert.p12文件的绝对路径", "", "str"),

    /**
     * 每天上课提醒时间
     */
    LESSON_REMIND_TIME("lesson_remind_time", "每天上课提醒时间", "19:31", "time"),

    /**
     * 每天上课次数预警时间
     */
    LESSON_COUNT_WARNING_TIME("lesson_count_warning_time", "每天上课次数预警时间", "07:01", "time"),

    /**
     * 学生端开启签到功能
     */
    STUDENT_CENTER_CAN_SIGN("student_center_can_sign", "学生端开启签到功能", "false", "bool"),


    /**
     * 学员预约后自动加入到课时
     */
    AUTO_JOIN_LESSON_BY_APPOINTMENT("auto_join_lesson_by_appointment", "学员预约后自动加入到课时", "true", "bool"),

    /**
     * 体验卡使用规则说明
     */
    COURSE_TRIAL_HELP("course_trial_help", "体验卡使用规则说明", "领取体验卡后可以到课时表里预约课程。", "str"),


    /**
     * 在线购课支付后可申请退款的小时数
     */
    ORDER_REFUND_LIMIT_HOUR("order_refund_limit_hour", "在线支付后可申请退款的小时数(0表示不限制)", "24", "int"),

    /**
     * 新订单默认提醒手机号
     */
    ORDER_NOTICE_MOBILE("order_notice_mobile", "学员在线购课后，若无顾问则通知该账号", "", "str");

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
