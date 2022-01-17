package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 学员合约查询参数
 *
 * @author 541720500@qq.com
 */
@Data
public class StudentCourseParamDTO extends PaginateDTO {
    private Long courseId;
    private Long studentId;
    private String keyword;
    private String studentName;
    private String subjectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate expiredStart;
    private LocalDate expiredEnd;
    private Boolean excludeExpired;
    // 是否是统计查询
    private Boolean verified;
    private Long operator;
}
