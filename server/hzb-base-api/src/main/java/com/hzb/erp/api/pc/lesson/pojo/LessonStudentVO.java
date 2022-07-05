package com.hzb.erp.api.pc.lesson.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.enums.SignTypeEnum;
import com.hzb.erp.api.pc.student.pojo.StudentVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonStudentVO extends StudentVO {
    private Long id;

    private SignStateEnum signState;

    @ApiModelProperty(value = "实扣课次数")
    private Integer decLessonCount;

    private Boolean onTrail;

    private SignTypeEnum signType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime signTime;

    private Long lessonId;
    private String lessonTitle;
    private String teacherName;
    private Long teacherId;

    private String courseName;

    private Integer score;
    private String evaluation;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime evaluateTime;
    private String evaluateTeacherName;
    private Integer countTeachEvaluation;

}
