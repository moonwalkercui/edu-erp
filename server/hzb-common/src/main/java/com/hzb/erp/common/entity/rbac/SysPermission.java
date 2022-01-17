package com.hzb.erp.common.entity.rbac;

import lombok.Data;

@Data
public class SysPermission {
    private Long id;
    private String name;
    private String url;
    private String path;
    private String code;
    private String parentId;
    private String groupName;
    private Short enabled;
}