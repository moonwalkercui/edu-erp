package com.hzb.erp.common.entity.rbac;

import lombok.Data;

@Data
public class SysUserRole {
    private Long id;
    private Long userId;
    private Long roleId;
}