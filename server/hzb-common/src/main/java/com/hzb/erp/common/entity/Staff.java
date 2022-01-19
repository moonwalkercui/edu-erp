package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.DegreeEnum;
import com.hzb.erp.common.enums.GenderEnum;
import com.hzb.erp.common.enums.StaffStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 教师员工表
 * </p>
 *
 * @author Ryan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Staff对象", description = "教师员工表")
public class Staff extends AutoFillEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @ApiModelProperty(value = "性别")
    private GenderEnum gender;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "毕业学校")
    private String school;

    @ApiModelProperty(value = "学历")
    private DegreeEnum degree;

    @ApiModelProperty(value = "备注")
    private String remark;
    private String intro;

    @ApiModelProperty(value = "在职状态")
    private StaffStateEnum state;

    @ApiModelProperty(value = "是否是管理者")
    private Boolean isManager;

    @ApiModelProperty(value = "头像")
    private String headImg;

    @ApiModelProperty(value = "入职日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @ApiModelProperty(value = "离职日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fireDate;

    @ApiModelProperty(value = "课时费")
    private BigDecimal classFee;

    @ApiModelProperty(value = "助教费")
    private BigDecimal assistantFee;

    @ApiModelProperty(value = "微信登录记录表id")
    private Long wxAccessId;

}
