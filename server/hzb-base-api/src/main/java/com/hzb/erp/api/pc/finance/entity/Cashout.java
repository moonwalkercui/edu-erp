package com.hzb.erp.api.pc.finance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.entity.AutoFillEntity;
import com.hzb.erp.common.enums.VerifyStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 请款表
 *
 * @TableName cashout
 */
@TableName(value = "cashout")
@Data
public class Cashout extends AutoFillEntity {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 请款类型
     */
    @ApiModelProperty(value = "请款类型")
    private Long type;

    /**
     * 请款金额
     */
    @ApiModelProperty(value = "请款金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "收款账号")
    private String account;

    @ApiModelProperty(value = "附件")
    private String attachFile;

    /**
     * 收款人
     */
    @ApiModelProperty(value = "收款人")
    private String payeeName;

    @ApiModelProperty(value = "借款项名称")
    private String title;
    /**
     * 请款信息
     */
    @ApiModelProperty(value = "请款信息")
    private String info;

    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    private VerifyStateEnum verifyState;

    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人")
    private Long verifyStaff;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime verifyTime;

    /**
     * 审核备注
     */
    @ApiModelProperty(value = "审核备注")
    private String verifyRemark;

}