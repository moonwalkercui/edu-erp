package com.hzb.erp.api.pc.creditMall.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

@Data
public class CreditExchangeParamDTO extends PaginateDTO {

    private String giftName;
    private Long creditMallId;
    private Long studentId;
    private String verifyStateText;
    private Integer verifyState;
    private String endDate;
    private String startDate;

}
