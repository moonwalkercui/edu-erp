package com.hzb.erp.api.pc.lesson.pojo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * 快速开课
 *
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class LessonSaveQuicklyDTO {

    private Long id;

    @NotNull(message = "未选择课程")
    private Long courseId;

    @NotNull(message = "未选择老师")
    @Size(min = 1, message = "未选择老师")
    private List<Long> teacherIds;

    private List<Long> assistantIds;

    // 快速开课不需要班级，需要学生
    @NotNull(message = "未选择学生")
    @Size(min = 1, message = "未选择学生")
    private List<Long> studentIds;

    private Long roomId;

    @NotNull(message = "未设置上课日期")
    private LocalDate date;

    @Min(value = 1, message = "消课次数必须不能小于1")
    private Integer decCount;

    @NotNull(message = "未设置开始时间")
    private LocalTime startTime;

    @NotNull(message = "未设置结束时间")
    private LocalTime endTime;

    @NotNull(message = "是否开放预约")
    private Boolean bookable;

}
