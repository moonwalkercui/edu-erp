package com.hzb.erp.wechat.entiry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.entity.AutoFillEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单实体
 * </p>
 *
 * @author Ryan
 */
@Data
@ApiModel(value = "订单实体", description = "订单实体")
public class WxOrderRefund implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "退款编号")
    private String refundSn;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundMoney;
}
