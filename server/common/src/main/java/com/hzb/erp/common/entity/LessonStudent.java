package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.enums.SignTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 课时学员关联表
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LessonStudent对象", description = "课时学员关联表")
public class LessonStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "课次id")
    private Long lessonId;

    @ApiModelProperty(value = "点名老师id")
    private Long teacherId;

    @ApiModelProperty(value = "班级id")
    private Long classId;

    @ApiModelProperty(value = "学员id")
    private Long studentId;

    @ApiModelProperty(value = "实扣课次")
    private Integer decLessonCount;

    @ApiModelProperty(value = "签到时间")
    private LocalDateTime signTime;

    @ApiModelProperty(value = "签到方式")
    private SignTypeEnum signType;

    @ApiModelProperty(value = "签到状态")
    private SignStateEnum signState;

    @ApiModelProperty(value = "老师点评打分")
    private Integer score;

    @ApiModelProperty(value = "老师评语")
    private String evaluation;

    @ApiModelProperty(value = "评语时间")
    private LocalDateTime evaluateTime;

    @ApiModelProperty(value = "评语老师")
    private Long evaluateTeacher;

    @ApiModelProperty(value = "消费课程ID")
    private Long consumeCourseId;

    @ApiModelProperty(value = "顾问id")
    private Long counselor;
}

