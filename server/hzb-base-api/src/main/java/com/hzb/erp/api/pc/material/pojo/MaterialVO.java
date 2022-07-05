package com.hzb.erp.api.pc.material.pojo;

import com.hzb.erp.api.pc.material.entity.Material;
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

    @ApiModelProperty(value = "更新人")
    private String editorName;
}
