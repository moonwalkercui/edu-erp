package com.hzb.erp.api.pc.finance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hzb.erp.common.enums.RefundTypeEnum;
import com.hzb.erp.common.enums.VerifyStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 学员退款记录
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Refund对象", description = "学员退款记录")
public class Refund implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "合约id:每个合约仅可退款一次")
    private Long studentCourseId;

    @ApiModelProperty(value = "退费办理人")
    private Long operator;

    @ApiModelProperty(value = "退费金额负数")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "退费课时")
    private Integer refundLessonCount;

    @ApiModelProperty(value = "退费发起时间")
    private LocalDateTime applyTime;

    @ApiModelProperty(value = "退费说明")
    private String remark;

    @ApiModelProperty(value = "退费办完时间")
    private LocalDateTime doneTime;

    @ApiModelProperty(value = "类型")
    private RefundTypeEnum typeNum;

    @ApiModelProperty(value = "审核时间")
    private Long verifyTime;

    @ApiModelProperty(value = "审核人")
    private Long verifyStaff;

    @ApiModelProperty(value = "退款状态")
    private VerifyStateEnum verifyState;

}
