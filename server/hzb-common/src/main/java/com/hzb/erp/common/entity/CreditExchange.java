package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 积分商城兑换记录
 *
 * @TableName credit_exchange
 */
@TableName(value = "credit_exchange")
@Data
public class CreditExchange extends AutoFillEntity {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "学生id")
    @TableField(value = "student_id")
    private Long student_id;

    @ApiModelProperty(value = "学生账号id")
    @TableField(value = "user_id")
    private Long user_id;

    @ApiModelProperty(value = "兑换数量")
    @TableField(value = "amount")
    private Integer amount;

}