package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据权限自定义设置
 * @TableName data_permission_custom
 */
@TableName(value ="data_permission_custom")
@Data
public class DataPermissionCustom implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 权限id
     */
    private Long permissionId;

    /**
     * 机构id
     */
    private Long orgId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}