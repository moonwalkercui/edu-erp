package com.hzb.erp.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.DegreeEnum;
import com.hzb.erp.common.enums.GenderEnum;
import com.hzb.erp.common.enums.StaffStateEnum;
import com.hzb.erp.utils.DateTool;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StaffVO {
    private Long id;
    private String name;
    private String mobile;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private GenderEnum gender;
    private String idCard;

    private String school;
    private DegreeEnum degree;
    private StaffStateEnum state;
    private String remark;
    private String intro;
    @ApiModelProperty(value = "头像")
    private String headImg;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fireDate;
    private Boolean isManager;
    private BigDecimal classFee;
    private BigDecimal assistantFee;

    public Integer getAge() {
        return DateTool.getAgeByBirthday(this.getBirthday());
    }

    private Long positionId;
    private String groupName;
    private String comName;
    private String dptName;
    private String roleName;
    private String positionName;
}
