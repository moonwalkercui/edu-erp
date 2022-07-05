package com.hzb.erp.common.pojo;

import lombok.Data;

import java.time.LocalDate;

/**
 * 操作日志查询DTO
 */
@Data
public class OperationLogParamDTO extends PaginateDTO {

    private String keyword;
    private String type;
    private Long operator;
    private LocalDate endDate;
    private LocalDate startDate;

}
