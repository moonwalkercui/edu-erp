package com.hzb.erp.common.pojo.dto;

import lombok.Data;

/**
 * 作业列表查询DTO
 */
@Data
public class AdvertisementParamDTO extends PaginateDTO {
    private Long creator;
    private String title;
}
