package com.hzb.erp.common.entity;

import com.hzb.erp.common.enums.VerifyStateEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * description 审核积累
 * @author Ryan 541720500@qq.com
 * @since 2022/07/02
 */
@Data
public class AuditBaseEntity {

    @ApiModelProperty(value = "审核确认状态")
    private VerifyStateEnum verifyState;

    @ApiModelProperty(value = "审核人")
    private Long verifyStaff;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime verifyTime;

    @ApiModelProperty(value = "审核备注")
    private String verifyRemark;
}
