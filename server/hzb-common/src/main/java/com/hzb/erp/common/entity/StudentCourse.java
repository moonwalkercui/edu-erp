package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.FinanceStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 学员合约表
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "StudentCourse对象", description = "学员合约表")
public class StudentCourse extends AutoFillEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "购买科目id")
    private Long subjectId;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "有效期至")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "经手人")
    private Long operator;

    @ApiModelProperty(value = "购买总课次")
    private Integer countLessonTotal;

    @ApiModelProperty(value = "已上课次")
    private Integer countLessonComplete;

    @ApiModelProperty(value = "退费次数")
    private Integer countLessonRefund;

    @ApiModelProperty(value = "不足时已提醒次数")
    private Integer warningTimes;

//    @ApiModelProperty(value = "剩余课时 改成自动计算")
//    private Integer countLessonRemaining;

    @ApiModelProperty(value = "课程金额")
    private BigDecimal courseAmount;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "成交/合约金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "实付金额")
    private BigDecimal paidAmount;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "是否付清")
    private Boolean payOff;

    @ApiModelProperty(value = "审核状态")
    private FinanceStateEnum verifyState;

    @ApiModelProperty(value = "消课优先级")
    private Integer priority;

    @ApiModelProperty(value = "是否来自于体验卡")
    private Boolean fromTrial;

}
