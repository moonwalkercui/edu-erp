package com.hzb.erp.common.entity.rbac;

import lombok.Data;

@Data
public class SysStudent {
    private Integer id;
    private String username;
    private String password;
    private Short state;
}