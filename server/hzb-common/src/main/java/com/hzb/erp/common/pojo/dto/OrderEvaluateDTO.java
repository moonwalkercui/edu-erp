package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderEvaluateDTO extends PaginateDTO {

    @NotNull(message = "缺少参数ID")
    private Long orderId;

    @NotNull(message = "缺少分数")
    private Integer score;

    @NotNull(message = "缺少内容")
    private String content;

    private Long studentId;
}
