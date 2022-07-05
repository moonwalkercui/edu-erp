package com.hzb.erp.api.pc.lesson.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 作业提交记录
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "HomeworkRecord对象", description = "作业提交记录")
public class HomeworkRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属作业")
    private Long homeworkId;

    @ApiModelProperty(value = "学生名")
    private Long studentId;

    @ApiModelProperty(value = "时间")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "内容")
    private String content;
    private String images;
    private Integer score;
    private String comment;
    private LocalDateTime commentTime;
    private Long commentTeacher;

}
