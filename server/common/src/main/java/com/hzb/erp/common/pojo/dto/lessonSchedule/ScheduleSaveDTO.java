package com.hzb.erp.common.pojo.dto.lessonSchedule;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ryan
 * description
 **/
@Data
public class ScheduleSaveDTO {

    private Long id;
    @NotNull(message = "请选择班级")
    private Long classId;

    @NotNull(message = "未选择上课老师")
    @Size(min = 1, message = "请选择上课老师")
    private List<Long> teacherIds;

    private List<Long> assistantIds;

    @NotNull(message = "未设置开始日期")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalDate startDate;

    @NotNull(message = "未设置结束日期")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalDate endDate;

    private Boolean excludeHoliday;

    private Integer decLessonCount;

    private Integer times;

    @NotNull(message = "未设置上课时间")
    @Size(min = 1, message = "请设置上课时间")
    private List<ScheduleSettingDTO> setting;

}
