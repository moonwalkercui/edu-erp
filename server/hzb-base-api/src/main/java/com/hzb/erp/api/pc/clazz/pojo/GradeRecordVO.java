package com.hzb.erp.api.pc.clazz.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 成绩单记录
 * </p>
 *
 * @author Ryan
 */
@Data
@ApiModel(value = "GradeRecordVO对象", description = "成绩单记录")
public class GradeRecordVO {
    private Long id;
    @ApiModelProperty(value = "考核项id")
    private Long gradeId;

    @ApiModelProperty(value = "考核项标题")
    private String gradeTitle;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "分数")
    private Integer score;

    @ApiModelProperty(value = "时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

}
