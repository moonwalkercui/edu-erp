package com.hzb.erp.common.pojo;

import lombok.Data;

/**
 * 分页基类
 *
 * @author 541720500@qq.com
 */
@Data
public class PaginateDTO {
    private Integer page;
    private Integer pageSize = 20;

    /**
     * 排序字段
     */
    private String sortBy;

    /**
     * 排序类型
     */
    private String sortType;
}
