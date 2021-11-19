package com.hzb.erp.common.entity.rbac;

import lombok.Data;

@Data
public class SysRolePermission {
    private Long id;
    private Long roleId;
    private Long permissionId;

}