package com.hzb.erp.api.pc.clazz.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 教室VO
 * </p>
 *
 * @author Ryan
 */
@Data
@ApiModel(value = "ClassroomVO", description = "教室")
public class ClassroomVO {

    private Long id;

    @ApiModelProperty(value = "教室名")
    private String name;

    @ApiModelProperty(value = "位置")
    private String address;

    @ApiModelProperty(value = "面积平方米")
    private Integer area;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建者")
    private Long creator;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime addTime;

}
