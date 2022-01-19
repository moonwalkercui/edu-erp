package com.hzb.erp.common.pojo.dto;

import lombok.Data;
import org.apache.poi.hpsf.Decimal;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

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
