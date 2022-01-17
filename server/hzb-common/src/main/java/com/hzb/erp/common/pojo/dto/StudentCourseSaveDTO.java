package com.hzb.erp.common.pojo.dto;

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

    @NotNull(message = "未选择学员")
    private Long studentId;

    private String studentName;

    @NotNull(message = "未选择课程")
    private Long courseId;

    @NotNull(message = "缺少应收金额")
    @Min(value = 1, message = "金额不能小于1")
    private BigDecimal courseAmount;

    @NotNull(message = "缺少实收金额")
    @Min(value = 1, message = "金额不能小于1")
    private BigDecimal paidAmount;

    @NotNull(message = "缺少课时数")
    @Min(value = 1, message = "课时数不能小于1")
    private Integer countLessonTotal;

    private Long operator;

    private BigDecimal discount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    private String remark;


}
