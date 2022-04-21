package com.hzb.erp.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName staff_orginfo
 */
@TableName(value = "staff_orginfo")
@Data
public class StaffOrginfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 员工id
     */
    private Long staffId;

    /**
     * 所属机构
     */
    private Long orgId;

    /**
     * 所属集团
     */
    private Long groupId;

    /**
     * 所属分校公司
     */
    private Long comId;

    /**
     * 所属部门
     */
    private Long dptId;

    /**
     * 所属职位
     */
    private Long positionId;

    /**
     * 删除标记
     */
    private Boolean deleted;

    /**
     *
     */
    private LocalDateTime addTime;

    /**
     * id全路径
     */
    private String idPath;

    /**
     *
     */
    private Long creator;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}