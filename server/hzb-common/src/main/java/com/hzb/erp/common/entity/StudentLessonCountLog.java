package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hzb.erp.common.enums.LessonCountChangeStageEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学员课次变更记录表
 *
 * @TableName student_lesson_count_log
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "StudentLessonCountLog对象", description = "学员课次变更记录表")
public class StudentLessonCountLog implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学员id
     */
    private Long studentId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 课次id
     */
    private Long lessonId;

    /**
     * 变更课次数
     */
    private Integer changeCount;

    /**
     * 剩余数量
     */
    private Integer remainingCount;

    /**
     * 操作人
     */
    private Long staffId;

    /**
     * 时间
     */
    private LocalDateTime addTime;

    /**
     * 变更阶段
     */
    private LessonCountChangeStageEnum stage;

    private String remark;

    private static final long serialVersionUID = 1L;


}