package com.hzb.erp.common.pojo;

import lombok.Data;

/**
 * 通用参数接收类
 */

@Data
public class CommonParamDTO extends PaginateDTO {
    private String name;
    private String title;
}
