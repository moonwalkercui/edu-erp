package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统设置表
 *
 * @TableName setting_option
 */
@TableName(value = "setting_option")
@Data
public class SettingOption implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 组id
     */
    private Long settingId;

    /**
     * 名称
     */
    private String name;
    /**
     * 码
     */
    private String code;

    /**
     * 值
     */
    private String value;

    /**
     * 值数据类型
     */
    private String valueType;

    /**
     * 说明
     */
    private String info;

    /**
     * 排序
     */
    private Integer sortNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}