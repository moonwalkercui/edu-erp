package com.hzb.erp.api.pc.lesson.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课时老师关联表
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LessonTeacher对象", description = "课时老师关联表")
public class LessonTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "课程id")
    private Long lessonId;

    @ApiModelProperty(value = "学员id")
    private Long teacherId;

    @ApiModelProperty(value = "课时费")
    private BigDecimal commission;

    @ApiModelProperty(value = "1上课老师2助教")
    private Integer typeNum;


}
