package com.hzb.erp.api.pc.student.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.hzb.erp.common.enums.ContactStageEnum;
import com.hzb.erp.common.enums.ContactTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 学员跟进表
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ContactRecord对象", description = "学员跟进表")
public class ContactRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "跟进记录")
    private String info;

    @ApiModelProperty(value = "联系时间")
    private LocalDateTime contactTime;

    @ApiModelProperty(value = "下次联系时间")
    private LocalDateTime contactNextTime;

    @ApiModelProperty(value = "联系的方式")
    private ContactTypeEnum contactType;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    @ApiModelProperty(value = "添加时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime addTime;

    @ApiModelProperty(value = "进展阶段")
    private ContactStageEnum stage;

    @TableLogic
    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;
}
