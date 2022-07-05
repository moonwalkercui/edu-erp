package com.hzb.erp.api.pc.clazz.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 班级学员表
 * </p>
 *
 * @author 541720500@qq.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ClassStudent对象", description = "班级学员表")
public class ClassStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "班级id")
    private Long classId;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime addTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private Long creator;

    /**
     * 删除标记  取消逻辑删除改为手动
     */
    private Boolean deleted;

    /**
     * 加入原因
     */
    private Byte reason;
    /**
     * 备注信息
     */
    private String remark;

    /**
     * 消费课程
     */
    private Long consumeCourseId;
}
