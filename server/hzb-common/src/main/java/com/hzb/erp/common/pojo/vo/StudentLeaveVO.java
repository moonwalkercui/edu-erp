package com.hzb.erp.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.StudentLeaveSateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author 541720500@qq.com
 */
@Data
public class StudentLeaveVO {
    private Long id;
    private String studentName;
    private String courseName;
    private String teacherNames;
    private String mobile;
    @ApiModelProperty(value = "课次日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private String reason;

    private StudentLeaveSateEnum state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;
}
