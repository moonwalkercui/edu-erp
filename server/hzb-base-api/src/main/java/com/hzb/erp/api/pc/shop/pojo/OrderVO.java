package com.hzb.erp.api.pc.shop.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.api.pc.shop.entity.OrderItem;
import com.hzb.erp.common.enums.OrderStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单VO
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
public class OrderVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String sn;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderMoney;

    @ApiModelProperty(value = "支付金额")
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

    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "购买内容")
    private List<OrderItem> itemList;

    @ApiModelProperty(value = "退款信息")
    private List<OrderRefundVo> refundList;

    @ApiModelProperty(value = "是否可以退款")
    private Boolean cancelAble;
}
