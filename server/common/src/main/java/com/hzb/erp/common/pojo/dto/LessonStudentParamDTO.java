package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import java.time.LocalDate;

/*
 * 课次学员关系表DTO
 * */
@Data
public class LessonStudentParamDTO extends PaginateDTO {
    private String keyword;
    private Long subjectId;
    private Long evaluateTeacherId;
    private Long teacherId;
    private Long lessonId;
    private Long studentId;
    private Long courseId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean onlyEvaluate;
}
