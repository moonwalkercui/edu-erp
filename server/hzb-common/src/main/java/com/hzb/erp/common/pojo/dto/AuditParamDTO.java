package com.hzb.erp.common.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AuditParamDTO {
    @NotNull(message = "缺少参数IDS")
    @ApiModelProperty(value = "选中项")
    private List<Long> ids;

    @NotNull(message = "请选择审核状态")
    @ApiModelProperty(value = "审核状态")
    private Integer verifyState;

    @ApiModelProperty(value = "审核说明")
    private String verifyRemark;

    private Long staffId;

}
