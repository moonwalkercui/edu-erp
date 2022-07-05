package com.hzb.erp.api.pc.lesson.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 作业记录
 * </p>
 *
 * @author Ryan
 */
@Data
@ApiModel(value = "HomeworkRecordVO对象", description = "作业记录")
public class HomeworkRecordVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "作业ID")
    private Long homeworkId;

    @ApiModelProperty(value = "学员ID")
    private Long studentId;

    @ApiModelProperty(value = "学员姓名")
    private String studentName;

    @ApiModelProperty(value = "提交内容")
    private String content;
    private Integer score;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime commentTime;
    private String commentTeacherName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

}
