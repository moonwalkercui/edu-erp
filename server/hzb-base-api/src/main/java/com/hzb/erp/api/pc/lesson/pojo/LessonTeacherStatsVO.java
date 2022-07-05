package com.hzb.erp.api.pc.lesson.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LessonTeacherStatsVO {
    private Long teacherId;
    private String teacherName;
    private Integer year;
    private Integer month;
    private Integer teacherCount;
    private Integer assistantCount;
    private BigDecimal classFee;
    private BigDecimal assistantFee;
    private BigDecimal totalClassFee;
    private BigDecimal totalAssistantFee;
}