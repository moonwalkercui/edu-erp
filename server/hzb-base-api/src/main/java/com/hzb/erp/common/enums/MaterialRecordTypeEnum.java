package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hzb.erp.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 物资变更记录类型
 *
 * @author 541720500@qq.com
 */

@Getter
@AllArgsConstructor
public enum MaterialRecordTypeEnum implements BaseEnum {
    IN(1, "入库"),
    OUT(2, "出库"),
    CREDIT_MALL(3, "积分商城兑换礼品");

    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
