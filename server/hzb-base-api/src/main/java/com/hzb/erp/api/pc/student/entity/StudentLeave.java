package com.hzb.erp.api.pc.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hzb.erp.common.enums.StudentLeaveSateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 学员请假
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "StudentLeave对象", description = "学员请假")
public class StudentLeave implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private LocalDateTime addTime;

    @ApiModelProperty(value = "学生外键")
    private Long studentId;

    @ApiModelProperty(value = "请假课次外键")
    private Long lessonId;

    @ApiModelProperty(value = "原因")
    private String reason;

    @ApiModelProperty(value = "状态")
    private StudentLeaveSateEnum state;

//    @ApiModelProperty(value = "请假状态")
//    private StudentLeaveSateEnum verifyState;

    @ApiModelProperty(value = "取消操作人")
    private Long verifyStaff;

    @ApiModelProperty(value = "取消时间")
    private LocalDateTime verifyTime;

//    @ApiModelProperty(value = "审核备注")
//    private String verifyRemark;

}
