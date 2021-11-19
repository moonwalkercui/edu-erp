package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CashoutParamDTO extends PaginateDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String payee;
    private Long staffId;
    private Long type;
    private Integer verifyState;
}
