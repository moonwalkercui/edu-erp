package com.hzb.erp.api.pc.clazz.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

@Data
public class ClassStudentParamDTO extends PaginateDTO {
    private Long classId;
    private Long lessonId;
    private Boolean unsigned;
}
