package com.hzb.erp.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 课程评价vo
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
public class CourseCommentVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "学生头像")
    private String headImg;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "课程ID")
    private Long courseId;

    @ApiModelProperty(value = "课程姓名")
    private String courseName;

    @ApiModelProperty(value = "评价内容")
    private String content;

    @ApiModelProperty(value = "打分")
    private Integer score;

    @ApiModelProperty(value = "发布状态")
    private Boolean state;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

}
