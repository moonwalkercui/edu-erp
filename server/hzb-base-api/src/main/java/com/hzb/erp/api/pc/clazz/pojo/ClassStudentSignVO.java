package com.hzb.erp.api.pc.clazz.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.SignStateEnum;
import com.hzb.erp.common.enums.SignTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class ClassStudentSignVO {

    /**
     * 可空 在没有生成签到记录时id为空
     */
    private Long id;
    private Long studentId;
    private Long lessonId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime signTime;
    private SignStateEnum signState;
    private SignTypeEnum signType;
    private Integer decLessonCount;
    private String studentName;
    private String mobile;
    private Integer consumeCourseId;
    private String consumeCourseName;
    private Long classId;
    private String className;

    /**
     * 当前剩余课时（动态）
     */
    private Integer countLessonRemaining;

}
