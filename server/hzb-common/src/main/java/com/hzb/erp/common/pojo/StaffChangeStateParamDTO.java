package com.hzb.erp.common.pojo;

import com.hzb.erp.common.enums.StaffStateEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class StaffChangeStateParamDTO {

    @NotNull(message = "参数不能为空")
    @Size(min = 1)
    private List<Long> id;

    @NotNull(message = "缺少解聘时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private StaffStateEnum state;

}
