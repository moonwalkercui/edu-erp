package com.hzb.erp.common.pojo.vo;

import com.hzb.erp.common.entity.TeachEvaluation;
import lombok.Data;

@Data
public class TeachEvaluationVO extends TeachEvaluation {
    private String teacherName;
    private String studentName;
    private String lessonTitle;
}
