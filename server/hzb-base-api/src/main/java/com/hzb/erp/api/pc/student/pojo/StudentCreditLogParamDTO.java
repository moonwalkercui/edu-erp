package com.hzb.erp.api.pc.student.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

@Data
public class StudentCreditLogParamDTO extends PaginateDTO {
    private Long studentId;
    private String mobile;
    private String changeTypeText;
    private Integer changeType;
    private String endDate;
    private String startDate;
}
