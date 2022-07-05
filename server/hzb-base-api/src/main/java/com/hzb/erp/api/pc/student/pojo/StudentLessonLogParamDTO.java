package com.hzb.erp.api.pc.student.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

/**
 * 课次变动记录记录DTO
 */
@Data
public class StudentLessonLogParamDTO extends PaginateDTO {
    private Long teacherId;
    private Long studentId;
}
