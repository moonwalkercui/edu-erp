package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class HomeworkRecordCommentSaveDTO extends PaginateDTO {
    private Long id;

    @NotNull(message = "缺少homeworkId参数")
    private Long homeworkId;

    @NotNull(message = "缺少评分")
    private Integer score;

    private String comment;
}
