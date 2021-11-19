package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.OprationTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 审核记录
 * @TableName audit_record
 */
@TableName(value ="operation_record")
@Data
public class OperationRecord implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 类型
     */
    private OprationTypeEnum type;

    /**
     * 审核对象
     */
    private Long itemId;

    /**
     * 审核状态
     */
    private String state;

    @ApiModelProperty(value = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    /**
     * 审核备注
     */
    private String content;

    @ApiModelProperty(value = "添加时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}