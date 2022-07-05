package com.hzb.erp.api.pc.lesson.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalTime;

/**
 * 排课计划的上课时间设置被展开日期或星期的逻辑对象(上课设置是用一个字段里weeks里存储的)
 * 提示: 本类不能用于生成课时列表,因为没有日期.
 *
 * @author Ryan
 * description
 **/
@Data
public class LessonWeekdayTimeBO implements Comparable<LessonWeekdayTimeBO> {

    @ApiModelProperty(value = "所属上课时间设置Id")
    private Long settingId;

    @ApiModelProperty(value = "星期几")
    private Integer weekday;

    @ApiModelProperty(value = "开始时间")
    private LocalTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalTime endTime;

    @ApiModelProperty(value = "教室ID")
    private Long roomId;

    @Override
    public int compareTo(LessonWeekdayTimeBO o) {
        // 先比较星期
        if (this.getWeekday() > o.getWeekday()) {
            return 1;
        } else if (this.getWeekday() < o.getWeekday()) {
            return -1;
        }
        // 以下是星期一样的情况
        if (this.getStartTime().isAfter(o.getStartTime())) {
            return 1;
        } else if (this.getStartTime().isBefore(o.getStartTime())) {
            return -1;
        }
        return 0;
    }

}
