package com.hzb.erp.api.pc.sys.pojo;

import com.hzb.erp.common.pojo.PaginateDTO;
import lombok.Data;

/**
 * 作业列表查询DTO
 */
@Data
public class AdvertisementParamDTO extends PaginateDTO {
    private Long creator;
    private String title;
}
