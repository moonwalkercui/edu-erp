package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职位岗位
 *
 * @TableName position
 */
@TableName(value = "position")
@Data
public class Position implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 职务id
     */
    private String name;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 编辑人
     */
    private Long editor;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 删除标记
     */
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}