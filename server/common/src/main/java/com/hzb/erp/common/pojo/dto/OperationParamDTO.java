package com.hzb.erp.common.pojo.dto;

import lombok.Data;

/**
 * 操作记录参数
 */

@Data
public class OperationParamDTO extends PaginateDTO {
    private String type;
    private Long itemId;
}
