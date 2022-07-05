package com.hzb.erp.api.pc.course.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 联系记录查询DTO
 */
@Data
public class CourseTrialRecordVO {

    @ApiModelProperty(value="主键")
    private Long id;

    @ApiModelProperty(value="体验卡id")
    private Long trialId;

    @ApiModelProperty(value="学生id")
    private Long studentId;

    @ApiModelProperty(value="手机号")
    private String mobile;

    @ApiModelProperty(value="学生姓名")
    private String studentName;

    @ApiModelProperty(value="课程名称")
    private String courseName;

    @ApiModelProperty(value="体验卡名称")
    private String trialTitle;

    @ApiModelProperty(value = "领取时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "领取课时数")
    private Integer lessonCount;

    @ApiModelProperty(value = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiredDate;

}
