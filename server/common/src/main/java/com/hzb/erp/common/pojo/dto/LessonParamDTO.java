package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 课时列表查询DTO
 */
@Data
public class LessonParamDTO extends PaginateDTO {
    private Long lessonId;
    private Long studentId;
    private Long teacherId;
    private Long[] teacherIds;
    private Long courseId;
    private Long[] courseIds;
    private Long classId;
    private Long[] classIds;
    private Long subjectId;
    private Long roomId;
    private Integer onTrail;
    private Long[] ids;

    private LocalDate endDate;
    private LocalDate startDate;

    private LocalDate date;
}
