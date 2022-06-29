package com.hzb.erp.studentMobile.pojo.vo;

import com.hzb.erp.common.enums.GenderEnum;
import com.hzb.erp.common.enums.StudentStageEnum;
import com.hzb.erp.utils.DateTool;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class StudentVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "学习阶段")
    private StudentStageEnum stage;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别")
    private GenderEnum gender;

    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

    @ApiModelProperty(value = "头像")
    private String headImg;

    @ApiModelProperty(value = "剩余积分")
    private Integer credit;

    public Integer getAge() {
        return DateTool.getAgeByBirthday(this.getBirthday());
    }

}
