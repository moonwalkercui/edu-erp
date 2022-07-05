package com.hzb.erp.api.pc.course.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

@Data
public class CourseCommentParamDTO extends PaginateDTO {
    private Long courseId;
    private Long studentId;
    private Boolean state;
    private Integer limit;
    private String orderSn;
}
