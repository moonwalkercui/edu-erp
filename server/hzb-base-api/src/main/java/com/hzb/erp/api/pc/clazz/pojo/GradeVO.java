package com.hzb.erp.api.pc.clazz.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 成绩单
 * </p>
 *
 * @author Ryan
 */
@Data
@ApiModel(value = "GradeVO对象", description = "成绩单")
public class GradeVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "说明")
    private String info;

    @ApiModelProperty(value = "创建者")
    private Long creator;

    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addTime;

}
