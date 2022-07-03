package com.hzb.erp.common.pojo.dto;

import lombok.Data;

@Data
public class CreditExchangeParamDTO extends PaginateDTO {

    private String giftName;
    private Long creditMallId;
    private String verifyStateText;
    private Integer verifyState;
    private String endDate;
    private String startDate;

}
