package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 联系记录查询DTO
 */
@Data
public class ContactRecordParamDTO extends PaginateDTO {
    private Long creator;
    private Long studentId;
    private String studentName;
    private Long staffId;
    private Integer stage;
    private LocalDate endDate;
    private LocalDate startDate;
}
