package com.hzb.erp.api.pc.material.pojo;

import com.hzb.erp.api.pc.material.entity.MaterialRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 物料出入库记录
 */
@Data
public class MaterialRecordVO extends MaterialRecord {

    @ApiModelProperty(value = "学生姓名")
    private String studentName;
    @ApiModelProperty(value = "员工姓名")
    private String staffName;
    @ApiModelProperty(value = "物料名")
    private String materialName;

}
