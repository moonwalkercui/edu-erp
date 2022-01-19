package com.hzb.erp.common.pojo.dto;

import lombok.Data;

@Data
public class ClassParamDTO extends PaginateDTO {
    private Long courseId;
    private Long studentId;

    // 班主任/上课老师/助教都作为查询条件
    private Long relTeacherId;
    // 班主任
    private Long teacherId;
    private String className;
    private Integer typeNum;
    private Boolean over;
}
