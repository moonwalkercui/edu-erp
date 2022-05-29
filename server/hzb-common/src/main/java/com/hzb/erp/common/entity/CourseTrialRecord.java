package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * CourseTrialRecord课程体验卡领取记录
 * @author 
 */
@ApiModel(value="com.hzb.erp.CourseTrialRecord课程体验卡领取记录")
@Data
public class CourseTrialRecord implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 课程
     */
    @ApiModelProperty(value="体验卡id")
    private Long trialId;

    @ApiModelProperty(value="学生id")
    private Long studentId;

    @ApiModelProperty(value="学生id")
    private Long userId;

    @ApiModelProperty(value="过期时间")
    private LocalDate expiredDate;

    @ApiModelProperty(value="生成的学生课程关联记录")
    private Long studentCourseId;

    @ApiModelProperty(value = "添加时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    private static final long serialVersionUID = 1L;
}