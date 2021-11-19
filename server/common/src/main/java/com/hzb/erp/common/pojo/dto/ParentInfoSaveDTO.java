package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class ParentInfoSaveDTO {
    @NotNull(message = "缺少ID参数")
    private Long id;
    private String mobile;

    @NotNull(message = "缺少姓名")
    private String name;

    @NotNull(message = "缺少状态参数")
    private Boolean state;

}
