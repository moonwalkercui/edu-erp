package com.hzb.erp.api.mobile.student.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Ryan 541720500@qq.com
 * 请假参数
 */
@Data
public class StudentLeaveDTO {
    @NotNull(message = "请选择课程")
    private Long lessonId;

    private Long studentId;
    private String reason;
}
