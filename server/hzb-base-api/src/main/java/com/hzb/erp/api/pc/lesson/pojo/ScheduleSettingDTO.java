package com.hzb.erp.api.pc.lesson.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Ryan
 * description
 **/
@Data
public class ScheduleSettingDTO {

    @NotNull(message = "未设置星期")
    @Size(min = 1, message = "请设置星期")
    private List<Integer> weeks;

    @NotNull(message = "请设置上课开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalTime startTime;

    @NotNull(message = "请设置上课结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalTime endTime;

    private Long roomId;

}
