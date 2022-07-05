package com.hzb.erp.api.pc.course.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

/**
 * 体验卡查询DTO
 */
@Data
public class CourseTrialRecordParamDTO extends PaginateDTO {
    private Long trialId;
    private Long studentId;
}
