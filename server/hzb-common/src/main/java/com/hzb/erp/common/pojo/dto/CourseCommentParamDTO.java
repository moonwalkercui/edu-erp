package com.hzb.erp.common.pojo.dto;

import lombok.Data;

@Data
public class CourseCommentParamDTO extends PaginateDTO {
    private Long courseId;
    private Long studentId;
    private Boolean state;
    private Integer limit;
}
