package com.hzb.erp.common.pojo.dto;

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
    private Long courseId;

    @NotNull(message = "缺少价格")
    private BigDecimal price;

    private String remark;

    @ApiModelProperty(value = "家长端账号ID")
    private Long userId;

    @ApiModelProperty(value = "学生ID")
    private Long studentId;
}
