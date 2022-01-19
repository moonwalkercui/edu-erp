package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 系统设置表
 *
 * @TableName setting
 */
@TableName(value = "setting")
@Data
public class Setting implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 组名
     */
    private String name;

    /**
     * 信息
     */
    private String remark;

    /**
     * 排序
     */
    private Integer sortNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}