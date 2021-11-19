package com.hzb.erp.common.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限函数类型
 * @author Ryan
 */
@Getter
@AllArgsConstructor
public enum DataScopeTypeEnum implements BaseEnum {

    /**
     * 查询数据
     * SELECT * FROM (originSql) temp_data_scope WHERE temp_data_scope.creator IN
     * (select staff_id from staff_orginfo where org_id in (
     *  select id from org where id = ?1 or find_in_set(?1, id_path)
     * ))
     * ?1 用户所属机构id
     */
    ALL(0, "全部"),
    GROUP(1, "本集团数据"),
    COM(2, "本校数据"),
    DPT(3, "本部门数据"),
    SELF(4, "自己的数据"),
    CUSTOM(10, "自定义");

    @EnumValue
    private final int code;
    @JsonValue
    private final String dist;

}