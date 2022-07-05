package com.hzb.erp.api.pc.clazz.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class GradeRecordSaveDTO extends PaginateDTO {
    private Long id;

    @NotNull(message = "缺少考核项id")
    private Long gradeId;

    @NotNull(message = "未选择学员")
    private Long studentId;

    @NotNull(message = "缺少分数")
    @Min(value = 0, message = "分数最小为0分")
    private Integer score;

}
