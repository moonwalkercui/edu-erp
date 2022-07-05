package com.hzb.erp.common.pojo;

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

    @ApiModelProperty(value = "上岗日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @ApiModelProperty(value = "解雇日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fireDate;

    @ApiModelProperty(value = "是否是管理员")
    private Boolean isManager;

    @ApiModelProperty(value = "课时费")
    private BigDecimal classFee;
    @ApiModelProperty(value = "助教课时费")
    private BigDecimal assistantFee;

    @ApiModelProperty(value = "岗位")
    private Long positionId;

    @ApiModelProperty(value = "所属集团")
    private String groupName;

    @ApiModelProperty(value = "所属公司")
    private String comName;

    @ApiModelProperty(value = "所属部门")
    private String dptName;

    @ApiModelProperty(value = "机构名全路径")
    private String orgNamePath;

    @ApiModelProperty(value = "机构Id")
    private String orgId;

    private String roleName;
    private String positionName;

    public Integer getAge() {
        return DateTool.getAgeByBirthday(this.getBirthday());
    }
}
