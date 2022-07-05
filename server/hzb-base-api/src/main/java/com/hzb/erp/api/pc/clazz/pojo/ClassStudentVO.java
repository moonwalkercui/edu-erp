package com.hzb.erp.api.pc.clazz.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.api.pc.clazz.entity.ClassStudent;
import com.hzb.erp.common.enums.GenderEnum;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.enums.SignTypeEnum;
import com.hzb.erp.utils.DateTool;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class ClassStudentVO extends ClassStudent {

    private Long id;
    private Long studentId;
    private Long classId;
    private String name;
    private String mobile;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private GenderEnum gender;
    private String className;

    //    private Integer countLessonTotal;
//    private Integer countLessonComplete;
//    private Integer countLessonRefund;
    private Integer countLessonRemaining;

    //    private Integer decCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime signTime;
    private SignStateEnum signState;
    private SignTypeEnum signType;
    private Long lessonId;
    /**
     * 班级里的默认扣课id
     */
    private Long consumeCourseId;
    private String consumeCourseName;

    private Integer decLessonCount;

    public Integer getAge() {
        return DateTool.getAgeByBirthday(this.getBirthday());
    }

    private Boolean warning;

    public Boolean getWarning() {
        return countLessonRemaining == null ? null : countLessonRemaining <= 5;
    }
}
