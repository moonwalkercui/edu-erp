package com.hzb.erp.common.pojo;

import lombok.Data;

@Data
public class StaffParamDTO extends PaginateDTO {
    private String keyword;
    private Integer state;
    private Boolean isManager;
    private String role;
    private Long orgId;
}
