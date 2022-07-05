package com.hzb.erp.api.pc.student.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hzb.erp.common.enums.ContactStageEnum;
import com.hzb.erp.common.enums.ContactTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 联系记录查询DTO
 */
@Data
public class ContactRecordVO {

    private Long id;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    private String studentName;

    @ApiModelProperty(value = "跟进记录")
    private String info;

    @ApiModelProperty(value = "联系时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime contactTime;

    @ApiModelProperty(value = "下次联系时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime contactNextTime;

    @ApiModelProperty(value = "联系的方式")
    private ContactTypeEnum contactType;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    private String staffName;

    @ApiModelProperty(value = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime addTime;

    @ApiModelProperty(value = "进展阶段")
    private ContactStageEnum stage;

}
