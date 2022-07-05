package com.hzb.erp.api.pc.lesson.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

@Data
public class AppointmentParamDTO extends PaginateDTO {
    private Long studentId;
}
