package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class FinanceParamDTO extends PaginateDTO {
    private String title;
    private Long operator;
    private Long payer;
    private List<Integer> type;
    private Integer state;
}
