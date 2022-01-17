package com.hzb.erp.security.Enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hzb.erp.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> 登录用户身份 </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Getter
@AllArgsConstructor
public enum LoginUserIdentity implements BaseEnum {
    STAFF(1, "员工"),
    USER(2, "家长");

    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
