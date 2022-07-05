package com.hzb.erp.common.pojo;

import com.hzb.erp.common.enums.DataScopeTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Ryan 541720500@qq.com
 * 数据权限保存
 */
@Data
public class DataPermissionSaveDTO {
    private Long id;
    @NotNull(message = "参数错误")
    private Long positionId;
    @NotBlank(message = "缺少实体表名")
    private String entityName;
    private String ownerField;
    @NotNull(message = "缺少数据权限类型")
    private DataScopeTypeEnum scopeType;
    private List<Long> orgIds;
    private String info;
}
