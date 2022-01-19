package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LessonSignSaveDTO extends PaginateDTO {

    @NotNull(message = "缺少课程参数")
    private Long lessonId;
    @NotNull(message = "缺少学员参数")
    private Long studentId;
    @NotNull(message = "缺少签到状态参数")
    private Integer state;
}
