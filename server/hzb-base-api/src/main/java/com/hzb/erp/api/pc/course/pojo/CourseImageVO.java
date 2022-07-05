package com.hzb.erp.api.pc.course.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 课程图片vo
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
public class CourseImageVO {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "图片url")
    private String imageUrl;
}
