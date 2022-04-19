package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.hzb.erp.common.enums.VerifyStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 试听预约记录
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Appointment对象", description = "试听预约记录")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "试听课次")
    private Long lessonId;

    @ApiModelProperty(value = "所属课程")
    private Long courseId;

    @ApiModelProperty(value = "学员id")
    private Long studentId;

    @ApiModelProperty(value = "预约时间")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime verifyTime;

    @ApiModelProperty(value = "审核状态")
    private VerifyStateEnum verifyState;

    @ApiModelProperty(value = "审核人")
    private Long verifyStaff;


}
