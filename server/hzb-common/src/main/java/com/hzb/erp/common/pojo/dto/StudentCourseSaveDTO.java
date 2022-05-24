package com.hzb.erp.common.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 学员合约post参数
 *
 * @author 541720500@qq.com
 */
@Data
public class StudentCourseSaveDTO extends PaginateDTO {

    private Long id;

    @ApiModelProperty(value = "学员")
    @NotNull(message = "未选择学员")
    private Long studentId;

    @ApiModelProperty(value = "学员姓名")
    private String studentName;

    @ApiModelProperty(value = "课程")
    @NotNull(message = "未选择课程")
    private Long courseId;

    @ApiModelProperty(value = "应收金额")
    @NotNull(message = "缺少应收金额")
    @Min(value = 1, message = "金额不能小于1")
    private BigDecimal courseAmount;

    @ApiModelProperty(value = "实收金额")
    @NotNull(message = "缺少实收金额")
    @Min(value = 1, message = "金额不能小于1")
    private BigDecimal paidAmount;

    @ApiModelProperty(value = "课时数")
    @NotNull(message = "缺少课时数")
    @Min(value = 1, message = "课时数不能小于1")
    private Integer countLessonTotal;

    @ApiModelProperty(value = "操作人")
    private Long operator;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discount;

    @ApiModelProperty(value = "过期时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "备注")
    private String remark;


}
