package com.hzb.erp.common.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreditMallParamDTO extends PaginateDTO {
    private String name;
    private Long categoryId;
    @ApiModelProperty(value = "是否只显示可兑换的")
    private Boolean canExchange;
    private String stateText;
    private Integer state;
}
