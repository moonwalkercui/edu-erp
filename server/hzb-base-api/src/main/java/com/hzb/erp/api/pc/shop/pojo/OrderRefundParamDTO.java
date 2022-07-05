package com.hzb.erp.api.pc.shop.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class OrderRefundParamDTO extends PaginateDTO {
    private List<Integer> state;
    @ApiModelProperty(value = "订单单号")
    private String orderSn;
    @ApiModelProperty(value = "退款单号")
    private String refundSn;
    private Long orderId;
    private Long studentId;
    private Long reasonType;
    private LocalDate endDate;
    private LocalDate startDate;
}
