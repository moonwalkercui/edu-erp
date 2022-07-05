package com.hzb.erp.api.pc.clazz.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClassroomSaveDTO {
    private Long id;

    @NotBlank(message = "未输入名称")
    private String name;

    @NotBlank(message = "未输入地址")
    private String address;

    private Integer area;
    private String remark;

}
