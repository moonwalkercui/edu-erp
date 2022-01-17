package com.hzb.erp.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 家庭关系
 */
@Getter
@AllArgsConstructor
public enum FamilyRelationshipEnum implements BaseEnum {
    NONE(0, "无"),
    FATHER(1, "爸爸"),
    MOTHER(2, "妈妈"),
    GRANDFATHER(3, "爷爷"),
    GRANDMOTHER(4, "奶奶"),
    GRANDPA(5, "姥爷"),
    GRANDMA(6, "姥姥"),
    UNCLE(7, "叔叔"),
    AUNT(8, "姑姑"),
    OTHER(9, "其他");
    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;
}
