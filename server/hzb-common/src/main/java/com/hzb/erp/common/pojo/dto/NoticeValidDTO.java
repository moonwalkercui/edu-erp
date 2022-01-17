package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class NoticeValidDTO {
    private Long id;
    @NotBlank(message = "缺少标题")
    private String title;
    @NotBlank(message = "缺少内容")
    private String content;
}
