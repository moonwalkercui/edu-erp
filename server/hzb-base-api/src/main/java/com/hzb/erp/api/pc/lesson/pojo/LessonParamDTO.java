package com.hzb.erp.api.pc.lesson.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
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

    // 是否可预约 20220424 cui
    private Boolean bookable;
}
