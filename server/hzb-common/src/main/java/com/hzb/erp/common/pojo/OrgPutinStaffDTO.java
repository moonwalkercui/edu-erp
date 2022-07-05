package com.hzb.erp.common.pojo;

import com.hzb.erp.common.entity.Org;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 给人员放入机构
 */

@Data
public class OrgPutinStaffDTO extends Org {

    @NotNull(message = "缺少机构")
    private Long orgId;

    @NotEmpty(message = "缺少人员")
    private Long[] staffIds;

}
