package com.hzb.erp.api.pc.material.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

@Data
public class MaterialRecordParamDTO extends PaginateDTO {
    private Long studentId;
    private Long staffId;
    private Long materialId;
    private String changeTypeText;
    private Integer changeType;
    private String endDate;
    private String startDate;
}
