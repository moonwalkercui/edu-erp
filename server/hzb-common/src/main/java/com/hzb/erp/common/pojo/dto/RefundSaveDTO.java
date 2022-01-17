package com.hzb.erp.common.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class RefundSaveDTO {

    private Long id;

    @ApiModelProperty(value = "学生id")
    @NotNull(message = "未选择学生")
    private Long studentId;

    @ApiModelProperty(value = "学生课程关联表id")
    @NotNull(message = "未选择退费课程")
    private Long studentCourseId;

    @ApiModelProperty(value = "退费金额负数")
    @NotNull(message = "未设置退费金额")
    @Min(value = 0, message = "不能小于0元")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "退费课时")
    @Min(value = 0, message = "退费课时不能小于0")
    private Integer refundLessonCount;

    @ApiModelProperty(value = "退费说明")
    private String remark;

}
