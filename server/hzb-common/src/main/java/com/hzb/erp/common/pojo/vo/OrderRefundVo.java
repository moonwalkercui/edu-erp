package com.hzb.erp.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.OrderRefundStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单退款VO
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
public class OrderRefundVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "退款单号")
    private String sn;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal money;

    @ApiModelProperty(value = "原因类型")
    private String reasonTypeText;

    @ApiModelProperty(value = "原因描述")
    private String reason;

    @ApiModelProperty(value = "状态")
    private OrderRefundStateEnum state;

    @ApiModelProperty(value = "审核人")
    private Long verifyId;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime verifyTime;

    @ApiModelProperty(value = "审核备注")
    private String verifyRemark;

}
