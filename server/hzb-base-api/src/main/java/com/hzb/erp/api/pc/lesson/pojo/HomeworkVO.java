package com.hzb.erp.api.pc.lesson.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 作业
 * </p>
 *
 * @author Ryan
 */
@Data
@ApiModel(value = "Homework对象", description = "作业")
public class HomeworkVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "班级ID")
    private Long classId;

    @ApiModelProperty(value = "班级")
    private String className;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "创建人")
    private Long creator;
    @ApiModelProperty(value = "创建人")
    private String teacherName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    private Integer completeCount;

    @ApiModelProperty(value = "单个学生查询时用于判断是否完成")
    private Boolean completed;

    private List<HomeworkRecordVO> recordList;

}
