package com.hzb.erp.service.bo;

import lombok.Data;
/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
public class UploadValidateBO {
    private Long maxSize;
    private String acceptExt;
    private Boolean isImage;
}
