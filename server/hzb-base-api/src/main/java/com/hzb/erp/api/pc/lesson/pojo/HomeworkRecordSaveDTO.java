package com.hzb.erp.api.pc.lesson.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class HomeworkRecordSaveDTO extends PaginateDTO {
    private Long id;

    @NotNull(message = "缺少homeworkId参数")
    private Long homeworkId;

    private Long studentId;

    @NotNull(message = "缺少内容")
    private String content;

    private List<String> imgList;

}
