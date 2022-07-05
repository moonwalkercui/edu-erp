package com.hzb.erp.api.pc.student.pojo;

import com.hzb.erp.api.pc.student.entity.StudentCreditLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 兑换记录
 */
@Data
public class StudentCreditLogVO extends StudentCreditLog {

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

}
