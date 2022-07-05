package com.hzb.erp.api.pc.lesson.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

/*
 * 课次学员关系表DTO
 * */
@Data
public class LessonScheduleParamDTO extends PaginateDTO {
    private Long classId;
    private Integer lessonType;
}
