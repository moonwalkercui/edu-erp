package com.hzb.erp.common.pojo.dto;

import com.hzb.erp.common.enums.AdvertisementTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AdvertisementSaveDTO extends PaginateDTO {
    private Long id;

    @NotBlank(message = "缺少标题")
    private String title;

    @NotBlank(message = "缺少内容")
    private String content;

    //    private List<String> cover;
    private String[] cover;

    private AdvertisementTypeEnum type;

}
