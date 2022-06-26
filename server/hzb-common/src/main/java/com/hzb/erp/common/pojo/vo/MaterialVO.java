package com.hzb.erp.common.pojo.vo;

import com.hzb.erp.common.entity.Material;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 物料
 * </p>
 *
 * @author Ryan
 */
@Data
@ApiModel(value = "物料对象", description = "物料")
public class MaterialVO extends Material {

    @ApiModelProperty(value = "所属学校")
    private String schoolName;

}
