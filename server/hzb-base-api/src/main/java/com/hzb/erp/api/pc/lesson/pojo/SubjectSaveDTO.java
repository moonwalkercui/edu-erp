package com.hzb.erp.api.pc.lesson.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SubjectSaveDTO extends PaginateDTO {
    private Long id;
    @NotBlank(message = "缺少名称")
    private String name;
    private String info;
    private Integer sortNum;
}
