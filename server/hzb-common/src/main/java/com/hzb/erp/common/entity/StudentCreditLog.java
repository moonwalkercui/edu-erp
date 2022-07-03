package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.StudentCreditChangeTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学生积分变动记录
 * @TableName student_credit_log
 */
@TableName(value ="student_credit_log")
@Data
public class StudentCreditLog implements Serializable {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "学生id")
    @TableField(value = "student_id")
    private Long studentId;

    @ApiModelProperty(value = "学生账号id")
    @TableField(value = "user_id")
    private Long userId;

    @ApiModelProperty(value = "变动数量")
    @TableField(value = "credit")
    private Integer credit;

    @ApiModelProperty(value = "剩余积分数量")
    @TableField(value = "current_credit")
    private Integer currentCredit;

    @ApiModelProperty(value = "变动类型")
    @TableField(value = "change_type")
    private StudentCreditChangeTypeEnum changeType;

    @ApiModelProperty(value = "来源id")
    @TableField(value = "source_id")
    private Long sourceId;

    @ApiModelProperty(value = "变动时间")
    @TableField(value = "add_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "其他说明")
    @TableField(value = "remark")
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}