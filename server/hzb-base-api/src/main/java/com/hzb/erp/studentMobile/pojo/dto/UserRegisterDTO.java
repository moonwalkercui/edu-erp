package com.hzb.erp.studentMobile.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Ryan 541720500@qq.com
 * 学员端注册
 */
@Data
public class UserRegisterDTO {

    @NotBlank(message = "缺少称呼")
    private String name;
    @NotBlank(message = "缺少手机号")
    private String mobile;
    @NotNull(message = "缺少验证码")
    private String smscode;
    @NotBlank(message = "缺少密码")
    private String password;
    private Boolean agree;
}
