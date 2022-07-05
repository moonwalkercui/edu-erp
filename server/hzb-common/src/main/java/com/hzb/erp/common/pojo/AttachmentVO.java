package com.hzb.erp.common.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttachmentVO {
    private Long id;
    @ApiModelProperty(value = "附件名")
    private String fileName;
    @ApiModelProperty(value = "文件扩展名")
    private String ext;
    @ApiModelProperty(value = "文件大小")
    private Integer fileSize;
    @ApiModelProperty(value = "存储路径")
    private String path;
}
