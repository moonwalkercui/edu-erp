package com.hzb.erp.api.pc.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.MessageUserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 站内信
 *
 * @TableName message
 */
@TableName(value = "message")
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 接收人ID 0所有
     */
    @ApiModelProperty(value = "接收人ID")
    private Long toId;

    /**
     * 发送人ID
     */
    @ApiModelProperty(value = "发送人ID")
    private Long fromId;

    /**
     * 消息相关用户身份
     */
    @ApiModelProperty(value = "消息相关用户身份")
    private MessageUserTypeEnum toType;
    private MessageUserTypeEnum fromType;

    /**
     * 发送时间
     */
    @ApiModelProperty(value = "发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "添加时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    @TableLogic
    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;
}