package com.hzb.erp.common.pojo.dto;

import lombok.Data;

/**
 * 成绩表查询DTO
 */
@Data
public class GradeParamDTO extends PaginateDTO {
    private Long creator;
    private String title;
    private Long gradeId;
    private Long studentId;
}
