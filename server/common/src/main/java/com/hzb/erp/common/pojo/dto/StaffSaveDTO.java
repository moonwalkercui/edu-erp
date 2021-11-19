package com.hzb.erp.common.pojo.dto;

import com.hzb.erp.common.enums.DegreeEnum;
import com.hzb.erp.common.enums.GenderEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class StaffSaveDTO {

    private Long id;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "手机号不能为空")
    private String mobile;
    private String school;
    private LocalDate birthday;
    private DegreeEnum degree;

    @NotNull(message = "请选择性别")
    private GenderEnum gender;

    private LocalDate hireDate;
    private String intro;
    private Boolean isManager;
    private BigDecimal classFee;
    private BigDecimal assistantFee;

    @NotNull(message = "缺少机构")
    private Long orgId;

    @NotNull(message = "缺少职位")
    private Long positionId;
    private Long creator;
}
