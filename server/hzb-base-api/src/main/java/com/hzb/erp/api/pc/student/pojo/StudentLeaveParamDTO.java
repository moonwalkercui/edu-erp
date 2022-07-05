package com.hzb.erp.api.pc.student.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentLeaveParamDTO extends PaginateDTO {
    private Long teacherId;
    private String studentName;
    private LocalDate startDate;
    private LocalDate endDate;
//    private Integer state;
}
