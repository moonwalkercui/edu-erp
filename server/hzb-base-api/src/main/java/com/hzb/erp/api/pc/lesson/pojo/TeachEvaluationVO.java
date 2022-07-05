package com.hzb.erp.api.pc.lesson.pojo;

import com.hzb.erp.api.pc.lesson.entity.TeachEvaluation;
import lombok.Data;

@Data
public class TeachEvaluationVO extends TeachEvaluation {
    private String teacherName;
    private String studentName;
    private String lessonTitle;
}
