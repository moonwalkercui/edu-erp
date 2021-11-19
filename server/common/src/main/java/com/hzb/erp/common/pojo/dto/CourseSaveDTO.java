package com.hzb.erp.common.pojo.dto;

import com.hzb.erp.common.enums.LessonTypeEnum;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class CourseSaveDTO extends PaginateDTO {

    private Long id;

    @NotBlank(message = "未输入名称")
    @Size(min = 2, max = 30, message = "名称字数有误")
    private String name;

    @NotNull(message = "未选择科目")
    private Long subjectId;

    @Min(value = 1, message = "金额不能小于1")
    private BigDecimal unitPrice;

    @NotNull(message = "未输入总价")
    @Min(value = 1, message = "金额不能小于1")
    private BigDecimal price;

    @NotNull(message = "未选择单位")
    private String unitName;

    @NotNull(message = "未输入课次数")
    @Min(value = 1, message = "课次数不能小于1")
    private Integer lessonCount;

    @NotNull(message = "未选择课堂类型")
    private LessonTypeEnum lessonType;

    @NotNull(message = "未输入有效月数")
    @Min(value = 1, message = "有效月数不能小于1")
    private Integer expireMonths;
    private String info;

}
