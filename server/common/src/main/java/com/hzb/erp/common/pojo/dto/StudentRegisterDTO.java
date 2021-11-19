package com.hzb.erp.common.pojo.dto;

import com.hzb.erp.common.enums.FamilyRelationshipEnum;
import com.hzb.erp.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Ryan 541720500@qq.com
 * 添加学员
 */
@Data
public class StudentRegisterDTO {

    private Long id;
    @NotBlank(message = "缺少姓名")
    private String name;
    @NotNull(message = "缺少性别")
    private GenderEnum gender;
    @ApiModelProperty(value = "家庭关系")
    private FamilyRelationshipEnum familyRel;
    private String idcard;
    @NotNull(message = "缺少生日")
    private LocalDate birthday;
    private String headImg;
    private Long joinWay;

}
