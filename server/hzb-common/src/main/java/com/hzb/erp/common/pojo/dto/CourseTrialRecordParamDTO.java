package com.hzb.erp.common.pojo.dto;

import lombok.Data;

/**
 * 体验卡查询DTO
 */
@Data
public class CourseTrialRecordParamDTO extends PaginateDTO {
    private Long trialId;
    private Long studentId;
}
