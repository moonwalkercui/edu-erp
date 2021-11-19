package com.hzb.erp.common.enums;

import lombok.Getter;

/*
 * 数据库存储状态
 * */
@Getter
public enum DbExistStateEnum {
    /*
     * 不存在
     * */
    NONE,
    /*
     * 被删除了
     * */
    DELETED,
    /*
     * 存在
     * */
    EXIST;
}
