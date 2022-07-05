package com.hzb.erp.api.pc.clazz.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 班级
 *
 * @author Ryan
 */
@Data
public class ClassVO {
    private Long id;

    @ApiModelProperty(value = "班级名")
    private String name;

    @ApiModelProperty(value = "科目名")
    private String subjectName;

    private Long teacherId;
    @ApiModelProperty(value = "班级负责人")
    private String teacherName;

    private Long courseId;
    @ApiModelProperty(value = "课程名")
    private String courseName;

//    private Long assistantId1;
//    @ApiModelProperty(value = "助教1")
//    private String assistantName1;
//
//    private Long assistantId2;
//    @ApiModelProperty(value = "助教2")
//    private String assistantName2;

    //    一对一课程有改参数
//    @ApiModelProperty(value = "一对一课程参数")
//    private String studentName;

    private Long classroomId;
    @ApiModelProperty(value = "教室")
    private String classroom;

    @ApiModelProperty(value = "计划开课日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "计划结课日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "预排课次数")
    private Integer plannedLessonCount;

    @ApiModelProperty(value = "预招人数")
    private Integer plannedStudentCount;


    @ApiModelProperty(value = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate addTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否结业")
    private Boolean beOver;

    /**
     * 以下为sql查询字段
     */
    @ApiModelProperty(value = "学员数")
    private Integer studentCount;

    @ApiModelProperty(value = "上完课次")
    private Integer overLessonCount;

    @ApiModelProperty(value = "已排课次")
    private Integer scheduleLessonCount;
}
