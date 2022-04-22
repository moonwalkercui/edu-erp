package com.hzb.erp.utils;

import com.hzb.erp.common.entity.Org;
import com.hzb.erp.service.TreeListBuilder;
import com.hzb.erp.service.bo.TreeListBO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TreeListBuilderTest {
    @Test
    void tryIt() {

        Org org1 = new Org();
        org1.setId(1L);
        org1.setPid(0L);
        org1.setName("1L");

        Org org2 = new Org();
        org2.setId(2L);
        org2.setPid(1L);
        org2.setName("2L");

        Org org3 = new Org();
        org3.setId(3L);
        org3.setPid(2L);
        org3.setName("3L");

        Org org4 = new Org();
        org4.setId(4L);
        org4.setPid(3L);
        org4.setName("4L");

        Org org5 = new Org();
        org5.setId(5L);
        org5.setPid(2L);
        org5.setName("5L");

        List<TreeListBO> newList = new ArrayList<>();
        newList.add(org2);
        newList.add(org5);
        newList.add(org4);
        newList.add(org1);
        newList.add(org3);

        TreeListBuilder targetObj = new TreeListBuilder(newList);
        System.out.println("============1234============");
        targetObj.build(4L);
        System.out.println(targetObj.getIdPath());
        System.out.println(targetObj.getNamePath());

        TreeListBuilder targetObj2 = new TreeListBuilder(newList);
        System.out.println("============125============");
        targetObj2.build(5L);
        System.out.println(targetObj2.getIdPath());
        System.out.println(targetObj2.getNamePath());

    }
}