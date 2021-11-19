package com.hzb.erp.utils;

import lombok.Getter;

@Getter
public enum ResponseCodeEnums {

    /**
     * 返回结果枚举
     */
    SUCCESS(1000, "操作成功"),
    LOGIN_SUCCESS(1001, "注销成功"),
    LOGOUT_SUCCESS(1002, "注销成功"),

    BIZ_ERROR(2000, "业务异常"),
    // 大于2000 小于2100 的需要前端重新登录,
    TOKEN_NOTFOUND(2001, "用户未登录"),
    TOKEN_EXPIRED(2002, "登录状态丢失，请重新登录"),
    LOGIN_ERROR(2003, "验证登录失败"),

    FORBIDDEN(2100, "权限不足"),
    BLANK_PARAMS_ERROR(2101, "空参数"),
    PARAMS_FORMAT_ERROR(2102, "参数格式错误"),

//    WX_REQUIRE_BIND_STUDENT(2150, "该微信未绑定学员，请绑定"),
//    WX_UNKNOWN_USER(2151, "未获取到微信用户信息"),

    SYSTEM_ERROR(2200, "系统错误");

    private int code;

    private String msg;

    private ResponseCodeEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
