package com.hzb.erp.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 联系记录查询DTO
 */
@Data
public class CourseTrialVO {

    @ApiModelProperty(value="主键")
    private Long id;

    @ApiModelProperty(value="课程")
    private Long courseId;

    @ApiModelProperty(value="课程")
    private String courseName;

    @ApiModelProperty(value="名称")
    private String title;

    @ApiModelProperty(value="发行数量")
    private Integer quantity;

    @ApiModelProperty(value="剩余数量")
    private Integer remainingQuantity;

    @ApiModelProperty(value="课时数")
    private Integer lessonCount;

    @ApiModelProperty(value="领取后有效天数")
    private Integer expireDays;

    @ApiModelProperty(value="发行结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value="发布状态")
    private Boolean state;

    @ApiModelProperty(value = "编辑时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime editTime;

    @ApiModelProperty(value = "编辑者")
    private String editorName;

}
