package com.hzb.erp.common.pojo.dto;

import lombok.Data;

/**
 * 课次变动记录记录DTO
 */
@Data
public class StudentLessonLogParamDTO extends PaginateDTO {
    private Long teacherId;
    private Long studentId;
}
