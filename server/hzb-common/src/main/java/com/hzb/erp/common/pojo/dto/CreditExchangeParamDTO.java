package com.hzb.erp.common.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreditExchangeParamDTO extends PaginateDTO {
    @NotNull(message = "缺少参数")
    @ApiModelProperty(value = "礼品ID")
    private Long id;

    @Min(value = 1, message = "兑换数量有误")
    @NotNull(message = "缺少数量")
    @ApiModelProperty(value = "兑换数量")
    private Integer num;

    @ApiModelProperty(value = "学生账号ID")
    private Long userId;

    @ApiModelProperty(value = "学生ID")
    private Long studentId;
}
