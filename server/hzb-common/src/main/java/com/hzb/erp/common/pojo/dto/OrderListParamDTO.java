package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import java.util.List;


@Data
public class OrderListParamDTO extends PaginateDTO {
    private List<Integer> state;
    private String sn;
    private Long studentId;
    private Long courseId;
}
