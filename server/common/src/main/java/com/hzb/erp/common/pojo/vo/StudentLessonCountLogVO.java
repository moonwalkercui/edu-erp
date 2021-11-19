package com.hzb.erp.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.LessonCountChangeStageEnum;
import com.hzb.erp.common.enums.LessonStateEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author 541720500@qq.com
 */
@Data
public class StudentLessonCountLogVO {
    private Long id;
    private Long studentId;
    private String studentName;
    private String courseName;
    private Long lessonId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    private LessonStateEnum lessonState;
    private Integer changeCount;
    private Integer remainingCount;
    private String staffName;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date addTime;
    private LessonCountChangeStageEnum stage;
}
