package com.hzb.erp.common.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
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

    @ApiModelProperty(value = "库存")
    private Integer storage;

}
