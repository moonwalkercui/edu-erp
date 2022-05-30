package com.hzb.erp.studentMobile.pojo.vo;

import com.hzb.erp.common.enums.GenderEnum;
import com.hzb.erp.common.enums.StudentStageEnum;
import com.hzb.erp.utils.DateTool;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class StudentVo {
    private Long id;
    private String name;
    private StudentStageEnum stage;
    private Integer age;
    private GenderEnum gender;
    private LocalDate birthday;
    private String headImg;

    public Integer getAge() {
        return DateTool.getAgeByBirthday(this.getBirthday());
    }

}
