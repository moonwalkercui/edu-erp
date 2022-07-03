package com.hzb.erp.common.pojo.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
* 出入库传输对象
* */
@Data
public class MaterialStorageDTO extends PaginateDTO {

    @NotNull(message = "缺少ID")
    private Long id;

    @NotNull(message = "请输入数量")
    @Min(value = 1, message = "数量最小为1")
    private Integer storage;

    @NotEmpty(message = "请输入说明")
    private String reason;

    private Boolean inStorage;

}
