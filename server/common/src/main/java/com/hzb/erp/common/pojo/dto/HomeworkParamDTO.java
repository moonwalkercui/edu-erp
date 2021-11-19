package com.hzb.erp.common.pojo.dto;

import lombok.Data;

/**
 * 作业列表查询DTO
 */
@Data
public class HomeworkParamDTO extends PaginateDTO {
    private Long homeworkId;
    private Long teacherId;
    private Long studentId;
    private Long classId;
    private String title;
}
