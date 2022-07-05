package com.hzb.erp.api.pc.lesson.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.LessonStateEnum;
import com.hzb.erp.common.enums.TeachTypeEnum;
import com.hzb.erp.common.entity.AutoFillEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 课次表
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Lesson对象", description = "课次表")
public class Lesson extends AutoFillEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "课次标题")
    private String title;

    @ApiModelProperty(value = "课节数")
    private Integer sn;

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "编排计划id")
    private Long scheduleId;

    @ApiModelProperty(value = "班级id")
    private Long classId;

    @ApiModelProperty(value = "教室id")
    private Long roomId;

    @ApiModelProperty(value = "上课日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @ApiModelProperty(value = "学员上课扣课次数")
    private Integer decCount;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否可预约1是0否")
    private Boolean canAppointment;

    @ApiModelProperty(value = "授课方式")
    private TeachTypeEnum teachType;

    @ApiModelProperty(value = "是否试听")
    private Boolean onTrail;

    @ApiModelProperty(value = "试听结果")
    private String trailResult;

    @ApiModelProperty(value = "状态")
    private LessonStateEnum state;

    @ApiModelProperty(value = "结课时间")
    private LocalDateTime closeTime;

    @ApiModelProperty(value = "结课人")
    private Long closeOperator;

    @ApiModelProperty(value = "主讲人（第一个老师）")
    private Long teacherId;

    @ApiModelProperty(value = "是否开放预约")
    private Boolean bookable;

//    @ApiModelProperty(value = "点名状态")
//    private Boolean rollcallState;

    /**
    * 课程描述文字
    * */
    public String descToString() {
        return date.toString() + " " + startTime.toString() + "~" + endTime.toString() + " " + title;
    }
}
