package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 体验卡查询DTO
 */
@Data
public class CourseTrialParamDTO extends PaginateDTO {
    private Long courseId;
    private Boolean state;
    private String title;
    // 去掉发行结束胡
    private Boolean excludeEnd;
}
