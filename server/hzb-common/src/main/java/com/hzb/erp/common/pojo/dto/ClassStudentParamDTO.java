package com.hzb.erp.common.pojo.dto;

import lombok.Data;

@Data
public class ClassStudentParamDTO extends PaginateDTO {
    private Long classId;
    private Long lessonId;
    private Boolean unsigned;
}
