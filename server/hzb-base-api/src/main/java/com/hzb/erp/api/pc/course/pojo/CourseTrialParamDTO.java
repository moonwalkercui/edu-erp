package com.hzb.erp.api.pc.course.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

/**
 * 体验卡查询DTO
 */
@Data
public class CourseTrialParamDTO extends PaginateDTO {
    private Long courseId;
    private Boolean state;
    private String title;
    // 去掉发行结束的
    private Boolean excludeEnd;
    // 去掉拥有的
    private Long excludeStudentId;
}
