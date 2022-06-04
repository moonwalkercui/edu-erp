package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.OrderItemTypeEnum;
import com.hzb.erp.common.enums.OrderStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "订单实体", description = "订单实体")
@TableName(value = "shop_order")
public class Order extends AutoFillEntity {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String sn;

    @ApiModelProperty(value = "家长端账号ID")
    private Long userId;

    @ApiModelProperty(value = "学生ID")
    private Long studentId;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderMoney;

    @ApiModelProperty(value = "实际支付金额")
    private BigDecimal payMoney;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否已退款")
    private Boolean refunded;

    @ApiModelProperty(value = "订单状态")
    private OrderStateEnum state;

    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "取消时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime cancelTime;


}
