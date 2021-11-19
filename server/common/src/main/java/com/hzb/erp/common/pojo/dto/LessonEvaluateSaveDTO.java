package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LessonEvaluateSaveDTO extends PaginateDTO {

    @NotNull(message = "缺少主键")
    private Long id;

    @NotNull(message = "缺少评分")
    private Integer score;

    private String evaluation;
}
