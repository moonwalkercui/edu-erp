package com.hzb.erp.api.pc.lesson.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class LessonScheduleVO {
    private Long id;

    private Long classId;

    @ApiModelProperty(value = "班级名")
    private String className;

    private Long courseId;

    @ApiModelProperty(value = "课程名")
    private String courseName;

    @ApiModelProperty(value = "开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalTime endTime;

    @ApiModelProperty(value = "上课教师Id")
    private String teacherIds;

    @ApiModelProperty(value = "上课教师")
    private String teacherNames;

    @ApiModelProperty(value = "助教Id")
    private String assistantIds;

    @ApiModelProperty(value = "助教")
    private String assistantNames;

    @ApiModelProperty(value = "排课次数")
    private Integer times;

    @ApiModelProperty(value = "学员扣课次数")
    private Integer decLessonCount;

    @ApiModelProperty(value = "不含节日")
    private Boolean excludeHoliday;

    @ApiModelProperty(value = "是否已生成课表")
    private Boolean state;

    @ApiModelProperty(value = "上课时间描述")
    private List<String> lessonTimesInfo;

    @ApiModelProperty(value = "冲突的id字段")
    private String conflictIds;

    @ApiModelProperty(value = "生成的课次数")
    private String lessonCount;

}
