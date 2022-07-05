package com.hzb.erp.api.pc.lesson.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class HomeworkSaveDTO extends PaginateDTO {
    private Long id;

    @NotNull(message = "缺少班级")
    private Long classId;

    @NotBlank(message = "缺少标题")
    private String title;

    @NotBlank(message = "缺少作业内容")
    private String content;
}
