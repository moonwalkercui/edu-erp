package com.hzb.erp.common.pojo.vo;

import com.hzb.erp.common.entity.StudentCreditLog;
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
