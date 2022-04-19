package com.hzb.erp.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.*;
import com.hzb.erp.utils.EnumTools;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class LessonVO {
    private Long id;
    @ApiModelProperty(value = "课节数 第几节")
    private Integer sn;

    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ApiModelProperty(value = "星期")
    private WeekdaysEnum week;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "课次标题")
    private String title;

    @ApiModelProperty(value = "上课教师")
    private String teacherNames;
    private String teacherIds;

    @ApiModelProperty(value = "助教")
    private String assistantNames;
    private String assistantIds;

    @ApiModelProperty(value = "课程")
    private String courseName;

    private Long classId;
    private Long roomId;

    @ApiModelProperty(value = "班级")
    private String className;

    @ApiModelProperty(value = "课程类型")
    private LessonTypeEnum lessonType;

    @ApiModelProperty(value = "教室")
    private String classroom;
    private TeachTypeEnum teachType;

    @ApiModelProperty(value = "是否试听")
    private Boolean onTrail;

    @ApiModelProperty(value = "试听结果")
    private String trailResult;

    @ApiModelProperty(value = "应扣课次数")
    private String decCount;

    @ApiModelProperty(value = "学员的签到状态")
    private SignStateEnum studentSignState;

    @ApiModelProperty(value = "学员的实扣课时")
    private String studentDecLessonCount;

    @ApiModelProperty(value = "签到人数")
    private Integer studentSignNum;

    @ApiModelProperty(value = "学员数量")
    private Integer studentNum;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否开放预约")
    private Boolean booking;

    private LessonStateEnum state;

//    @ApiModelProperty(value = "点名状态")
//    private Boolean rollcallState;

    public WeekdaysEnum getWeek() {
        return EnumTools.getByCode(date.getDayOfWeek().getValue(), WeekdaysEnum.class);
    }
}
