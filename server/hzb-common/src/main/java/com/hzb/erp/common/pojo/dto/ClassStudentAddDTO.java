package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 课时列表查询DTO
 */
@Data
public class ClassStudentAddDTO {

    @NotNull(message = "缺少参数ClassId")
    private Long classId;

    @Size(min = 1, message = "请选择学员")
    private List<Long> studentIds;

}
