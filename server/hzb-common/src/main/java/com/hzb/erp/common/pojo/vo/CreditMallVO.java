package com.hzb.erp.common.pojo.vo;

import com.hzb.erp.common.entity.CreditMall;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 积分礼品
 * </p>
 *
 * @author Ryan
 */
@Data
@ApiModel(value = "CreditMallVO对象", description = "积分礼品")
public class CreditMallVO extends CreditMall {

    @ApiModelProperty(value = "图片")
    private String cover;

    @ApiModelProperty(value = "所属分校名")
    private String schoolName;

    @ApiModelProperty(value = "库存")
    private Integer storage;

}
