package com.hzb.erp.api.pc.lesson.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 课时添加调课生DTO
 */
@Data
public class LessonStudentAddDTO {

    @NotNull(message = "缺少参数ClassId")
    private Long lessonId;

    @Size(min = 1, message = "请选择学员")
    private List<Long> studentIds;

}
