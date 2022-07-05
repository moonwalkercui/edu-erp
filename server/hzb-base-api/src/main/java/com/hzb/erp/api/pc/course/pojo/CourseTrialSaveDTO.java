package com.hzb.erp.api.pc.course.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 体验卡查询DTO
 */
@Data
public class CourseTrialSaveDTO {

    @ApiModelProperty(value="主键")
    private Long id;

    @NotNull(message = "未选择课程")
    @ApiModelProperty(value="课程")
    private Long courseId;

    @NotBlank(message = "缺少名称")
    @ApiModelProperty(value="名称")
    private String title;

    @NotNull(message = "缺少发行数量")
    @Min(value = 0, message = "发行数量最小为0")
    @ApiModelProperty(value="发行数量")
    private Integer quantity;

    @NotNull(message = "缺少课时数")
    @Min(value = 1, message = "课时数最小为1")
    @ApiModelProperty(value="课时数")
    private Integer lessonCount;

    @ApiModelProperty(value="领取后有效天数")
    private Integer expireDays;

    @ApiModelProperty(value="结束发行日期")
    private LocalDate endDate;

    @ApiModelProperty(value="启用状态")
    private Boolean state;
}
