package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hzb.erp.common.enums.FinanceStateEnum;
import com.hzb.erp.common.enums.FinanceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 财务记录表
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FinanceRecord对象", description = "财务记录表")
public class FinanceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "金额,退款为负数")
    private BigDecimal amount;

    @ApiModelProperty(value = "收入项类型")
    private FinanceTypeEnum itemType;

    @ApiModelProperty(value = "项目id")
    private Long itemId;

    @ApiModelProperty(value = "经办人")
    private Long operator;

    @ApiModelProperty(value = "付款人")
    private Long payer;

    @ApiModelProperty(value = "款项备注")
    private String remark;

    @ApiModelProperty(value = "经办日期")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "审核确认状态")
    private FinanceStateEnum verifyState;

    @ApiModelProperty(value = "审核人")
    private Long verifyStaff;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime verifyTime;

    @ApiModelProperty(value = "审核备注")
    private String verifyRemark;

}
