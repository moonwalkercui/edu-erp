package com.hzb.erp.utils;

import com.hzb.erp.service.bo.TreeListBO;

import java.util.LinkedList;
import java.util.List;

/**
 * <p> 递归查找父级 </p>
 * <p>
 * 通过通过结果集某一条数据的父级id来递归查找一直到根级
 * 用法如下：
 * ParentListBuilder<Org> treeUtil = new ParentListBuilder<>(records);
 * treeUtil.build(11L);
 * List<Org> res = treeUtil.getRes();
 * </p>
 *
 * @author Ryan 541720500@qq.com
 */
public class ParentListBuilder<T extends TreeListBO> {

    List<T> res;
    List<T> dataList;

    public ParentListBuilder(List<T> records) {
        res = new LinkedList<>();
        dataList = records;
    }

    public void building(Long pid) {
        for (T item : dataList) {
            if (item.getId().equals(pid)) {
                res.add(0, item);
                building(item.getPid());
                break;
            }
        }
    }

    public List<T> getRes() {
        return res;
    }
}
