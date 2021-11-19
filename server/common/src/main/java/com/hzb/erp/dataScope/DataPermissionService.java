package com.hzb.erp.dataScope;


import com.hzb.erp.common.entity.DataPermission;
import com.hzb.erp.common.entity.StaffOrginfo;
import com.hzb.erp.common.mapper.DataPermissionMapper;
import com.hzb.erp.common.mapper.StaffOrginfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Ryan
 * description 数据权限服务
 **/
@Service
public class DataPermissionService {

    @Autowired
    private DataPermissionMapper dataPermissionMapper;

    @Autowired
    private StaffOrginfoMapper staffOrginfoMapper;

    private static DataPermissionMapper baseMapper;
    private static StaffOrginfoMapper orginfoMapper;

    @PostConstruct
    public void init() {
        baseMapper = dataPermissionMapper;
        orginfoMapper = staffOrginfoMapper;
    }

    /**
     * 获取数据权限配置列表
     */
    public static List<DataPermission> getPermissionList(Long staffId, String entityName) {
        return baseMapper.getByStaffIdAndEntityName( staffId,  entityName);
    }

    /**
    * 获取机构信息
    * */
    public static StaffOrginfo getOrgInfo(Long uid) {
        return orginfoMapper.getByStaffId(uid);
    }

}
