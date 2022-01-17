package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 批量绑定角色和用户
 */
@Data
public class BatchBindRoleToUserDTO extends PaginateDTO {
    @NotEmpty(message = "未选择人员")
    private List<Long> staffIds;
    @NotEmpty(message = "未选择角色")
    private List<Long> roleIds;
}
