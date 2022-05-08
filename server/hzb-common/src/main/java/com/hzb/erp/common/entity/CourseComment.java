package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 课程评价
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
@ApiModel(value = "课程评价", description = "课程评价")
public class CourseComment extends AutoFillEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属课程id")
    private Long courseId;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "评价内容")
    private String content;

    @ApiModelProperty(value = "打分")
    private Integer score;

    @ApiModelProperty(value = "发布状态")
    private Boolean state;

}
