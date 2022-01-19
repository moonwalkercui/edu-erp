package com.hzb.erp.common.pojo.dto;

import lombok.Data;

/**
 * 课时列表查询DTO
 */
@Data
public class StudentParamDTO extends PaginateDTO {

    private String keyword;
    private Integer stage;
    // 排除阶段
    private Integer excludeStage;
    private Long classId;
    // 排除班级
    private Long excludeClassId;
    private Long creator;
    private Long userId;
    private Long courseId;
}
