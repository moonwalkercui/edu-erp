package com.hzb.erp.api.pc.finance.pojo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CashoutSaveDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 请款类型
     */
    @NotNull(message = "缺少请款类型")
    private Long type;

    /**
     * 请款金额
     */
    @NotNull(message = "缺少请款金额")
    @Min(value = 1, message = "请款金额最小为1")
    private BigDecimal amount;
    /**
     * 收款人
     */
    @NotBlank(message = "缺少收款人")
    private String payeeName;

    @NotBlank(message = "缺少收款账户")
    private String account;

    @NotBlank(message = "缺少请款项目名称")
    private String title;
    private List<Long> attachFile;
    /**
     * 请款信息
     */
    private String info;

}
