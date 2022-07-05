package com.hzb.erp.common.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p> 绑定微信表单参数对象 </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Data
public class WxAccessBindDTO {
    @NotNull(message = "未选择人员")
    private Long userId;
    @NotNull(message = "未选择微信用户")
    private Long wxAccessId;
}
