package com.hzb.erp.api.pc.clazz.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.hzb.erp.common.entity.AutoFillEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 成绩单
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Grade对象", description = "成绩单")
public class Grade extends AutoFillEntity {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "成绩项目标题")
    private String title;

    @ApiModelProperty(value = "成绩项目说明")
    private String info;

}
