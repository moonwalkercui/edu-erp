package com.hzb.erp.service.bo;

/**
 * <p>
 *     树形列表数据对象
 *     操作方法是treeListUtil
 * </p>
 *
 * @author Ryan 541720500@qq.com
 */
public interface TreeListBO {

    /**
     * id
     */
    Long getId();

    /**
     * 父级id
     */
    Long getPid();

    /**
     * 名称
     */
    String getName();
}
