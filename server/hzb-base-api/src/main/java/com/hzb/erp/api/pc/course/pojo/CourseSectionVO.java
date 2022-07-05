package com.hzb.erp.api.pc.course.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 课程章节vo
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
public class CourseSectionVO {

    @ApiModelProperty(value = "章节名称")
    private String title;

    @ApiModelProperty(value = "计划课时数")
    private Integer lessonCount;

}
