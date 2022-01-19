package com.hzb.erp.studentCenter.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class ForgetPasswordDTO {

    @NotBlank(message = "缺少手机号")
    private String mobile;
    @NotBlank(message = "缺少验证码")
    private String smscode;
    @NotBlank(message = "缺少密码")
    private String password;
}
