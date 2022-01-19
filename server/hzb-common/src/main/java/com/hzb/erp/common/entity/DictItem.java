package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 系统设置表
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DictItem对象", description = "系统设置表")
public class DictItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "组id")
    private Long dictId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "说明")
    private String info;

    @ApiModelProperty(value = "排序")
    private Integer sortNum;


}
