package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hzb.erp.common.enums.MaterialRecordTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 物料出入库记录
 *
 * @TableName material_record
 */
@TableName(value = "material_record")
@Data
public class MaterialRecord extends AutoFillEntity {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "物料id")
    @TableField(value = "material_id")
    private Long materialId;

    @ApiModelProperty(value = "涉及员工id")
    @TableField(value = "staff_id")
    private Long staffId;

    @ApiModelProperty(value = "涉及学生id")
    @TableField(value = "student_id")
    private Long studentId;

    @ApiModelProperty(value = "变动数量")
    @TableField(value = "amount")
    private Integer amount;

    @ApiModelProperty(value = "变动原因")
    @TableField(value = "reason")
    private String reason;

    @ApiModelProperty(value = "变动类型1积分商城")
    @TableField(value = "change_type")
    private MaterialRecordTypeEnum changeType;

    @ApiModelProperty(value = "变动对象id如积分商城里的商品id")
    @TableField(value = "change_target_id")
    private Long changeTargetId;

    @ApiModelProperty(value = "备注信息")
    @TableField(value = "remark")
    private String remark;

}