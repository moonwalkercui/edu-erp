package com.hzb.erp.api.mobile.student.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class ChangePasswordFormDTO {

    @NotBlank(message = "缺少旧密码")
    private String oldpassword;
    @NotBlank(message = "缺少新密码")
    private String newpassword;
    @NotBlank(message = "缺少重复")
    private String repassword;
}
