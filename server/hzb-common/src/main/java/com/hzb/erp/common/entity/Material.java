package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 物料表
 * @TableName material
 */
@TableName(value ="material")
@Data
public class Material extends AutoFillEntity {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属学校
     */
    @ApiModelProperty(value = "所属学校")
    @TableField(value = "school_id")
    private Long school_id;

    /**
     * 物料名称
     */
    @ApiModelProperty(value = "物料名称")
    @TableField(value = "name")
    private String name;

    /**
     * 库存量
     */
    @ApiModelProperty(value = "库存量")
    @TableField(value = "storage")
    private Integer storage;

    /**
     * 物料说明
     */
    @ApiModelProperty(value = "物料说明")
    @TableField(value = "info")
    private String info;

    /**
     * 封面图
     */
    @ApiModelProperty(value = "封面图")
    @TableField(value = "cover")
    private String cover;

}