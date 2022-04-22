package com.hzb.erp.service;

import com.hzb.erp.service.bo.TreeListBO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * <p>
 * 递归获取层级数据工具
 * 适用于实现了 TreeListBO 接口的List
 * </p>
 * TreeListBuilder targetObj = new TreeListBuilder(newList);
 * targetObj.build(4L);
 * System.out.println(targetObj.idPath());
 * System.out.println(targetObj.namePath());
 *
 * @author Ryan 541720500@qq.com
 */
public class TreeListBuilder {

    List<TreeListBO> dataList;
    Deque<Long> idList;
    Deque<String> nameList;

    public TreeListBuilder(List<TreeListBO> dataList) {
        this.dataList = dataList;
        this.idList = new LinkedList<>();
        this.nameList = new LinkedList<>();
    }

    /**
     * 递归查询
     *
     * @param startId 开始轮询的id，向上轮询
     */
    private void searchPidList(Long startId) {
        Long targetPid = null;
        String targetName = null;
        for (TreeListBO item : this.dataList) {
            if (startId.equals(item.getId())) {
                targetPid = item.getPid();
                targetName = item.getName();
                break;
            }
        }
        if (targetPid == null || targetPid == 0) {
            this.nameList.addFirst(targetName);
            return;
        }
        this.idList.addFirst(targetPid);
        this.nameList.addFirst(targetName);
        searchPidList(targetPid);
    }

    /**
     * 获取id层级结果
     *
     * @param startId 开始轮询的id
     */
    public void build(Long startId) {
        searchPidList(startId);
        this.idList.add(startId);
    }

    /**
     * 获取name层级结果
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public List<Long> getIdPath() {
        return (List<Long>) this.idList;
    }

    /**
     * 获取name层级结果
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public List<String> getNamePath() {
        return (List<String>) this.nameList;
    }
}
