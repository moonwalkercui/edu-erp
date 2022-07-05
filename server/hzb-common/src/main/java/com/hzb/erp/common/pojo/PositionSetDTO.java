package com.hzb.erp.common.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Ryan 541720500@qq.com
 * 设置员工岗位
 */
@Data
public class PositionSetDTO {
    @NotNull(message = "缺少参数:员工")
    private Long staffId;
    @NotNull(message = "缺少参数:岗位")
    private Long positionId;
    @NotNull(message = "缺少参数:机构")
    private Long orgId;
    private Long creator;
}
