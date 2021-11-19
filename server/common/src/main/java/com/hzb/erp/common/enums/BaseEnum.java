package com.hzb.erp.common.enums;

/*
 * 枚举接口
 * 该接口有个作用是实现系统键码值列表
 * 见：CommonService.getDictList()
 * */
public interface BaseEnum {
    int getCode();

    String getDist();
}
