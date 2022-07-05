package com.hzb.erp.api.pc.finance.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 交欠费参数
 *
 * @author 541720500@qq.com
 */
@Data
public class PayOverdueDTO extends PaginateDTO {
    @NotNull(message = "缺少ID参数")
    private Long id;
    @NotNull(message = "未设置金额")
    @Min(value = 1)
    private BigDecimal amount;
    private Long operator;

}
