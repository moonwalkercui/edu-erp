package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 课程介绍图片
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
@ApiModel(value = "课程介绍图片", description = "课程介绍图片")
public class CourseImage extends AutoFillEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属课程id")
    private Long courseId;

    @ApiModelProperty(value = "图片url")
    private String imageUrl;

}
