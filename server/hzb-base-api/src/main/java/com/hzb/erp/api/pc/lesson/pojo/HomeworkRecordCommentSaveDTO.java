package com.hzb.erp.api.pc.lesson.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class HomeworkRecordCommentSaveDTO extends PaginateDTO {
    private Long id;

    @NotNull(message = "缺少homeworkId参数")
    private Long homeworkId;

    @NotNull(message = "缺少评分")
    private Integer score;

    private String comment;
}
