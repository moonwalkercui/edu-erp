package com.hzb.erp.api.pc.shop.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 订单确认
 */
@Data
public class OrderConfirmDTO {
    @NotNull(message = "缺少ID")
    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @NotNull(message = "缺少价格")
    @ApiModelProperty(value = "支付金额")
    private BigDecimal price;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discount;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "家长端账号ID")
    private Long userId;

    @ApiModelProperty(value = "学生ID")
    @NotNull(message = "缺少选择学生")
    private Long studentId;
}
