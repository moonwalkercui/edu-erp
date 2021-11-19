package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName region
 */
@TableName(value ="region")
@Data
public class Region implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 名称
     */
    private String name;

    /**
     * 全称
     */
    private String mergerName;

    /**
     * 层级 0 1 2 省市区县
     */
    private Byte level;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 长途区号
     */
    private String code;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 首字母
     */
    private String first;

    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}