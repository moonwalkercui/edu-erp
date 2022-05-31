package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CourseSectionDTO extends PaginateDTO {

    @NotNull(message = "缺少CourseID")
    private Long courseId;

    @NotBlank(message = "缺少标题")
    private String title;

    @NotNull(message = "缺少课时数")
    private Integer lessonCount;

}
