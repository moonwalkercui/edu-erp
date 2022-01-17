package com.hzb.erp.common.entity.rbac;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysPermissionDTO implements Serializable {
    private Long id;
    private String name;
    private String url;
    private String path;
    private String parentId;
    private Short enabled;
    private List<SysRole> roles;
}