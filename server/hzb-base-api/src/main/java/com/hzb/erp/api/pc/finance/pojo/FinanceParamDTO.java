package com.hzb.erp.api.pc.finance.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
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
