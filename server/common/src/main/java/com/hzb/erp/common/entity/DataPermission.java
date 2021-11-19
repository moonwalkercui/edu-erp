package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hzb.erp.common.enums.DataScopeTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName data_permission
 */
@TableName(value ="data_permission")
@Data
public class DataPermission implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 岗位id
     */
    private Long positionId;

    /**
     * 表名
     */
    private String entityName;
    private String ownerField;
    private String info;

    /**
     * 
     */
    private DataScopeTypeEnum scopeType;

    @TableField(exist = false)
    private List<DataPermissionCustom> customList;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}