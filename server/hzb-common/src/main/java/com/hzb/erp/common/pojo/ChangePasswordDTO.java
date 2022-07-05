package com.hzb.erp.common.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p> </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Data
public class ChangePasswordDTO {
    @NotNull(message = "未选择人员")
    private Long id;

    @NotBlank(message = "缺少密码")
    private String password;
    private String passwordEncode;
}
