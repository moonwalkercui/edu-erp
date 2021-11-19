package com.hzb.erp.common.entity.rbac;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("staff")
public class SysUser {
    private Integer id;
    private String username;
    private String password;
    private Short state;
}