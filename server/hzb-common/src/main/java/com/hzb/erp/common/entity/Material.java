package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hzb.erp.common.enums.SwitchEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 物料表
 * @TableName material
 */
@TableName(value ="material")
@Data
public class Material extends AutoFillEntity {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "所属学校")
    @TableField(value = "school_id")
    @NotNull(message = "缺少所属学校")
    private Long schoolId;

    @ApiModelProperty(value = "物料名称")
    @TableField(value = "name")
    @NotEmpty(message = "缺少物料名称")
    private String name;

    @ApiModelProperty(value = "分类id")
    @TableField(value = "category_id")
    private Long categoryId;

    @ApiModelProperty(value = "分类名")
    @TableField(value = "category_name")
    private String categoryName;

    @ApiModelProperty(value = "库存量")
    @TableField(value = "storage")
    private Integer storage;

    @ApiModelProperty(value = "物料说明")
    @TableField(value = "info")
    private String info;

    @ApiModelProperty(value = "封面图")
    @TableField(value = "cover")
    private String cover;

    @ApiModelProperty(value = "状态")
    @TableField(value = "state")
    private SwitchEnum state;

}