package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 课次编排
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LessonSchedule对象", description = "课次编排")
public class LessonSchedule extends AutoFillEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "班级id")
    private Long classId;

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "上课老师id列表")
    private String teacherIds;

    @ApiModelProperty(value = "助教id列表")
    private String assistantIds;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "学员扣课次数")
    private Integer decLessonCount;

    @ApiModelProperty(value = "总排课次数")
    private Integer times;

    @ApiModelProperty(value = "排除节日")
    private Boolean excludeHoliday;

    @ApiModelProperty(value = "是否生成课表")
    private Boolean state;

    @ApiModelProperty(value = "冲突的id字段")
    private String conflictIds;

}
