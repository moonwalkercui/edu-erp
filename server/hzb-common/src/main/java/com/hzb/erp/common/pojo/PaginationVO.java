package com.hzb.erp.common.pojo;

import lombok.Data;

import java.util.List;

/**
 * 分页
 */
@Data
public class PaginationVO {
    private List<?> records;
    private Long total;
    private Long page;
    private Long pageSize;
    private Long pageCount;
}
