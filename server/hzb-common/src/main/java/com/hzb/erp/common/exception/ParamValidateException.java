package com.hzb.erp.common.exception;

import com.hzb.erp.utils.ResponseCodeEnums;
import lombok.Getter;

/**
 * 参数验证异常类
 *
 * @author Ryan
 */
@Getter
public class ParamValidateException extends RuntimeException {
    private final Integer code;

    /**
     * 使用已有的错误类型
     *
     * @param type 枚举类中的错误类型
     */
    public ParamValidateException(ResponseCodeEnums type) {
        super(type.getMsg());
        this.code = type.getCode();
    }

    /**
     * 自定义错误类型
     *
     * @param code 自定义的错误码
     * @param msg  自定义的错误提示
     */
    public ParamValidateException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 自定义错误类型
     *
     * @param msg 自定义的错误提示
     */
    public ParamValidateException(String msg) {
        super(msg);
        this.code = ResponseCodeEnums.BIZ_ERROR.getCode();
    }
}