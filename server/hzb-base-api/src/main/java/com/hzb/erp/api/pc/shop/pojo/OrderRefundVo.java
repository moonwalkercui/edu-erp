package com.hzb.erp.api.pc.shop.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.OrderRefundStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单退款VO
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
public class OrderRefundVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "退款单号")
    private String refundSn;

    @ApiModelProperty(value = "订单单号")
    private String orderSn;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundMoney;

    @ApiModelProperty(value = "原因类型")
    private String reasonTypeText;

    @ApiModelProperty(value = "原因描述")
    private String reason;

    @ApiModelProperty(value = "状态")
    private OrderRefundStateEnum state;

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "审核人姓名")
    private String verifyName;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime verifyTime;

    @ApiModelProperty(value = "审核备注")
    private String verifyRemark;

    @ApiModelProperty(value = "执行退款人姓名")
    private String operatorName;

    @ApiModelProperty(value = "执行退款时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime operationTime;

}
