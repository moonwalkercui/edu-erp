package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName teach_evaluation
 */
@TableName(value = "teach_evaluation")
@Data
public class TeachEvaluation implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 课次id
     */
    private Long lessonId;

    /**
     * 老师id
     */
    private Long teacherId;

    /**
     * 综合评分
     */
    private Integer score1;

    /**
     * 课堂气氛
     */
    private Integer score2;

    /**
     * 授课态度
     */
    private Integer score3;

    /**
     * 教学效果
     */
    private Integer score4;

    /**
     * 评语
     */
    private String content;

    /**
     * 评价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 是否匿名
     */
    private Boolean anonymity;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}