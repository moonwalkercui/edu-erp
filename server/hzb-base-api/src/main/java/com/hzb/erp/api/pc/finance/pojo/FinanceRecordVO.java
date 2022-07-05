package com.hzb.erp.api.pc.finance.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.FinanceStateEnum;
import com.hzb.erp.common.enums.FinanceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 财务记录vo
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
public class FinanceRecordVO implements Serializable {

    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "金额,退款为负数")
    private BigDecimal amount;

    @ApiModelProperty(value = "收入项类型")
    private FinanceTypeEnum itemType;

    @ApiModelProperty(value = "经办人")
    private String operatorName;

    @ApiModelProperty(value = "付款学生姓名")
    private String payerName;

    @ApiModelProperty(value = "学生id")
    private Long payer;

    @ApiModelProperty(value = "款项备注")
    private String remark;

    @ApiModelProperty(value = "经办日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "审核确认状态")
    private FinanceStateEnum verifyState;

    @ApiModelProperty(value = "审核人")
    private String verifyStaffName;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime verifyTime;

    @ApiModelProperty(value = "审核备注")
    private String verifyRemark;

}
