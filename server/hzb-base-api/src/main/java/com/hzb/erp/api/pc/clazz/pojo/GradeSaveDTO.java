package com.hzb.erp.api.pc.clazz.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GradeSaveDTO extends PaginateDTO {
    private Long id;

    @NotBlank(message = "缺少标题")
    private String title;

    @NotBlank(message = "缺少说明")
    private String info;
}
