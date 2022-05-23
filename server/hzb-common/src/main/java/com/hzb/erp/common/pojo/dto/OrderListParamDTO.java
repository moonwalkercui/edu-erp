package com.hzb.erp.common.pojo.dto;

import lombok.Data;


@Data
public class OrderListParamDTO extends PaginateDTO {
    private Integer state;
    private String sn;
    private Long studentId;
}
