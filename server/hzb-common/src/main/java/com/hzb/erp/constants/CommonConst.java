package com.hzb.erp.constants;

/**
 * @author Ryan
 * description  通用全局变量
 */
public class CommonConst {
    /**
     * 通讯token名称
     */
    public static final String DEFAULT_TOKEN_NAME = "access-token";
    public static final String WX_ACCESS_ID = "wxaccess";

    public static final String JWT_CLAIMS_KEY = "userDetails";

    /**
     * 机构id_path链接间隔符号
     */
    public static final String ORG_ID_PATH_SEPARATOR = ",";
    /**
     * 机构name_path链接间隔符号
     */
    public static final String ORG_NAME_PATH_SEPARATOR = " ";

    public static final int VERIFY_REJECT_CODE = -1;
    public static final int VERIFY_READY_CODE = 1;
    public static final int VERIFY_PASS_CODE = 2;
    public static final int SUCCESS_CODE = 100;
    public static final int FAIL_CODE = 101;


    public static final String TEACHER_IDENTITY_NAME = "teacher";
    public static final String STUDENT_IDENTITY_NAME = "student";
    public static final Long STUDENT_ROLE_ID = -1L;
    /**
     * 主动阻止请求的id
     */
    public static final Long ACCESS_DENIED_ID = 0L;
    public static final String STAFF_LOGIN_USERNAME = "username";
    public static final String STAFF_LOGIN_PASSWORD = "password";
    public static final String STUDENT_LOGIN_USERNAME = "username";
    public static final String STUDENT_LOGIN_PASSWORD = "password";

}