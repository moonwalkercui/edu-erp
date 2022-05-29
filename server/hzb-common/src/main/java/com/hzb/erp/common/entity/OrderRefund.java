package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.OrderRefundStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单退款
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "订单退款", description = "订单退款")
@TableName(value = "shop_order_refund")
public class OrderRefund implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "退款编号")
    private String refundSn;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "学生端账号id")
    private Long userId;

    @ApiModelProperty(value = "学生端账号id")
    private Long studentId;

    @ApiModelProperty(value = "物品名称")
    private String itemNames;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundMoney;

    @ApiModelProperty(value = "原因类型")
    private Long reasonType;

    @ApiModelProperty(value = "原因描述")
    private String reason;

    @ApiModelProperty(value = "状态")
    private OrderRefundStateEnum state;

    @ApiModelProperty(value = "审核人ID")
    private Long verifyStaff;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime verifyTime;

    @ApiModelProperty(value = "审核备注")
    private String verifyRemark;

    @ApiModelProperty(value = "微信退款id")
    private String wxRefundId;

    @ApiModelProperty(value = "申请时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "执行退款人ID")
    private Long operatorId;

    @ApiModelProperty(value = "执行退款人姓名")
    private String operatorName;

    @ApiModelProperty(value = "执行退款时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime operationTime;

}
