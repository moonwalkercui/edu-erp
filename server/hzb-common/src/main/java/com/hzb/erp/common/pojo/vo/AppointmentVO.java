package com.hzb.erp.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.VerifyStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentVO {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "学生id")
    private Long studentId;
    @ApiModelProperty(value = "学生姓名")
    private String studentName;
    @ApiModelProperty(value = "学生手机号")
    private String studentMobile;
    @ApiModelProperty(value = "课程ID")
    private Long lessonId;
    @ApiModelProperty(value = "课程标题")
    private String lessonTitle;
    @ApiModelProperty(value = "上课时间")
    private String lessonTime;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "预约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;
    @ApiModelProperty(value = "审核人")
    private String verifyStaffName;
    @ApiModelProperty(value = "审核状态")
    private VerifyStateEnum verifyState;
    @ApiModelProperty(value = "审核备注")
    private String verifyRemark;
}
