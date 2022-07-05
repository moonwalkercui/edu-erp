package com.hzb.erp.api.pc.shop.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OrderRefundDTO{

    @ApiModelProperty(value = "订单id")
    @NotNull
    private Long orderId;

    @ApiModelProperty(value = "退款金额")
    @NotNull
    private BigDecimal refundMoney;

    @ApiModelProperty(value = "家长端账号ID")
    private Long userId;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "退款原因类型")
    @NotNull
    private Long reasonType;

    @ApiModelProperty(value = "退款原因备注")
    private String reason;

    private LocalDate endDate;
    private LocalDate startDate;

}
