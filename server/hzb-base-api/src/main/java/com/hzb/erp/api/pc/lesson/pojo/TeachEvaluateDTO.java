package com.hzb.erp.api.pc.lesson.pojo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Ryan 541720500@qq.com
 * 学评教
 */
@Data
public class TeachEvaluateDTO {

    @NotNull(message = "缺少参数")
    private Long lessonId;

    @NotNull(message = "缺少综合评分")
    @Min(value = 1, message = "缺少综合评分")
    private Integer score1;

    @NotNull(message = "缺少课堂气氛评分")
    @Min(value = 1, message = "缺少课堂气氛评分")
    private Integer score2;

    @NotNull(message = "缺少授课态度评分")
    @Min(value = 1, message = "缺少授课态度评分")
    private Integer score3;

    @NotNull(message = "缺少教学效果评分")
    @Min(value = 1, message = "缺少教学效果评分")
    private Integer score4;

    private String content;

    private Boolean anonymity;
}
