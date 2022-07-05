package com.hzb.erp.api.pc.clazz.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ClassSaveDTO {
    private Long id;

    @NotBlank(message = "未输入名称")
    private String name;

    @NotNull(message = "未选择课程")
    private Long courseId;

    private Long classroomId;

    @NotNull(message = "未选择班级负责人")
    private Long teacherId;
//    private Long assistantId1;
//    private Long assistantId2;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "未输入预招人数")
    @Min(value = 1, message = "金额不能小于1")
    private Integer plannedStudentCount;

    @NotNull(message = "未输入总价")
    @Min(value = 1, message = "金额不能小于1")
    private Integer plannedLessonCount;

    private String remark;

}
