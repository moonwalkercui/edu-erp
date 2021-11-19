package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class NameValidDTO {
    private Long id;
    @NotBlank(message = "缺少名称")
    private String name;
}
