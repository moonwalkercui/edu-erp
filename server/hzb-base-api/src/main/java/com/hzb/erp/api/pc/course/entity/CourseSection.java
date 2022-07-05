package com.hzb.erp.api.pc.course.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hzb.erp.common.entity.AutoFillEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 课程大纲
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
@ApiModel(value = "课程大纲", description = "课程大纲")
public class CourseSection extends AutoFillEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属课程id")
    private Long courseId;

    @ApiModelProperty(value = "章节标题")
    private String title;

    @ApiModelProperty(value = "计划课时数")
    private Integer lessonCount;

}
