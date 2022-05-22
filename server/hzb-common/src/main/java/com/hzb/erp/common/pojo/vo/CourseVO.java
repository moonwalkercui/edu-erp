package com.hzb.erp.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.LessonTypeEnum;
import com.hzb.erp.common.enums.SwitchEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 合约vo
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
public class CourseVO {
    private Long id;

    @ApiModelProperty(value = "课程名")
    private String name;

    @ApiModelProperty(value = "课程提示")
    private String info;

    @ApiModelProperty(value = "状态")
    private SwitchEnum state;

    @ApiModelProperty(value = "科目id")
    private BigInteger subjectId;

    @ApiModelProperty(value = "科目名")
    private String subjectName;

    @ApiModelProperty(value = "课程类型")
    private LessonTypeEnum lessonType;

    @ApiModelProperty(value = "课次数")
    private Integer lessonCount;

    @ApiModelProperty(value = "有效期月份")
    private Integer expireMonths;

    @ApiModelProperty(value = "课程金额")
    private BigDecimal price;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "单位")
    private String unitName;

    @ApiModelProperty(value = "关联课程数量")
    private String linkCount;

    @ApiModelProperty(value = "是否上架")
    private Boolean forSale;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "报名截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate closeDate;

    @ApiModelProperty(value = "师资信息")
    private String teacherInfo;

    @ApiModelProperty(value = "服务说明")
    private String serviceInfo;

    @ApiModelProperty(value = "是否可预约")
    private Boolean bookable;

}
