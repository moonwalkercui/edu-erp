package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hzb.erp.common.enums.LessonTypeEnum;
import com.hzb.erp.common.enums.SwitchEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 课程
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Course对象", description = "合约")
public class Course extends AutoFillEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "课程名")
    private String name;

    @ApiModelProperty(value = "课程提示")
    private String info;

    @ApiModelProperty(value = "状态")
    private SwitchEnum state;

    @ApiModelProperty(value = "科目id")
    private Long subjectId;

    @ApiModelProperty(value = "课次数")
    private Integer lessonCount;

    @ApiModelProperty(value = "课程类型")
    private LessonTypeEnum lessonType;

    @ApiModelProperty(value = "有效期月份")
    private Integer expireMonths;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "单位")
    private String unitName;

    @ApiModelProperty(value = "是否可预约")
    private Boolean bookable;

    @ApiModelProperty(value = "是否上架")
    private Boolean forSale;

    @ApiModelProperty(value = "库存名额")
    private Integer storage;

}
