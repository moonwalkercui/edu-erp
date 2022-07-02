package com.hzb.erp.common.pojo.dto;

import lombok.Data;

@Data
public class MaterialParamDTO extends PaginateDTO {
    private String name;
    private String stateText;
    private Integer state;
}
