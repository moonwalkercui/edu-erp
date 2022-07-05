package com.hzb.erp.api.pc.course.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hzb.erp.common.entity.AutoFillEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * CourseTrial课程体验卡
 * @author Ryan
 */
@ApiModel(value="com.hzb.erp.CourseTrial课程体验卡")
@Data
public class CourseTrial extends AutoFillEntity {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程
     */
    @ApiModelProperty(value="课程")
    private Long courseId;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String title;

    /**
     * 发行数量
     */
    @ApiModelProperty(value="发行数量")
    private Integer quantity;

    /**
     * 课时数
     */
    @ApiModelProperty(value="课时数")
    private Integer lessonCount;

    /**
     * 领取后有效天数
     */
    @ApiModelProperty(value="领取后有效天数")
    private Integer expireDays;

    /**
     * 发行结束日期
     */
    @ApiModelProperty(value="发行结束日期")
    private LocalDate endDate;

    /**
     * 发布状态
     */
    @ApiModelProperty(value="发布状态")
    private Boolean state;

}