package com.hzb.erp.api.pc.material.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

@Data
public class MaterialParamDTO extends PaginateDTO {
    private String name;
    private String stateText;
    private Integer state;
}
