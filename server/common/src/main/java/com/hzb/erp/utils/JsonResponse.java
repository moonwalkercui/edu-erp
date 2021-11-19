package com.hzb.erp.utils;

import lombok.Data;

/**
 * description:
 *
 * @author Ryan
 */
@Data
public class JsonResponse<T> {

    /**
     * 状态码
     */
    private Integer errCode = 0;

    /**
     * 请求成功时返回的对象
     */
    private T data = null;

    /**
     * 提示信息
     */
    private String msg;

}