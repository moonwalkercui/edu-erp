package com.hzb.erp.common.pojo.dto;

import lombok.Data;

/**
 * 通用参数接收类
 */

@Data
public class OrgParamDTO extends PaginateDTO {
    private String name;
    private Integer level;
    private Integer type;
    private Long pid;
}
