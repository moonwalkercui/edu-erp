package com.hzb.erp.common.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 课时的上课时间对象 与父类的区别是,这个有日期 本类可以生成课时列表
 *
 * @author Ryan
 * description
 **/
@Data
public class LessonDatetimeBO extends LessonWeekdayTimeBO {
    @ApiModelProperty(value = "日期")
    private LocalDate date;
}
