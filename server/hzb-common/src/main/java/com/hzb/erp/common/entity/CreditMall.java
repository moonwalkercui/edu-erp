package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 积分商城礼品表
 * @TableName credit_mall
 */
@TableName(value ="credit_mall")
@Data
public class CreditMall extends AutoFillEntity {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "物料id")
    @TableField(value = "material_id")
    private Long material_id;

    @ApiModelProperty(value = "分类id")
    @TableField(value = "category_id")
    private Long category_id;

    @ApiModelProperty(value = "礼品名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "兑换所需积分")
    @TableField(value = "credit")
    private Integer credit;

    @ApiModelProperty(value = "历史兑换数量")
    @TableField(value = "sale_num")
    private Integer sale_num;

    @ApiModelProperty(value = "预览次数")
    @TableField(value = "view_num")
    private Integer view_num;

    @ApiModelProperty(value = "详情内容")
    @TableField(value = "content")
    private String content;

    @ApiModelProperty(value = "是否可以兑换")
    @TableField(value = "state")
    private Boolean state;

}